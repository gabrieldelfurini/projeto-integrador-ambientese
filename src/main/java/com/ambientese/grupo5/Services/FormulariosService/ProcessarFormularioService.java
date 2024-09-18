package com.ambientese.grupo5.Services.FormulariosService;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.CheckListRepository;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ambientese.grupo5.DTO.FormularioRequest;
import com.ambientese.grupo5.Model.Enums.NivelCertificadoEnum;
import com.ambientese.grupo5.Model.Enums.RespostasEnum;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Model.RespostaModel;
import com.ambientese.grupo5.Repository.FormularioRepository;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import com.ambientese.grupo5.Repository.RespostaRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcessarFormularioService {

    private final FormularioRepository formularioRepository;

    private final PerguntasRepository perguntasRepository;

    private final RespostaRepository respostaRepository;

    private final EmpresaRepository empresaRepository;

    private final CheckListRepository checkListRepository;

    public ProcessarFormularioService(FormularioRepository formularioRepository, PerguntasRepository perguntasRepository, RespostaRepository respostaRepository, EmpresaRepository empresaRepository, CheckListRepository checkListRepository) {
        this.formularioRepository = formularioRepository;
        this.perguntasRepository = perguntasRepository;
        this.respostaRepository = respostaRepository;
        this.empresaRepository = empresaRepository;
        this.checkListRepository = checkListRepository;
    }

    @Transactional
    public FormularioModel criarFormulario(Long empresaId, List<FormularioRequest> formularioRequestList, Boolean isComplete, Long checklistId) {
        if (checklistId != null) {
            // Se checklistId não for nulo, usa o checklist para criar o formulário
            Optional<CheckListModel> checklistOpt = checkListRepository.findById(checklistId);
            if (!checklistOpt.isPresent()) {
                throw new RuntimeException("Checklist não encontrado com ID: " + checklistId);
            }

            CheckListModel checklist = checklistOpt.get();
            List<PerguntasModel> perguntasDoChecklist = checklist.getPerguntas();

            // Prepara a lista de perguntas para o formulário
            List<FormularioRequest> updatedFormularioRequestList = new ArrayList<>();
            for (PerguntasModel pergunta : perguntasDoChecklist) {
                updatedFormularioRequestList.add(new FormularioRequest(
                        pergunta.getId(),
                        pergunta.getDescricao(),
                        null, // Resposta será null, pois estamos criando um novo formulário
                        pergunta.getEixo(),
                        null // ID do formulário será null para novo formulário
                ));
            }

            formularioRequestList = updatedFormularioRequestList;
        }

        Optional<FormularioModel> formularioIncompletoOpt = formularioRepository.findIncompleteByEmpresaId(empresaId);

        if (isComplete) {
            if (formularioIncompletoOpt.isPresent()) {
                return substituirFormularioIncompletoPorCompleto(formularioIncompletoOpt.get(), formularioRequestList);
            } else {
                return criarFormularioCompleto(empresaId, formularioRequestList);
            }
        } else {
            if (formularioIncompletoOpt.isPresent()) {
                return substituirFormularioIncompletoPorIncompleto(formularioIncompletoOpt.get(), formularioRequestList);
            } else {
                return criarFormularioIncompleto(empresaId, formularioRequestList);
            }
        }
    }

    public FormularioModel criarFormularioCompleto(Long empresaId, List<FormularioRequest> formularioRequestList) {
        validarFormularioCompleto(empresaId, formularioRequestList);

        EmpresaModel empresa = empresaRepository.findById(empresaId).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        FormularioModel formularioModel = new FormularioModel();
        formularioModel.setEmpresa(empresa);

        formularioModel = atualizarPontuacoes(formularioModel, formularioRequestList);

        List<RespostaModel> respostaModels = criarRespostas(formularioModel, formularioRequestList);
        respostaRepository.saveAll(respostaModels);
        formularioModel.setRespostas(respostaModels);
        formularioRepository.save(formularioModel);

        atualizarRanking();

        return formularioModel;
    }

    public FormularioModel criarFormularioIncompleto(Long empresaId, List<FormularioRequest> formularioRequestList) {
        EmpresaModel empresa = empresaRepository.findById(empresaId).orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        FormularioModel formularioModel = new FormularioModel();
        formularioModel.setEmpresa(empresa);
        formularioModel.setDataRespostas(new Date());

        formularioModel = formularioRepository.saveAndFlush(formularioModel);

        List<RespostaModel> respostaModels = criarRespostas(formularioModel, formularioRequestList);
        respostaRepository.saveAll(respostaModels);
        formularioModel.setRespostas(respostaModels);

        return formularioRepository.saveAndFlush(formularioModel);
    }

    private FormularioModel substituirFormularioIncompletoPorCompleto(FormularioModel formularioIncompleto, List<FormularioRequest> formularioRequestList) {
        // Remover todas as respostas antigas do formulário incompleto
        List<RespostaModel> respostasAntigas = formularioIncompleto.getRespostas();
        if (respostasAntigas != null && !respostasAntigas.isEmpty()) {
            formularioIncompleto.setRespostas(new ArrayList<>());
            formularioRepository.saveAndFlush(formularioIncompleto);
            respostaRepository.deleteAll(respostasAntigas);
        }

        // Atualizar o formulário incompleto para se tornar completo
        atualizarPontuacoes(formularioIncompleto, formularioRequestList);
        formularioIncompleto.setDataRespostas(new Date());

        List<RespostaModel> novasRespostas = criarRespostas(formularioIncompleto, formularioRequestList);
        respostaRepository.saveAll(novasRespostas);
        formularioIncompleto.setRespostas(novasRespostas);
        formularioRepository.saveAndFlush(formularioIncompleto);

        atualizarRanking();

        return formularioIncompleto;
    }

    private FormularioModel substituirFormularioIncompletoPorIncompleto(FormularioModel formularioIncompleto, List<FormularioRequest> formularioRequestList) {
        // Remover todas as respostas antigas do formulário incompleto
        List<RespostaModel> respostasAntigas = formularioIncompleto.getRespostas();
        if (respostasAntigas != null && !respostasAntigas.isEmpty()) {
            formularioIncompleto.setRespostas(new ArrayList<>());
            formularioRepository.saveAndFlush(formularioIncompleto);
            respostaRepository.deleteAll(respostasAntigas);
        }

        // Atualizar o formulário incompleto com novas respostas
        formularioIncompleto.setDataRespostas(new Date());

        List<RespostaModel> novasRespostas = criarRespostas(formularioIncompleto, formularioRequestList);
        respostaRepository.saveAll(novasRespostas);
        formularioIncompleto.setRespostas(novasRespostas);

        return formularioRepository.saveAndFlush(formularioIncompleto);
    }

    private FormularioModel atualizarPontuacoes(FormularioModel formularioModel, List<FormularioRequest> formularioRequestList) {
        int totalPerguntas = formularioRequestList.size();
        int perguntasConforme = 0;
        int conformeSocial = 0;
        int naoConformeSocial = 0;
        int conformeAmbiental = 0;
        int naoConformeAmbiental = 0;
        int conformeGovernamental = 0;
        int naoConformeGovernamental = 0;

        for (FormularioRequest resposta : formularioRequestList) {
            if (resposta.getRespostaUsuario() != null) {
                if (resposta.getRespostaUsuario() == RespostasEnum.Conforme) {
                    perguntasConforme++;
                    switch (resposta.getPerguntaEixo()) {
                        case Social:
                            conformeSocial++;
                            break;
                        case Ambiental:
                            conformeAmbiental++;
                            break;
                        case Governamental:
                            conformeGovernamental++;
                            break;
                    }
                } else if (resposta.getRespostaUsuario() == RespostasEnum.NaoConforme) {
                    switch (resposta.getPerguntaEixo()) {
                        case Social:
                            naoConformeSocial++;
                            break;
                        case Ambiental:
                            naoConformeAmbiental++;
                            break;
                        case Governamental:
                            naoConformeGovernamental++;
                            break;
                    }
                } else if (resposta.getRespostaUsuario() == RespostasEnum.NaoSeAdequa) {
                    totalPerguntas--;
                }
            }
        }

        double pontuacaoFinal = (double) perguntasConforme / totalPerguntas * 100.0;
        double pontuacaoAmbiental = (double) conformeAmbiental / (conformeAmbiental + naoConformeAmbiental) * 100.0;
        double pontuacaoSocial = (double) conformeSocial / (conformeSocial + naoConformeSocial) * 100.0;
        double pontuacaoGovernamental = (double) conformeGovernamental / (conformeGovernamental + naoConformeGovernamental) * 100.0;

        NivelCertificadoEnum nivelCertificado = calcularNivelCertificado(pontuacaoFinal);

        formularioModel.setPontuacaoFinal((int) pontuacaoFinal);
        formularioModel.setPontuacaoSocial((int) pontuacaoSocial);
        formularioModel.setPontuacaoAmbiental((int) pontuacaoAmbiental);
        formularioModel.setPontuacaoGovernamental((int) pontuacaoGovernamental);
        formularioModel.setCertificado(nivelCertificado);
        formularioModel.setDataRespostas(new Date());
        return formularioRepository.saveAndFlush(formularioModel);
    }

    private List<RespostaModel> criarRespostas(FormularioModel formularioModel, List<FormularioRequest> formularioRequestList) {
        List<RespostaModel> respostaModels = new ArrayList<>();
        for (FormularioRequest entry : formularioRequestList) {
            PerguntasModel pergunta = perguntasRepository.findById(entry.getPerguntaId()).orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));
            RespostaModel respostaModel = new RespostaModel(formularioModel, pergunta, entry.getRespostaUsuario());
            respostaModels.add(respostaModel);
        }
        return respostaModels;
    }

    private void atualizarRanking() {
        List<FormularioModel> latestFormularios = formularioRepository.findLatestByEmpresaOrderByPontuacaoFinalDesc();

        for (int i = 0; i < latestFormularios.size(); i++) {
            FormularioModel formulario = latestFormularios.get(i);
            EmpresaModel empresa = formulario.getEmpresa();
            empresa.setRanking(i + 1);
            empresaRepository.save(empresa);
        }
    }

    NivelCertificadoEnum calcularNivelCertificado(double pontuacaoFinal) {
        if (pontuacaoFinal >= 100) {
            return NivelCertificadoEnum.Ouro;
        } else if (pontuacaoFinal >= 75.1) {
            return NivelCertificadoEnum.Prata;
        } else {
            return NivelCertificadoEnum.Bronze;
        }
    }

    private void validarFormularioCompleto(Long empresaId, List<FormularioRequest> formularioRequestList) {
        if (formularioRequestList.size() != 30) {
            throw new RuntimeException("Número de perguntas inválido");
        }
        if (empresaId == null) {
            throw new RuntimeException("ID da empresa inválido");
        }
    }

    public boolean haveAvaliacaoAtiva(Long empresaId) {
        FormularioModel ultimoFormulario = formularioRepository.findLatestFormByEmpresaId(empresaId);
        if (ultimoFormulario != null) {
            return ultimoFormulario.getPontuacaoFinal() == null;
        }

        return false;
    }
}

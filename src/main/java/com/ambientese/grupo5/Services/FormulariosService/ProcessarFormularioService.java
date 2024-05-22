package com.ambientese.grupo5.Services.FormulariosService;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ambientese.grupo5.DTO.FormularioRequest;
import com.ambientese.grupo5.Model.Enums.NivelCertificadoEnum;
import com.ambientese.grupo5.Model.Enums.RespostasEnum;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Repository.FormularioRepository;

@Service
public class ProcessarFormularioService {

    private final FormularioRepository formularioRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public ProcessarFormularioService(FormularioRepository formularioRepository, EmpresaRepository empresaRepository) {
        this.formularioRepository = formularioRepository;
        this.empresaRepository = empresaRepository;
    }

    public FormularioModel criarProcessarEGerarCertificado(Long empresa_id, List<FormularioRequest> formularioRequestList) {
        int totalPerguntas = formularioRequestList.size();
        int perguntasConforme = 0;
        int pontuacaoSocial = 0;
        int pontuacaoAmbiental = 0;
        int pontuacaoGovernamental = 0;

        // Verificar as respostas e calcular pontuações
        for (FormularioRequest resposta : formularioRequestList) {
            if (resposta.getRespostaUsuario() != null && resposta.getRespostaUsuario() == RespostasEnum.Conforme) {
                perguntasConforme++;
                switch (resposta.getPerguntaEixo()) {
                    case Social:
                        pontuacaoSocial++;
                        break;
                    case Ambiental:
                        pontuacaoAmbiental++;
                        break;
                    case Governamental:
                        pontuacaoGovernamental++;
                        break;
                    default:
                        break;
                }
            }
        }

        double pontuacaoFinal = (double) perguntasConforme / totalPerguntas * 100.0;

        // Gerar certificado
        NivelCertificadoEnum nivelCertificado = calcularNivelCertificado(pontuacaoFinal);

        // Verificar a existência da empresa
        EmpresaModel empresa = empresaRepository.findById(empresa_id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));


        // Criar o objeto de modelo de formulário e definir suas propriedades
        FormularioModel formularioModel = new FormularioModel();
        formularioModel.setPontuacaoFinal((int) pontuacaoFinal);
        formularioModel.setPontuacaoSocial(pontuacaoSocial);
        formularioModel.setPontuacaoAmbiental(pontuacaoAmbiental);
        formularioModel.setPontuacaoGovernamental(pontuacaoGovernamental);
        formularioModel.setCertificado(nivelCertificado);
        formularioModel.setEmpresa(empresa); // Definir a empresa no formulário


        // Adicionar a hora das respostas
        formularioModel.setDataRespostas(new Date());

        // Crie uma lista de respostas associadas às perguntas
        List<RespostasEnum> respostas = new ArrayList<>();
        for (FormularioRequest entry : formularioRequestList) {
            respostas.add(entry.getRespostaUsuario());
        }
        formularioModel.setRespostas(respostas);

        // Salvar o formulário
        formularioRepository.save(formularioModel);

        return formularioModel;
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
}

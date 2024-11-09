package com.ambientese.grupo5.Services.FormulariosService;

import com.ambientese.grupo5.DTO.FormularioRequest;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Model.RespostaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository; // Novo repositório para EmpresaModel
import com.ambientese.grupo5.Repository.FormularioRepository;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormularioService {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private PerguntasRepository perguntasRepository;

    @Autowired
    private EmpresaRepository empresaRepository; // Novo repositório para EmpresaModel

    @Transactional
    public FormularioModel criarFormulario(Long empresaId, List<FormularioRequest> perguntasSelecionadas, Boolean isComplete) {
        // Cria uma nova instância do FormularioModel
        FormularioModel formulario = new FormularioModel();

        // Busca a empresa pelo ID
        EmpresaModel empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + empresaId));

        // Define a empresa no formulário
        formulario.setEmpresa(empresa);

        // Define o status do formulário (se o método existir na sua classe)
        formulario.setComplete(isComplete);

        // Recupera as perguntas baseadas nos IDs fornecidos
        List<PerguntasModel> perguntasModels = perguntasRepository.findAllById(
                perguntasSelecionadas.stream().map(FormularioRequest::getPerguntaId).collect(Collectors.toList())
        );

        // Mapeia as respostas do formulário
        List<RespostaModel> respostas = perguntasSelecionadas.stream()
                .map(req -> new RespostaModel(
                        formulario,
                        perguntasModels.stream().filter(p -> p.getId() == req.getPerguntaId()).findFirst().orElse(null),
                        req.getRespostaUsuario()
                ))
                .collect(Collectors.toList());

        ProcessarFormularioService processarFormularioService = new ProcessarFormularioService(formularioRepository, perguntasRepository, null, empresaRepository, null);
        processarFormularioService.atualizarPontuacoes(formulario, perguntasSelecionadas);
        // Define as respostas no formulário e salva
        formulario.setRespostas(respostas);
        formularioRepository.save(formulario);

        return formulario;
    }
}

package com.ambientese.grupo5.Controller.FormularioController;

import com.ambientese.grupo5.DTO.FormularioRequest;
import com.ambientese.grupo5.DTO.QuestionarioResponse;
import com.ambientese.grupo5.Model.FormularioModel;
import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Services.FormulariosService.CheckListService;
import com.ambientese.grupo5.Services.FormulariosService.FormularioService;
import com.ambientese.grupo5.Services.FormulariosService.PerguntasCheckListService;
import com.ambientese.grupo5.Services.FormulariosService.ProcessarFormularioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionarioController {

    private final PerguntasCheckListService perguntasCheckListService;
    private final CheckListService checkListService;
    private final FormularioService formularioService;
    private final ProcessarFormularioService processarFormularioService;

    @Autowired
    public QuestionarioController(PerguntasCheckListService perguntasCheckListService, CheckListService checkListService, FormularioService formularioService, ProcessarFormularioService processarFormularioService) {
        this.perguntasCheckListService = perguntasCheckListService;
        this.checkListService = checkListService;
        this.formularioService = formularioService;
        this.processarFormularioService = processarFormularioService;
    }

    @GetMapping("/auth/haveAvaliacaoAtiva/{empresaId}")
    public boolean avaliacaoAtiva(@PathVariable() Long empresaId) {
        // Chamar o método correto do ProcessarFormularioService
        return processarFormularioService.haveAvaliacaoAtiva(empresaId);
    }

    @GetMapping("/auth/questionario/{checklistId}")
    public QuestionarioResponse exibirQuestionario(@PathVariable Long checklistId, @RequestParam(required = false) Long empresaId) {
        if (empresaId == null) {
            throw new RuntimeException("ID da empresa não pode ser nulo");
        }
        // Usar o método correto para buscar perguntas do checklist
        return perguntasCheckListService.buscarPerguntasDoChecklist(checklistId, empresaId);
    }

    @PostMapping("/auth/processarRespostas")
    public FormularioModel processarRespostas(@RequestParam("empresa_id") Long empresaId, @RequestParam("is_complete") Boolean isComplete, @RequestBody List<FormularioRequest> respostas) {
        return formularioService.criarFormulario(empresaId, respostas, isComplete);
    }

    @GetMapping("/auth/checklists")
    public List<CheckListModel> listarChecklists() {
        return checkListService.buscarChecklists();
    }
}

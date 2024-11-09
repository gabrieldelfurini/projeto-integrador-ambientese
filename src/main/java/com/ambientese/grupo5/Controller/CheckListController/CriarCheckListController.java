package com.ambientese.grupo5.Controller.CheckListController;

import com.ambientese.grupo5.DTO.CheckListRequest;
import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Services.CheckListService.CriarCheckListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checklist")
public class CriarCheckListController {

    private final CriarCheckListService criarCheckListService;

    public CriarCheckListController(CriarCheckListService criarCheckListService) {
        this.criarCheckListService = criarCheckListService;
    }

    @PostMapping("/Add")
    public ResponseEntity<CheckListModel> createCheckList(
            @RequestParam("eixo") EixoEnum eixo,
            @RequestBody CheckListRequest checkListRequest) {

        String descricao = checkListRequest.getDescricao();
        List<Long> selectedPerguntasIds = checkListRequest.getSelectedPerguntasIds();

        System.out.println(" Descricao: " + descricao + " Selected Perguntas: " + selectedPerguntasIds);

        CheckListModel createdCheckList = criarCheckListService.createCheckList(eixo, descricao, selectedPerguntasIds);
        return ResponseEntity.ok(createdCheckList);
    }
}

package com.ambientese.grupo5.Controller.CheckListController;

import com.ambientese.grupo5.DTO.CheckListRequest;
import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Services.CheckListService.AtualizarCheckListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checklist")
public class AtualizarCheckListController {

    private final AtualizarCheckListService atualizarCheckListService;

    public AtualizarCheckListController(AtualizarCheckListService atualizarCheckListService) {
        this.atualizarCheckListService = atualizarCheckListService;
    }

    @PutMapping("/Edit/{id}")
    public ResponseEntity<CheckListModel> updateCheckList(
            @PathVariable Long id,
            @RequestParam("eixo") EixoEnum eixo, // Adicionando o EixoEnum aqui
            @RequestBody CheckListRequest checkListRequest) {

        String descricao = checkListRequest.getDescricao();
        List<Long> selectedPerguntasIds = checkListRequest.getSelectedPerguntasIds();

        CheckListModel updatedCheckList = atualizarCheckListService.updateCheckList(id, eixo, descricao, selectedPerguntasIds);
        return ResponseEntity.ok(updatedCheckList);
    }
}

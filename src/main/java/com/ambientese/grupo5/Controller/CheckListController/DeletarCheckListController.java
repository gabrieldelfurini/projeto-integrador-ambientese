package com.ambientese.grupo5.Controller.CheckListController;

import com.ambientese.grupo5.Services.CheckListService.DeletarCheckListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checklist")
public class DeletarCheckListController {

    private final DeletarCheckListService deletarcheckListService;

    public DeletarCheckListController(DeletarCheckListService deletarCheckListService) {
        this.deletarcheckListService = deletarCheckListService;
    }

    // Endpoint para deletar um checklist pelo ID
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<String> deleteCheckList(@PathVariable Long id) {
        try {
            deletarcheckListService.deleteCheckList(id);
            return ResponseEntity.ok("Checklist deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}

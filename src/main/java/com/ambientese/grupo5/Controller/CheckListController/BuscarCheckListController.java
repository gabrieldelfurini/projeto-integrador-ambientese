package com.ambientese.grupo5.Controller.CheckListController;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Services.CheckListService.ListarCheckListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checklists")
public class BuscarCheckListController {

    private final ListarCheckListService listarCheckListService;

    public BuscarCheckListController(ListarCheckListService listarCheckListService) {
        this.listarCheckListService = listarCheckListService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckListModel> getCheckListById(@PathVariable Long id) {
        Optional<CheckListModel> checkList = listarCheckListService.findById(id);
        return checkList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CheckListModel>> getAllCheckLists() {
        List<CheckListModel> checkLists = listarCheckListService.findAll();
        return ResponseEntity.ok(checkLists);
    }
}

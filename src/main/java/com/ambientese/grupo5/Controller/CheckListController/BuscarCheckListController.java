package com.ambientese.grupo5.Controller.CheckListController;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Services.CheckListService.ListarCheckListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checklists")
public class BuscarCheckListController {

    private final ListarCheckListService listarCheckListService;

    public BuscarCheckListController(ListarCheckListService listarCheckListService) {
        this.listarCheckListService = listarCheckListService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckListModel> getCheckListById(@PathVariable Long id) {
        CheckListModel checkList = listarCheckListService.findById(id);
        return ResponseEntity.ok(checkList);
    }

    @GetMapping
    public ResponseEntity<List<CheckListModel>> getAllCheckLists() {
        List<CheckListModel> checkLists = listarCheckListService.findAll();
        return ResponseEntity.ok(checkLists);
    }

    @GetMapping("/by-eixo")
    public ResponseEntity<List<CheckListModel>> getCheckListsByEixo(@RequestParam EixoEnum eixo) {
        List<CheckListModel> checkLists = listarCheckListService.findByEixo(eixo);
        return ResponseEntity.ok(checkLists);
    }
}

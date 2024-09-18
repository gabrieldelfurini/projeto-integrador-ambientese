package com.ambientese.grupo5.Services.CheckListService;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Repository.CheckListRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListarCheckListService {

    private final CheckListRepository checkListRepository;

    public ListarCheckListService(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    public CheckListModel findById(Long id) {
        return checkListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Checklist não encontrado com ID: " + id));
    }

    public List<CheckListModel> findAll() {
        return checkListRepository.findAll();
    }

    // Método opcional para paginação
    public Page<CheckListModel> findAllWithPagination(Pageable pageable) {
        return checkListRepository.findAll(pageable);
    }

    // Novo método para buscar checklists por eixo
    public List<CheckListModel> findByEixo(EixoEnum eixo) {
        return checkListRepository.findByEixo(eixo);
    }
}

package com.ambientese.grupo5.Services.CheckListService;

import com.ambientese.grupo5.Repository.CheckListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletarCheckListService {

    private final CheckListRepository checkListRepository;

    public DeletarCheckListService(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    @Transactional
    public void deleteCheckList(Long id) {
        checkListRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Checklist não encontrado com ID: " + id)
        );

        checkListRepository.deleteById(id);
    }
}

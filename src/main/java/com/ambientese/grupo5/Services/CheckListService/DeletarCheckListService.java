package com.ambientese.grupo5.Services.CheckListService;

import com.ambientese.grupo5.Repository.CheckListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Corrigido para importar do pacote do Spring

@Service
public class DeletarCheckListService {

    private final CheckListRepository checkListRepository;

    public DeletarCheckListService(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    @Transactional
    public void deleteCheckList(Long id) {
        if (!checkListRepository.existsById(id)) {
            throw new RuntimeException("Checklist não encontrado com ID: " + id);
        }

        checkListRepository.deleteById(id);
    }
}

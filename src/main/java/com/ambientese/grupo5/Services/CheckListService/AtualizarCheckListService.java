package com.ambientese.grupo5.Services.CheckListService;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Repository.CheckListRepository;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtualizarCheckListService {

    private final CheckListRepository checkListRepository;
    private final PerguntasRepository perguntasRepository;

    public AtualizarCheckListService(CheckListRepository checkListRepository, PerguntasRepository perguntasRepository) {
        this.checkListRepository = checkListRepository;
        this.perguntasRepository = perguntasRepository;
    }

    public CheckListModel updateCheckList(Long id, String descricao, List<Long> selectedPerguntasIds) {
        Optional<CheckListModel> existingCheckListOpt = checkListRepository.findById(id);

        if (existingCheckListOpt.isEmpty()) {
            throw new RuntimeException("Checklist não encontrado com ID: " + id);
        }

        CheckListModel existingCheckList = existingCheckListOpt.get();
        existingCheckList.setDescricao(descricao);

        List<PerguntasModel> selectedPerguntas = perguntasRepository.findAllById(selectedPerguntasIds);
        existingCheckList.setPerguntas(selectedPerguntas);

        return checkListRepository.save(existingCheckList);
    }
}

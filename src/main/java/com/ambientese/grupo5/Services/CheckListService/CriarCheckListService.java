package com.ambientese.grupo5.Services.CheckListService;

import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Repository.CheckListRepository;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CriarCheckListService {

    private final CheckListRepository checkListRepository;
    private final PerguntasRepository perguntasRepository;

    public CriarCheckListService(CheckListRepository checkListRepository, PerguntasRepository perguntasRepository) {
        this.checkListRepository = checkListRepository;
        this.perguntasRepository = perguntasRepository;
    }

    public CheckListModel createCheckList(EixoEnum eixo, String descricao, List<Long> selectedPerguntasIds) {
        // Busca as perguntas por Eixo
        List<PerguntasModel> perguntasByEixo = perguntasRepository.findByEixo(eixo);

        List<PerguntasModel> selectedPerguntas = perguntasByEixo.stream()
                .filter(pergunta -> selectedPerguntasIds.contains(pergunta.getId()))
                .collect(Collectors.toList());

        CheckListModel checkList = new CheckListModel();
        checkList.setDescricao(descricao);

        checkList.setPerguntas(selectedPerguntas);

        return checkListRepository.save(checkList);
    }
}

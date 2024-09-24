package com.ambientese.grupo5.Services.FormulariosService;

import com.ambientese.grupo5.DTO.FormularioRequest;
import com.ambientese.grupo5.DTO.QuestionarioResponse;
import com.ambientese.grupo5.Model.CheckListModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Repository.CheckListRepository;
import com.ambientese.grupo5.Repository.PerguntasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerguntasCheckListService {

    @Autowired
    private CheckListRepository checkListRepository;

    @Autowired
    private PerguntasRepository perguntasRepository;

    @Transactional
    public QuestionarioResponse buscarPerguntasDoChecklist(Long checklistId, Long empresaId) {
        Optional<CheckListModel> checklistOpt = checkListRepository.findById(checklistId);
        if (!checklistOpt.isPresent()) {
            throw new RuntimeException("Checklist não encontrado com ID: " + checklistId);
        }

        CheckListModel checklist = checklistOpt.get();
        List<PerguntasModel> perguntasDoChecklist = checklist.getPerguntas();

        // Embaralhar a lista se necessário
        Collections.shuffle(perguntasDoChecklist);

        List<FormularioRequest> perguntasRequestList = perguntasDoChecklist.stream()
                .map(pergunta -> new FormularioRequest(
                        pergunta.getId(),
                        pergunta.getDescricao(),
                        null, // Resposta será null, pois estamos criando um novo formulário
                        pergunta.getEixo(),
                        null // ID do formulário será null para novo formulário
                ))
                .collect(Collectors.toList());

        return new QuestionarioResponse(null, perguntasRequestList);
    }
}

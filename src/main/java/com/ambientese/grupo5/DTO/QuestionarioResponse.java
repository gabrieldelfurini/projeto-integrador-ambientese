package com.ambientese.grupo5.DTO;

import com.ambientese.grupo5.Model.PerguntasModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuestionarioResponse {
    private List<PerguntasModel> perguntas;
    private List<FormularioRequest> formularioRequests;

    public QuestionarioResponse(List<PerguntasModel> perguntas, List<FormularioRequest> formularioRequests) {
        this.perguntas = perguntas;
        this.formularioRequests = formularioRequests;
    }
}

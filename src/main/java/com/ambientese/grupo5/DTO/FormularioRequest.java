package com.ambientese.grupo5.DTO;

import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Model.Enums.RespostasEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormularioRequest {
    private Long perguntaId;
    private Long numeroPergunta;
    private String perguntaDescricao;
    private RespostasEnum respostaUsuario;
    private EixoEnum perguntaEixo;
    private Long idFormulario;
}

package com.ambientese.grupo5.DTO;

import com.ambientese.grupo5.Model.Enums.EixoEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PerguntasRequest {
    @NotNull
    @NotBlank
    private String descricao;

    @NotNull
    @NotBlank
    private EixoEnum eixo;
}

package com.ambientese.grupo5.DTO;

import com.ambientese.grupo5.Model.Enums.EixoEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PerguntaCadastro {
    private long id;
    private String descricao;
    private EixoEnum eixo;
    private Boolean finishList;

    public PerguntaCadastro(long id, String descricao, EixoEnum eixo, Boolean finishList) {
        this.id = id;
        this.descricao = descricao;
        this.eixo = eixo;
        this.finishList = finishList;
    }
}

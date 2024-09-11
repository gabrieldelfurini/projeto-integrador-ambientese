package com.ambientese.grupo5.DTO;

import java.util.List;

public class CheckListRequest {
    private String descricao;
    private List<Long> selectedPerguntasIds;

    // Getters e Setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Long> getSelectedPerguntasIds() {
        return selectedPerguntasIds;
    }

    public void setSelectedPerguntasIds(List<Long> selectedPerguntasIds) {
        this.selectedPerguntasIds = selectedPerguntasIds;
    }
}


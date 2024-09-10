package com.ambientese.grupo5.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import jakarta.persistence.*;

@Entity
@Table(name = "CheckList")
public class CheckListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String descricao;

    // Relação Many-to-One com PerguntasModel
    @ManyToOne
    @JoinColumn(name = "pergunta_id") // Chave estrangeira
    private PerguntasModel perguntas;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public PerguntasModel getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(PerguntasModel perguntas) {
        this.perguntas = perguntas;
    }
}

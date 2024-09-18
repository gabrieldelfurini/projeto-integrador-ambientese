package com.ambientese.grupo5.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.ambientese.grupo5.Model.Enums.EixoEnum;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "checklist")
public class CheckListModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String descricao;

    @ManyToMany
    @JoinTable(
            name = "checklist_perguntas",
            joinColumns = @JoinColumn(name = "checklist_id"),
            inverseJoinColumns = @JoinColumn(name = "pergunta_id")
    )
    private List<PerguntasModel> perguntas;

    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private EixoEnum eixo;

    public @NotNull @NotBlank EixoEnum getEixo() {
        return eixo;
    }

    public void setEixo(@NotNull @NotBlank EixoEnum eixo) {
        this.eixo = eixo;
    }

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

    public List<PerguntasModel> getPerguntas() {
        return perguntas;
    }

    public void setPerguntas(List<PerguntasModel> perguntas) {
        this.perguntas = perguntas;
    }
}

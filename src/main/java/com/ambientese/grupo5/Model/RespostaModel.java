package com.ambientese.grupo5.Model;

import com.ambientese.grupo5.Model.Enums.RespostasEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Resposta")
public class RespostaModel {

    @EmbeddedId
    private RespostaId id = new RespostaId();

    @ManyToOne
    @MapsId("formularioId")
    @JoinColumn(name = "formulario_id")
    @JsonBackReference
    private FormularioModel formulario;

    @ManyToOne
    @MapsId("perguntaId")
    @JoinColumn(name = "pergunta_id")
    private PerguntasModel pergunta;

    @Enumerated(EnumType.STRING)
    @Column(name = "resposta")
    private RespostasEnum resposta;

    public RespostaModel(FormularioModel formulario, PerguntasModel pergunta, RespostasEnum resposta) {
        this.formulario = formulario;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.id = new RespostaId(formulario.getId(), pergunta.getId());
    }
}

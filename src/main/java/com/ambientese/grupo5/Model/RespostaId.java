package com.ambientese.grupo5.Model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RespostaId implements Serializable {
    private Long formularioId;
    private Long perguntaId;
}

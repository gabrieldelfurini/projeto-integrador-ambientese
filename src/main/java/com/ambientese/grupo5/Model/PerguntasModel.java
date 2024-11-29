package com.ambientese.grupo5.Model;

import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Pergunta")
@Data // Lombok gera getters, setters, toString, equals, hashCode automaticamente
@NoArgsConstructor // Gera o construtor sem parâmetros
@AllArgsConstructor // Gera o construtor com todos os parâmetros
public class PerguntasModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank

    @NotNull
    @NotBlank
    @Enumerated(EnumType.STRING)
    private EixoEnum eixo;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL)
    @JsonIgnore // Evita a serialização da lista de respostas
    private List<RespostaModel> respostas;

    private Long numeroPergunta;
    private String descricao;

    // O Lombok vai gerar os métodos getters e setters, construtores, equals, hashCode, e toString.
}

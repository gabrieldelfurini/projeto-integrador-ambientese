package com.ambientese.grupo5.Model;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String cep;

    @NotNull
    private Integer numero;

    @NotNull
    @NotBlank
    private String logradouro;

    private String complemento;

    @NotNull
    @NotBlank
    private String cidade;

    @NotNull
    @NotBlank
    private String bairro;

    @NotNull
    @NotBlank
    private String UF;
}

package com.ambientese.grupo5.DTO;

import com.ambientese.grupo5.Model.EnderecoModel;
import com.ambientese.grupo5.Model.Enums.PorteEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class EmpresaRequest {

    @NotNull
    @NotBlank
    private String nomeFantasia;

    @NotNull
    @NotBlank
    private String nomeSolicitante;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    private String telefoneSolicitante;

    @NotNull
    @NotBlank
    private String razaoSocial;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    private String cnpj;

    @Column(length = 20)
    private String inscricaoSocial;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private EnderecoModel endereco;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    private String telefoneEmpresas;

    @NotNull
    @NotBlank
    private String ramo;

    @NotNull
    @NotBlank
    private PorteEnum porteEmpresas;
}

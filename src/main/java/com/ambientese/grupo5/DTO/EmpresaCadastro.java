package com.ambientese.grupo5.DTO;

import com.ambientese.grupo5.Model.EnderecoModel;
import com.ambientese.grupo5.Model.Enums.PorteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaCadastro {
    private Long id;
    private String nomeFantasia;
    private String nomeSolicitante;
    private String telefoneSolicitante;
    private String razaoSocial;
    private String cnpj;
    private String inscricaoSocial;
    private EnderecoModel endereco;
    private String email;
    private String telefoneEmpresas;
    private String ramo;
    private PorteEnum porteEmpresas;
    private Integer ranking;
    private Boolean finishList;
}

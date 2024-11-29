package com.ambientese.grupo5.DTO;

import com.ambientese.grupo5.Model.Enums.NivelCertificadoEnum;
import com.ambientese.grupo5.Model.Enums.PorteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormularioRanking {
    private long id;
    private Integer ranking;
    private String empresaNome;
    private NivelCertificadoEnum nivelCertificado;
    private String ramo;
    private PorteEnum porte;
    private Integer pontuacaoFinal;
    private Integer pontuacaoSocial;
    private Integer pontuacaoAmbiental;
    private Integer pontuacaoGovernamental;
    private Boolean finishList;
}

package com.ambientese.grupo5.Model;

import com.ambientese.grupo5.Model.Enums.NivelCertificadoEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Formulario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormularioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "formulario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private final List<RespostaModel> respostas = new ArrayList<>();

    @Column(name = "certificado")
    @Enumerated(EnumType.STRING)
    private NivelCertificadoEnum certificado;

    @ManyToOne()
    @JoinColumn(name = "empresa_id")
    private EmpresaModel empresa;

    @Column(name = "pontuacao_final")
    private Integer pontuacaoFinal;

    @Column(name = "pontuacao_social")
    private Integer pontuacaoSocial;

    @Column(name = "pontuacao_ambiental")
    private Integer pontuacaoAmbiental;

    @Column(name = "pontuacao_governamental")
    private Integer pontuacaoGovernamental;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_respostas")
    private Date dataRespostas;

    public void addResposta(RespostaModel resposta) {
        respostas.add(resposta);
        resposta.setFormulario(this);
    }

    public void removeResposta(RespostaModel resposta) {
        respostas.remove(resposta);
        resposta.setFormulario(null);
    }

    public void setRespostas(List<RespostaModel> respostas) {
        this.respostas.clear();
        if (respostas != null) {
            for (RespostaModel resposta : respostas) {
                addResposta(resposta);
            }
        }
    }
}

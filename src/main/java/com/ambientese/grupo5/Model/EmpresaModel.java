package com.ambientese.grupo5.Model;

import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.ambientese.grupo5.Model.Enums.PorteEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Empresa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String nomeFantasia;

    @NotNull
    @NotBlank
    private String nomeSolicitante;

    @Column(length = 15)
    @Pattern(regexp = "^[0-9]+$")
    @NotNull
    @NotBlank
    private String telefoneSolicitante;

    @NotNull
    @NotBlank
    private String razaoSocial;

    @Column(length = 14)
    @Pattern(regexp = "^[0-9]+$")
    @NotNull
    @NotBlank
    private String cnpj;

    @Column(length = 20)
    private String inscricaoSocial;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private EnderecoModel endereco;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @Column(length = 15)
    @Pattern(regexp = "^[0-9]+$")
    @NotNull
    @NotBlank
    private String telefoneEmpresas;

    @NotNull
    @NotBlank
    private String ramo;

    @NotNull
    @NotBlank
    private PorteEnum porteEmpresas;

    @Column(name = "ranking")
    private Integer ranking;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EmpresaModel other = (EmpresaModel) obj;
        return Objects.equals(id, other.id);
    }
}

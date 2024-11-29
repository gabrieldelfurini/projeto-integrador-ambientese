package com.ambientese.grupo5.Model;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "Funcionario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioModel {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

    @NotBlank(message = "O nome não pode estar em branco")
    private String nome;

    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O CPF deve estar no formato XXX.XXX.XXX-XX")
    @NotBlank(message = "O CPF não pode estar em branco")
    private String cpf;

    @Email(message = "O email deve ser válido")
    @NotBlank(message = "O email não pode estar em branco")
    private String email;

    @NotBlank(message = "A data de nascimento não pode estar em branco")
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private CargoModel cargo;
}

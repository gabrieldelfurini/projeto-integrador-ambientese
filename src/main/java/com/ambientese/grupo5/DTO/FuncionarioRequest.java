package com.ambientese.grupo5.DTO;

import com.ambientese.grupo5.Model.UsuarioModel;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioRequest {

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

    private UsuarioModel usuario;

    @NotBlank(message = "O cargo não pode estar em branco")
    private String cargo;

    private String login;
}
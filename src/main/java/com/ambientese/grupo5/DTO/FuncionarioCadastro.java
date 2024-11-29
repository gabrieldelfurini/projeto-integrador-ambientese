package com.ambientese.grupo5.DTO;

import java.time.LocalDate;

import com.ambientese.grupo5.Model.CargoModel;
import com.ambientese.grupo5.Model.UsuarioModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioCadastro {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private LocalDate dataNascimento;
    private CargoModel cargo;
    private UsuarioModel usuario;
    private Boolean finishList;
}

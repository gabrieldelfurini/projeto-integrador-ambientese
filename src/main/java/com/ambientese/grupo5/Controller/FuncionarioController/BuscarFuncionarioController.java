package com.ambientese.grupo5.Controller.FuncionarioController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ambientese.grupo5.DTO.FuncionarioCadastro;
import com.ambientese.grupo5.Services.FuncionarioService.ListarFuncionarioService;


@RestController
@RequestMapping("/auth/Funcionario")
@Validated
public class BuscarFuncionarioController {

    @Autowired
    private ListarFuncionarioService listarFuncionarioService;

    @GetMapping("/search")
    public ResponseEntity<List<FuncionarioCadastro>> buscarFuncionarios(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        List<FuncionarioCadastro> resultado = listarFuncionarioService.allPagedFuncionariosWithFilter(nome, page, size);

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/Get/{id}")
    public ResponseEntity<FuncionarioCadastro> buscarFuncionarioPorId(@PathVariable Long id) {
        FuncionarioCadastro funcionario = listarFuncionarioService.getFuncionarioById(id);
        return ResponseEntity.ok(funcionario);
    }
}

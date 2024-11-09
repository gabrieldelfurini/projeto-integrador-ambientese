package com.ambientese.grupo5.Services.FuncionarioService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ambientese.grupo5.DTO.FuncionarioCadastro;
import com.ambientese.grupo5.Model.FuncionarioModel;
import com.ambientese.grupo5.Repository.FuncionarioRepository;

import jakarta.transaction.Transactional;


@Service
public class ListarFuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;    

    @Transactional
    public List<FuncionarioCadastro> allPagedFuncionariosWithFilter(String nome, int page, int size) {
        List<FuncionarioModel> funcionarios;
        if (nome != null && !nome.isEmpty()) {
            funcionarios = funcionarioRepository.findAllByNomeStartingWithIgnoreCaseOrderByNomeAsc(nome);

            if (funcionarios.isEmpty()) {
                funcionarios = funcionarioRepository.findAllOrderByNomeAsc().stream()
                        .filter(funcionario -> funcionario.getCargo().getDescricao().toString().toLowerCase().startsWith(nome.toLowerCase()))
                        .collect(Collectors.toList());
            }
        } else {
            funcionarios = funcionarioRepository.findAll();
        }

        List<FuncionarioCadastro> resultado = paginarFuncionarios(funcionarios, page, size);

        return resultado;
    }

    private List<FuncionarioCadastro> paginarFuncionarios(List<FuncionarioModel> funcionarios, int page, int size) {
        int total = funcionarios.size();
        int start = Math.min(page * size, total);
        int end = Math.min((page + 1) * size, total);

        return funcionarios.subList(start, end).stream()
                .map(funcionario -> new FuncionarioCadastro(
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getCpf(),
                        funcionario.getEmail(),
                        funcionario.getDataNascimento(),
                        funcionario.getCargo(),
                        funcionario.getUsuario(),
                        end == total
                ))
                .collect(Collectors.toList());
    }

    
    @Transactional
    public FuncionarioCadastro getFuncionarioById(Long id) {
        Optional<FuncionarioModel> funcionarioOptional = funcionarioRepository.findById(id);
        
        if (!funcionarioOptional.isPresent()) {
            throw new RuntimeException("Funcionário não encontrado"); // or a custom exception
        }
        
        FuncionarioModel funcionario = funcionarioOptional.get();
        return new FuncionarioCadastro(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getEmail(),
                funcionario.getDataNascimento(),
                funcionario.getCargo(),
                funcionario.getUsuario(),
                false // Assuming 'isLastPage' is false for a single item
        );
    }
    
}

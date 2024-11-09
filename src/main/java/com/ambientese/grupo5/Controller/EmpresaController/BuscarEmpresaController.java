package com.ambientese.grupo5.Controller.EmpresaController;

import com.ambientese.grupo5.DTO.EmpresaCadastro;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Services.EmpresaService.ListarEmpresaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth/Empresa")
@Validated
public class BuscarEmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private ListarEmpresaService listarEmpresaService;

    @GetMapping
    public ResponseEntity<List<EmpresaModel>> getAllEmpresas() {
        List<EmpresaModel> empresaModels = listarEmpresaService.getAllEmpresas();
        return ResponseEntity.ok(empresaModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEmpresaPorId(@PathVariable Long id) {
        Optional<EmpresaModel> empresa = empresaRepository.findById(id);
        return empresa.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmpresaCadastro>> buscarEmpresas(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        System.out.println("Searching for companies with name: ");
        List<EmpresaCadastro> resultado = listarEmpresaService.allPagedEmpresasWithFilter(nome, page, size);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/avaliacao/search")
    public ResponseEntity<List<EmpresaCadastro>> empresasParaAvaliacao(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<EmpresaCadastro> resultado = listarEmpresaService.allPagedEmpresasWithFilter2(nome, page, size);
        return ResponseEntity.ok(resultado);
    }

}

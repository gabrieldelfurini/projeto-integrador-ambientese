package com.ambientese.grupo5.Controller.PerguntasController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ambientese.grupo5.DTO.PerguntaCadastro;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Model.PerguntasModel;
import com.ambientese.grupo5.Model.Enums.EixoEnum;
import com.ambientese.grupo5.Services.PerguntasService.ListarPerguntasService;
import com.ambientese.grupo5.Repository.PerguntasRepository;

@RestController
@RequestMapping("/auth/Perguntas")
public class BuscarPerguntaController {

    @Autowired
    private PerguntasRepository perguntasRepository;

    @Autowired
    private ListarPerguntasService listarPerguntasService;

    @GetMapping("/eixo/{eixo}")
    public List<PerguntasModel> listarPerguntasPorEixo(@PathVariable EixoEnum eixo) {
        return listarPerguntasService.listarPerguntasPorEixo(eixo);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PerguntaCadastro>> buscarPerguntas(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {


        List<PerguntaCadastro> resultado = listarPerguntasService.allPagedPerguntasWithFilter(nome, page, size);

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEmpresaPorId(@PathVariable Long id) {
        Optional<PerguntasModel> pergunta = perguntasRepository.findById(id);
        return pergunta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
}

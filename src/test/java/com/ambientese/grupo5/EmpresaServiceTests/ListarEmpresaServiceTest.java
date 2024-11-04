package com.ambientese.grupo5.EmpresaServiceTests;

import com.ambientese.grupo5.DTO.EmpresaCadastro;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Services.EmpresaService.ListarEmpresaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
public class ListarEmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private ListarEmpresaService listarEmpresaService;

    @Test
    void getAllEmpresas_quandoNaoHaEmpresas_entaoRetornaListaVazia() {
        when(empresaRepository.findAll()).thenReturn(Collections.emptyList());

        List<EmpresaModel> result = listarEmpresaService.getAllEmpresas();

        assertTrue(result.isEmpty());
        verify(empresaRepository, times(1)).findAll();
    }

    @Test
    void getAllEmpresas_quandoHaEmpresas_entaoRetornaListaDeEmpresas() {
        EmpresaModel empresa = new EmpresaModel();
        when(empresaRepository.findAll()).thenReturn(Collections.singletonList(empresa));

        List<EmpresaModel> result = listarEmpresaService.getAllEmpresas();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(empresaRepository, times(1)).findAll();
    }

    @Test
    void allPagedEmpresasWithFilter_quandoNomeNaoFornecido_entaoRetornaTodasEmpresas() {
        EmpresaModel empresa = new EmpresaModel();
        when(empresaRepository.findAll()).thenReturn(Collections.singletonList(empresa));

        List<EmpresaCadastro> result = listarEmpresaService.allPagedEmpresasWithFilter(null, 0, 10);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(empresaRepository, times(1)).findAll();
    }

    @Test
    void allPagedEmpresasWithFilter_quandoNomeFornecido_entaoRetornaEmpresasFiltradas() {
        EmpresaModel empresa = new EmpresaModel();
        when(empresaRepository.findAllByNomeFantasiaStartingWithIgnoreCaseOrderByNomeFantasiaAsc("nome"))
                .thenReturn(Collections.singletonList(empresa));

        List<EmpresaCadastro> result = listarEmpresaService.allPagedEmpresasWithFilter("nome", 0, 10);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(empresaRepository, times(1))
                .findAllByNomeFantasiaStartingWithIgnoreCaseOrderByNomeFantasiaAsc("nome");
    }

    @Test
    void allPagedEmpresasWithFilter2_quandoNomeNaoFornecido_entaoRetornaTodasEmpresasOrdenadas() {
        EmpresaModel empresa = new EmpresaModel();
        when(empresaRepository.findAllOrderByNomeFantasiaAsc()).thenReturn(Collections.singletonList(empresa));

        List<EmpresaCadastro> result = listarEmpresaService.allPagedEmpresasWithFilter2(null, 0, 10);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(empresaRepository, times(1)).findAllOrderByNomeFantasiaAsc();
    }

    @Test
    void allPagedEmpresasWithFilter2_quandoNomeFornecido_entaoRetornaEmpresasFiltradas() {
        EmpresaModel empresa = new EmpresaModel();
        when(empresaRepository.findAllByNomeFantasiaStartingWithIgnoreCaseOrderByNomeFantasiaAsc("nome"))
                .thenReturn(Collections.singletonList(empresa));

        List<EmpresaCadastro> result = listarEmpresaService.allPagedEmpresasWithFilter2("nome", 0, 10);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(empresaRepository, times(1))
                .findAllByNomeFantasiaStartingWithIgnoreCaseOrderByNomeFantasiaAsc("nome");
    }
}
package com.ambientese.grupo5.EmpresaServiceTests;

import com.ambientese.grupo5.DTO.EmpresaRequest;
import com.ambientese.grupo5.Exception.ValidacaoException;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Services.EmpresaService.AtualizarEmpresaService;
import com.ambientese.grupo5.Services.EmpresaService.ValidacaoCamposObrigatoriosService;
import com.ambientese.grupo5.Services.EmpresaService.ValidacaoCNPJService;
import com.ambientese.grupo5.Services.EmpresaService.MapearEmpresaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;




@ExtendWith(MockitoExtension.class)
public class AtualizarEmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ValidacaoCamposObrigatoriosService validacaoCamposObrigatoriosService;

    @Mock
    private ValidacaoCNPJService validacaoCNPJService;

    @Mock
    private MapearEmpresaService mapearEmpresaService;

    @InjectMocks
    private AtualizarEmpresaService atualizarEmpresaService;

    @Test
    void atualizarEmpresa_quandoEmpresaNaoEncontrada_entaoLancaExcecao() {
        Long id = 1L;
        EmpresaRequest empresaRequest = new EmpresaRequest();
        when(empresaRepository.findById(id)).thenReturn(Optional.empty());
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            atualizarEmpresaService.atualizarEmpresa(id, empresaRequest);
        });
        assertNotNull(exception);
        assertEquals("Empresa não encontrada", exception.getMessage());
    }

    @Test
    void atualizarEmpresa_quandoCamposObrigatoriosNaoPreenchidos_entaoLancaExcecao() {
        Long id = 1L;
        EmpresaRequest empresaRequest = new EmpresaRequest();
        EmpresaModel empresaModel = new EmpresaModel();
        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresaModel));

        doThrow(new ValidacaoException("Campos obrigatórios não preenchidos"))
                .when(validacaoCamposObrigatoriosService).validarCamposObrigatorios(empresaRequest);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            atualizarEmpresaService.atualizarEmpresa(id, empresaRequest);
        });
        assertNotNull(exception);
        assertEquals("CNPJ já existe para outra empresa", exception.getMessage());
    }

    @Test
    void atualizarEmpresa_quandoCNPJDuplicado_entaoLancaExcecao() {
        Long id = 1L;
        EmpresaRequest empresaRequest = new EmpresaRequest();
        EmpresaModel empresaModel = new EmpresaModel();
        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresaModel));

        doThrow(new ValidacaoException("CNPJ já existe para outra empresa"))
                .when(validacaoCNPJService).validarCnpjUnico(empresaRequest.getCnpj(), id);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> atualizarEmpresaService.atualizarEmpresa(id, empresaRequest));
        assertNotNull(exception);
        assertEquals("CNPJ já existe para outra empresa", exception.getMessage());
    }

    @Test
    void atualizarEmpresa_quandoAtualizacaoBemSucedida_entaoRetornaEmpresaAtualizada() {
        Long id = 1L;
        EmpresaRequest empresaRequest = new EmpresaRequest();
        EmpresaModel empresaModel = new EmpresaModel();
        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresaModel));

        doNothing().when(validacaoCamposObrigatoriosService).validarCamposObrigatorios(empresaRequest);
        doNothing().when(validacaoCNPJService).validarCnpjUnico(empresaRequest.getCnpj(), id);
        doNothing().when(mapearEmpresaService).mapearEmpresa(empresaModel, empresaRequest);
        when(empresaRepository.save(empresaModel)).thenReturn(empresaModel);

        EmpresaModel result = atualizarEmpresaService.atualizarEmpresa(id, empresaRequest);

        assertEquals(empresaModel, result);
        verify(empresaRepository, times(1)).save(empresaModel);
        verify(validacaoCamposObrigatoriosService, times(1)).validarCamposObrigatorios(empresaRequest);
        verify(validacaoCNPJService, times(1)).validarCnpjUnico(empresaRequest.getCnpj(), id);
        verify(mapearEmpresaService, times(1)).mapearEmpresa(empresaModel, empresaRequest);
    }
}
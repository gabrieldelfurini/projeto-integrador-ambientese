package com.ambientese.grupo5.Services.EmpresaService;

import com.ambientese.grupo5.DTO.EmpresaRequest;
import com.ambientese.grupo5.Exception.ValidacaoException;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Model.EnderecoModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;
import com.ambientese.grupo5.Repository.EnderecoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CriarEmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ValidacaoCamposObrigatoriosService validacaoCamposObrigatoriosService;

    @Mock
    private ValidacaoCNPJService validacaoCNPJService;

    @Mock
    private MapearEmpresaService mapearEmpresaService;

    @Mock
    private MapearEnderecoService mapearEnderecoService;

    @InjectMocks
    private CriarEmpresaService criarEmpresaService;

    @Test
    void criarEmpresa_quandoCamposObrigatoriosNaoPreenchidos_entaoLancaExcecao() {
        EmpresaRequest empresaRequest = new EmpresaRequest();

        doThrow(new ValidacaoException("Campos obrigatórios não preenchidos"))
                .when(validacaoCamposObrigatoriosService).validarCamposObrigatorios(empresaRequest);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> criarEmpresaService.criarEmpresa(empresaRequest));
        assertEquals("Campos obrigatórios não preenchidos", exception.getMessage());
    }

    @Test
    void criarEmpresa_quandoCNPJDuplicado_entaoLancaExcecao() {
        EmpresaRequest empresaRequest = new EmpresaRequest();

        doThrow(new ValidacaoException("CNPJ já existe para outra empresa"))
                .when(validacaoCNPJService).validarCnpjUnico(empresaRequest.getCnpj(), null);

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> criarEmpresaService.criarEmpresa(empresaRequest));
        assertEquals("CNPJ já existe para outra empresa", exception.getMessage());
    }

    @Test
    void criarEmpresa_quandoCriacaoBemSucedida_entaoRetornaEmpresaCriada() {
        EmpresaRequest empresaRequest = new EmpresaRequest();
        EnderecoModel enderecoModel = new EnderecoModel();
        EmpresaModel empresaModel = new EmpresaModel();
        EmpresaModel empresaSalva = new EmpresaModel();

        doNothing().when(validacaoCamposObrigatoriosService).validarCamposObrigatorios(empresaRequest);
        doNothing().when(validacaoCNPJService).validarCnpjUnico(empresaRequest.getCnpj(), null);
        doNothing().when(mapearEnderecoService).mapearEndereco(enderecoModel, empresaRequest);
        when(enderecoRepository.save(enderecoModel)).thenReturn(enderecoModel);
        doNothing().when(mapearEmpresaService).mapearEmpresa(empresaModel, empresaRequest);
        when(empresaRepository.save(empresaModel)).thenReturn(empresaSalva);

        EmpresaModel result = criarEmpresaService.criarEmpresa(empresaRequest);

        assertEquals(empresaSalva, result);
        verify(validacaoCamposObrigatoriosService, times(1)).validarCamposObrigatorios(empresaRequest);
        verify(validacaoCNPJService, times(1)).validarCnpjUnico(empresaRequest.getCnpj(), null);
        verify(mapearEnderecoService, times(1)).mapearEndereco(enderecoModel, empresaRequest);
        verify(enderecoRepository, times(1)).save(enderecoModel);
        verify(mapearEmpresaService, times(1)).mapearEmpresa(empresaModel, empresaRequest);
        verify(empresaRepository, times(1)).save(empresaModel);
    }
}
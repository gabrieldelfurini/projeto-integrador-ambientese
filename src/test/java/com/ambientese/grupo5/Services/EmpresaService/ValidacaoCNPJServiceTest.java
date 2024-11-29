package com.ambientese.grupo5.Services.EmpresaService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import com.ambientese.grupo5.Exception.ValidacaoException;
import com.ambientese.grupo5.Model.EmpresaModel;
import com.ambientese.grupo5.Repository.EmpresaRepository;



public class ValidacaoCNPJServiceTest {

    @InjectMocks
    private ValidacaoCNPJService validacaoCNPJService;

    @Mock
    private EmpresaRepository empresaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsValidCnpj_ValidCnpj() {
        String validCnpj = "12345678000195";
        assertTrue(validacaoCNPJService.isValidCnpj(validCnpj));
    }

    @Test
    public void testIsValidCnpj_InvalidCnpj() {
        String invalidCnpj = "12345678000196";
        assertFalse(validacaoCNPJService.isValidCnpj(invalidCnpj));
    }

    @Test
    public void testIsValidCnpj_NullCnpj() {
        assertFalse(validacaoCNPJService.isValidCnpj(null));
    }

    @Test
    public void testIsValidCnpj_InvalidLengthCnpj() {
        String invalidLengthCnpj = "12345678";
        assertFalse(validacaoCNPJService.isValidCnpj(invalidLengthCnpj));
    }

    @Test
    public void testValidarCnpjUnico_CnpjUnico() {
        String cnpj = "12345678000195";
        Long empresaId = 1L;
        when(empresaRepository.findByCnpj(cnpj)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> validacaoCNPJService.validarCnpjUnico(cnpj, empresaId));
    }

    @Test
    public void testValidarCnpjUnico_CnpjDuplicado() {
        String cnpj = "12345678000195";
        Long empresaId = 1L;
        EmpresaModel empresaModel = new EmpresaModel();
        empresaModel.setId(2L);
        when(empresaRepository.findByCnpj(cnpj)).thenReturn(Optional.of(empresaModel));

        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> validacaoCNPJService.validarCnpjUnico(cnpj, empresaId));
        assertEquals("Já existe uma empresa cadastrada com este CNPJ", exception.getMessage());
    }
}
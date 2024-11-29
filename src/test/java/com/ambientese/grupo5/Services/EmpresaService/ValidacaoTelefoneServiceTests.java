package com.ambientese.grupo5.Services.EmpresaService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ValidacaoTelefoneServiceTests {

    @Autowired
    private ValidacaoTelefoneService validacaoTelefoneService;

    @Test
    public void testIsValidTelefone_NullTelefone() {
        assertFalse(validacaoTelefoneService.isValidTelefone(null));
    }

    @Test
    public void testIsValidTelefone_ValidTelefone() {
        assertTrue(validacaoTelefoneService.isValidTelefone("123456789"));
    }
}
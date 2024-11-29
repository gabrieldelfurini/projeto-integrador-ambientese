package com.ambientese.grupo5.Services.EmpresaService;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ValidacaoCEPServiceTest {

    private final ValidacaoCEPService validacaoCEPService = new ValidacaoCEPService();

    @Test
    public void testValidCepWithHyphen() {
        assertTrue(validacaoCEPService.isValidCep("12345-678"));
    }

    @Test
    public void testValidCepWithoutHyphen() {
        assertTrue(validacaoCEPService.isValidCep("12345678"));
    }

    @Test
    public void testInvalidCepWithLetters() {
        assertFalse(validacaoCEPService.isValidCep("12345-67a"));
    }

    @Test
    public void testInvalidCepWithSpecialCharacters() {
        assertFalse(validacaoCEPService.isValidCep("12345-67@"));
    }

    @Test
    public void testInvalidCepWithLessDigits() {
        assertFalse(validacaoCEPService.isValidCep("1234-567"));
    }

    @Test
    public void testInvalidCepWithMoreDigits() {
        assertFalse(validacaoCEPService.isValidCep("123456789"));
    }

    @Test
    public void testNullCep() {
        assertFalse(validacaoCEPService.isValidCep(null));
    }

    @Test
    public void testEmptyCep() {
        assertFalse(validacaoCEPService.isValidCep(""));
    }
}
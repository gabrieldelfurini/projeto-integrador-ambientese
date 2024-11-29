package com.ambientese.grupo5.Services.EmpresaService;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ValidacaoEmailServiceTest {

    private final ValidacaoEmailService validacaoEmailService = new ValidacaoEmailService();

    @Test
    public void testValidEmail() {
        assertTrue(validacaoEmailService.isValidEmail("test@example.com"));
    }

    @Test
    public void testInvalidEmailNoAtSymbol() {
        assertFalse(validacaoEmailService.isValidEmail("testexample.com"));
    }

    @Test
    public void testInvalidEmailNoDomain() {
        assertFalse(validacaoEmailService.isValidEmail("test@.com"));
    }

    @Test
    public void testInvalidEmailNoTLD() {
        assertFalse(validacaoEmailService.isValidEmail("test@example"));
    }

    @Test
    public void testInvalidEmailMultipleAtSymbols() {
        assertFalse(validacaoEmailService.isValidEmail("test@@example.com"));
    }

    @Test
    public void testInvalidEmailEmptyString() {
        assertFalse(validacaoEmailService.isValidEmail(""));
    }

    @Test
    public void testInvalidEmailNull() {
        assertFalse(validacaoEmailService.isValidEmail(null));
    }
}
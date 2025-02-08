package com.app.nfprocessor.batch;

import com.app.nfprocessor.model.NotaFiscal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class NotaFiscalProcessorTest {
    NotaFiscalProcessor processor = new NotaFiscalProcessor();

    @Test
    void deveValidarNotaFiscal() throws Exception {
        assertNotNull(processor.process(new NotaFiscal(1L, "12345678000195", 1000.0)));
        assertNull(processor.process(new NotaFiscal(2L, "1234567800019", 1000.0))); // CNPJ inválido
        assertNull(processor.process(new NotaFiscal(3L, "12345678000195", -500.0))); // Valor negativo
    }
}

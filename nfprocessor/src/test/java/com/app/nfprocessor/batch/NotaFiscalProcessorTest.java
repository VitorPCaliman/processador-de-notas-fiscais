package com.app.nfprocessor.batch;

import com.app.nfprocessor.model.NotaFiscal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotaFiscalProcessorTest {

    NotaFiscalProcessor processor = new NotaFiscalProcessor();

    @Test
    void deveValidarNotaFiscalComSucesso() throws Exception {
        NotaFiscal notaValida = processor.process(new NotaFiscal(1L, "12345678000195", 1000.0));
        assertNotNull(notaValida);
        assertEquals(1L, notaValida.getId());
        assertEquals("12345678000195", notaValida.getCnpj());
        assertEquals(1000.0, notaValida.getValor());
    }

    @Test
    void deveRetornarNuloParaCnpjInvalido() throws Exception {
        NotaFiscal notaCnpjInvalido = processor.process(new NotaFiscal(2L, "1234567800019", 1000.0));
        assertNull(notaCnpjInvalido);
    }

    @Test
    void deveRetornarNuloParaValorNegativo() throws Exception {
        NotaFiscal notaValorNegativo = processor.process(new NotaFiscal(3L, "12345678000195", -500.0));
        assertNull(notaValorNegativo);
    }

    @Test
    void deveRetornarNuloParaValorZero() throws Exception {
        NotaFiscal notaValorZero = processor.process(new NotaFiscal(4L, "12345678000195", 0.0));
        assertNull(notaValorZero);
    }
}

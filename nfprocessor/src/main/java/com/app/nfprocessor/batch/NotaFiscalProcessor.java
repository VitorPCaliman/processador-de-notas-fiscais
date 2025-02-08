package com.app.nfprocessor.batch;

import com.app.nfprocessor.model.NotaFiscal;
import com.app.nfprocessor.utils.CNPJUtils;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalProcessor implements ItemProcessor<NotaFiscal, NotaFiscal> {

    @Override
    public NotaFiscal process(NotaFiscal nota) throws Exception {
        if (!CNPJUtils.isCnpjValido(nota.getCnpj()) || nota.getValor() <= 0) {
            return null;
        }
        return nota;
    }
}

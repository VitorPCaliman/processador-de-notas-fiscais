package com.app.nfprocessor.batch;

import com.app.nfprocessor.model.NotaFiscal;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.PrintWriter;


@Component
public class NotaFiscalItemWriter implements ItemWriter<NotaFiscal> {

    private static final String VALID_FILE = "notas_validas.csv";
    private static final String INVALID_FILE = "notas_invalidas.csv";

    @Override
    public void write(Chunk<? extends NotaFiscal> chunk) throws Exception {
        try (PrintWriter validWriter = new PrintWriter(new FileWriter(VALID_FILE, true));
             PrintWriter invalidWriter = new PrintWriter(new FileWriter(INVALID_FILE, true))) {

            for (NotaFiscal nota : chunk.getItems()) {
                if (nota != null) {
                    validWriter.println(nota.getId() + "," + nota.getCnpj() + "," + nota.getValor());
                } else {
                    invalidWriter.println("Nota inválida encontrada.");
                }
            }
        }
    }
}


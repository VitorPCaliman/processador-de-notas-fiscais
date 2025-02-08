package com.app.nfprocessor.runner;

import com.app.nfprocessor.service.NotaFiscalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotaFiscalCommandLineRunner implements CommandLineRunner {

    private final NotaFiscalService notaFiscalService;

    public NotaFiscalCommandLineRunner(NotaFiscalService notaFiscalService) {
        this.notaFiscalService = notaFiscalService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Iniciando processamento de notas fiscais...");
        notaFiscalService.processarNotasFiscais();
        System.out.println("Processamento finalizado.");
    }
}

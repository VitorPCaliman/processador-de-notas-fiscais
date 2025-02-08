package com.app.nfprocessor.service;

import com.app.nfprocessor.batch.NotaFiscalProcessor;
import com.app.nfprocessor.model.NotaFiscal;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotaFiscalService {

    private final NotaFiscalProcessor processor;

    @Value("${input.file}")
    private Resource inputFile;

    @Value("${output.validas}")
    private String validFilePath;

    @Value("${output.invalidas}")
    private String invalidFilePath;


    public NotaFiscalService(NotaFiscalProcessor processor) {
        this.processor = processor;
    }

    public void processarNotasFiscais() {
        List<NotaFiscal> notasValidas = new ArrayList<>();
        List<NotaFiscal> notasInvalidas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile.getInputStream()))) {
            String linha;

            reader.readLine();

            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");

                Long id = Long.parseLong(dados[0].trim());
                String cnpj = dados[1].trim();
                Double valor = Double.parseDouble(dados[2].trim());

                NotaFiscal nota = new NotaFiscal(id, cnpj, valor);

                NotaFiscal notaProcessada = processor.process(nota);
                if (notaProcessada != null) {
                    notasValidas.add(notaProcessada);
                } else {
                    notasInvalidas.add(nota);
                }
            }

            gerarArquivoCSV(validFilePath, notasValidas);
            gerarArquivoCSV(invalidFilePath, notasInvalidas);

            System.out.println("Processamento concluído. Arquivos gerados em:");
            System.out.println("Notas válidas: " + validFilePath);
            System.out.println("Notas inválidas: " + invalidFilePath);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro na validação das notas fiscais: " + e.getMessage(), e);
        }
    }

    private void gerarArquivoCSV(String filePath, List<NotaFiscal> notas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,cnpj,valor");
            writer.newLine();
            for (NotaFiscal nota : notas) {
                writer.write(nota.getId() + "," + nota.getCnpj() + "," + nota.getValor());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gerar arquivo CSV: " + e.getMessage(), e);
        }
    }
}

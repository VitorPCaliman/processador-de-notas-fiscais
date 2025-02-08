package com.app.nfprocessor.model;

import lombok.*;

@Data
public class NotaFiscal {

    private Long id;
    private String cnpj;
    private Double valor;

    public NotaFiscal(Long id, String cnpj, Double valor) {
        this.id = id;
        this.cnpj = cnpj;
        this.valor = valor;
    }

    public NotaFiscal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }


}

package br.com.diego.olabi.dto;

import lombok.Data;

@Data
public class TransacaoDTO {
    private double valor;

    public TransacaoDTO(double valor) {
        this.valor = valor;
    }

    public TransacaoDTO() {}
}

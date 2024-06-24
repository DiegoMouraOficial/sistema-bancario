package br.com.diego.olabi.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TransferenciaDTO {
    private UUID contaOrigemId;
    private UUID contaDestinoId;
    private double valor;

    public TransferenciaDTO(UUID contaOrigemId, double valor, UUID contaDestinoId) {
        this.contaOrigemId = contaOrigemId;
        this.valor = valor;
        this.contaDestinoId = contaDestinoId;
    }

    public TransferenciaDTO(){}
}

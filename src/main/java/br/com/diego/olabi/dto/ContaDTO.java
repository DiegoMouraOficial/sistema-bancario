package br.com.diego.olabi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ContaDTO {
    private UUID id;
    private String tipoConta;
    private Double limiteDeCredito;
    private Double saldo;
    private UUID clienteId;
    private String clienteNome;
    private String clienteCPF;

    public ContaDTO(UUID id, String clienteNome, String clienteCPF, UUID clienteId, Double saldo, Double limiteDeCredito, String tipoConta) {
        this.id = id;
        this.clienteNome = clienteNome;
        this.clienteCPF = clienteCPF;
        this.clienteId = clienteId;
        this.saldo = saldo;
        this.limiteDeCredito = limiteDeCredito;
        this.tipoConta = tipoConta;
    }

    public ContaDTO(){}
}

package br.com.diego.olabi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AtualizarContaRequest {
    private String tipoConta;
    private Double limiteDeCredito;
}

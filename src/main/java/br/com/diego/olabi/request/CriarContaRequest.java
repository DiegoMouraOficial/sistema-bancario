package br.com.diego.olabi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CriarContaRequest {

    private String tipoConta;
    private double rendaMensalCliente;
}

package br.com.diego.olabi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferenciaRequest {
    private UUID contaDestinoId;
    private Double valor;
}

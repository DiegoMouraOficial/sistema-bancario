package br.com.diego.olabi.model;

import br.com.diego.olabi.enums.TipoConta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Table(name = "contas")
public class Conta {
    @Id
    @UuidGenerator
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta")
    private TipoConta tipoConta;

    @Column(name = "limite_de_credito", nullable = false, columnDefinition = "float default 0.0")
    private Double limiteDeCredito;

    @Column(name = "saldo")
    private double saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonBackReference // Indica que esta é a parte que não deve ser serializada
    private Cliente cliente;

    public Conta(Cliente cliente, double saldo, Double limiteDeCredito, TipoConta tipoConta) {
        this.cliente = cliente;
        this.saldo = saldo;
        this.limiteDeCredito = limiteDeCredito;
        this.tipoConta = tipoConta;
    }

    public Conta(){}

    @Override
    public int hashCode() {
        return Objects.hash(id); // Exemplo simples usando apenas o ID
    }
}

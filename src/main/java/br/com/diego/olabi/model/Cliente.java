package br.com.diego.olabi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "clientes")
public class Cliente {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty("rendaSalarial")
    private double rendaMensal;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "endereco")
    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Indica que esta é a parte gerenciada da relação
    private Set<Conta> contas = new HashSet<>();

    //region ...Constructor
    public Cliente(){}

    public Cliente(String nome, String cpf, String email, double rendaMensal, String senha, String endereco, Set<Conta> contas) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.rendaMensal = rendaMensal;
        this.senha = senha;
        this.endereco = endereco;
        this.contas = contas;
    }
    //endregion

    // Implementação personalizada de hashCode e equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return Double.compare(rendaMensal, cliente.rendaMensal) == 0 && Objects.equals(id, cliente.id) && Objects.equals(nome, cliente.nome) && Objects.equals(cpf, cliente.cpf) && Objects.equals(email, cliente.email) && Objects.equals(senha, cliente.senha) && Objects.equals(endereco, cliente.endereco) && Objects.equals(contas, cliente.contas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf, email, rendaMensal, senha, endereco, contas);
    }
}

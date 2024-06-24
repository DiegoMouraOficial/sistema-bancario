package br.com.diego.olabi.repository;

import br.com.diego.olabi.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Cliente findByCpf(String cpf);
}

package br.com.diego.olabi.repository;

import br.com.diego.olabi.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ContaRepository extends JpaRepository<Conta, UUID> {
    List<Conta> findByClienteId(UUID clienteId);
}

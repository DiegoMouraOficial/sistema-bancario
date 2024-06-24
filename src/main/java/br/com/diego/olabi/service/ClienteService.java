package br.com.diego.olabi.service;

import br.com.diego.olabi.model.Cliente;
import br.com.diego.olabi.model.Conta;
import br.com.diego.olabi.repository.ClienteRepository;
import br.com.diego.olabi.repository.ContaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;


    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarClientePorCpf(String cpf) {
        logger.info("Buscando cliente por CPF: {}", cpf);
        Optional<Cliente> cliente = Optional.ofNullable(clienteRepository.findByCpf(cpf));
        if (cliente.isEmpty()) {
            logger.error("Cliente não encontrado com CPF: {}", cpf);
        }
        return cliente;
    }

    public Optional<Cliente> buscarClientePorId(UUID id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente atualizarCliente(UUID id, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        // Atualiza os dados do cliente existente com os dados do cliente atualizado
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setSenha(clienteAtualizado.getSenha());
        clienteExistente.setEndereco(clienteAtualizado.getEndereco());
        clienteExistente.setRendaMensal(clienteAtualizado.getRendaMensal());

        // Atualiza as contas associadas ao cliente
        for (Conta conta : clienteAtualizado.getContas()) {
            // Verifica se a conta existe no cliente existente
            Conta contaExistente = clienteExistente.getContas().stream()
                    .filter(c -> c.getId().equals(conta.getId()))
                    .findFirst()
                    .orElse(null);

            if (contaExistente != null) {
                // Atualiza os dados da conta existente
                contaExistente.setTipoConta(conta.getTipoConta());
                contaExistente.setLimiteDeCredito(conta.getLimiteDeCredito());
                contaExistente.setSaldo(conta.getSaldo());
            } else {
                // Adiciona a nova conta ao cliente existente
                clienteExistente.getContas().add(conta);
            }
        }

        // Salva e retorna o cliente atualizado
        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    public boolean deletarCliente(UUID clienteId) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteId);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            // Remover todas as contas associadas ao cliente
            for (Conta conta : cliente.getContas()) {
                contaRepository.delete(conta);
            }

            // Deletar o cliente após ter removido todas as contas associadas
            clienteRepository.delete(cliente);

            return true; // Indica que o cliente foi deletado com sucesso
        } else {
            return false; // Indica que o cliente não foi encontrado
        }
    }
}

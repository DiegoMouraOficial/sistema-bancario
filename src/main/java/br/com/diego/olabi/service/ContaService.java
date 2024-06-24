package br.com.diego.olabi.service;

import br.com.diego.olabi.enums.TipoConta;
import br.com.diego.olabi.request.AtualizarContaRequest;
import br.com.diego.olabi.model.Cliente;
import br.com.diego.olabi.model.Conta;
import br.com.diego.olabi.request.CriarContaRequest;
import br.com.diego.olabi.repository.ClienteRepository;
import br.com.diego.olabi.repository.ContaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Conta criarConta(UUID clienteId, CriarContaRequest request) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));

        // Converter tipoConta para enum TipoConta
        TipoConta tipoConta = TipoConta.valueOf(request.getTipoConta());

        // Verifica se o cliente já possui conta do mesmo tipo
        if (clienteJaPossuiConta(cliente, tipoConta)) {
            throw new RuntimeException("O cliente já possui uma conta " + tipoConta.name() + ".");
        }

        Conta conta = new Conta(cliente, 0.0, 0.0, tipoConta);

        // Define o limite de crédito de acordo com o tipo de conta e a renda mensal do cliente
        definirLimiteDeCredito(conta, tipoConta, request.getRendaMensalCliente());

        // Salva conta no repositório
        conta = contaRepository.save(conta);

        // Adiciona a conta ao cliente e atualiza o cliente
        cliente.getContas().add(conta);
        clienteRepository.save(cliente);

        return conta;
    }

    public Conta buscarContaPorId(UUID contaId) {
        return contaRepository.findById(contaId)
                .orElse(null);
    }

    private void definirLimiteDeCredito(Conta conta, TipoConta tipoConta, double rendaMensalCliente) {
        switch (tipoConta) {
            case CONTA_CORRENTE_BRONZE:
                if (rendaMensalCliente <= 3000.0) {
                    conta.setLimiteDeCredito(1500.0);
                } else {
                    throw new RuntimeException("Renda mensal superior ao limite para conta bronze.");
                }
                break;
            case CONTA_CORRENTE_PRATA:
                if (rendaMensalCliente <= 6000.0) {
                    conta.setLimiteDeCredito(2800.0);
                } else {
                    throw new RuntimeException("Renda mensal superior ao limite para conta prata.");
                }
                break;
            case CONTA_CORRENTE_OURO:
                if (rendaMensalCliente <= 10000.0) {
                    conta.setLimiteDeCredito(5500.0);
                } else {
                    throw new RuntimeException("Renda mensal superior ao limite para conta ouro.");
                }
                break;
            case CONTA_CORRENTE_BLACK:
                if (rendaMensalCliente <= 20000.0) {
                    conta.setLimiteDeCredito(10500.0);
                } else {
                    throw new RuntimeException("Renda mensal superior ao limite para conta black.");
                }
                break;
            default:
                throw new RuntimeException("Tipo de conta não suportado: " + tipoConta.name());
        }
    }

    public Conta atualizarConta(UUID contaId, AtualizarContaRequest request) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        if (request.getTipoConta() != null) {
            conta.setTipoConta(TipoConta.valueOf(request.getTipoConta())); // Certifique-se de que o valor do tipo de conta é válido
        }
        if (request.getLimiteDeCredito() != null) {
            conta.setLimiteDeCredito(request.getLimiteDeCredito());
        }

        return contaRepository.save(conta);
    }

    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public void excluirConta(UUID contaId) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada com ID: " + contaId));

        contaRepository.delete(conta);
    }

    private boolean clienteJaPossuiConta(Cliente cliente, TipoConta tipoConta) {
        return cliente.getContas().stream()
                .anyMatch(conta -> conta.getTipoConta() == tipoConta);
    }

    // Método para depósito
    public Conta depositar(UUID contaId, Double valor) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }

        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }

    // Método para saque
    public Conta sacar(UUID contaId, Double valor) {
        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }

        if (conta.getSaldo() < valor) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        conta.setSaldo(conta.getSaldo() - valor);
        return contaRepository.save(conta);
    }

    // Método para transferência
    public Conta transferir(UUID contaOrigemId, UUID contaDestinoId, Double valor) {
        Conta contaOrigem = contaRepository.findById(contaOrigemId)
                .orElseThrow(() -> new EntityNotFoundException("Conta de origem não encontrada: " + contaOrigemId));
        Conta contaDestino = contaRepository.findById(contaDestinoId)
                .orElseThrow(() -> new EntityNotFoundException("Conta de destino não encontrada: " + contaDestinoId));

        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        if (contaOrigem.getSaldo() < valor) {
            throw new IllegalArgumentException("Saldo insuficiente na conta de origem.");
        }

        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
        contaDestino.setSaldo(contaDestino.getSaldo() + valor);

        contaRepository.save(contaOrigem);
        return contaRepository.save(contaDestino);
    }
}

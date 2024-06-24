package br.com.diego.olabi.controller;

import br.com.diego.olabi.request.AtualizarContaRequest;
import br.com.diego.olabi.model.Conta;
import br.com.diego.olabi.dto.ContaDTO;
import br.com.diego.olabi.request.CriarContaRequest;
import br.com.diego.olabi.request.OperacaoRequest;
import br.com.diego.olabi.request.TransferenciaRequest;
import br.com.diego.olabi.service.ClienteService;
import br.com.diego.olabi.service.ContaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/criar-conta/{clienteId}")
    public ResponseEntity<Conta> criarConta(@PathVariable UUID clienteId, @RequestBody CriarContaRequest request) {
        // Valida se tipoConta não é nulo
        if (request.getTipoConta() == null) {
            throw new IllegalArgumentException("O parâmetro 'tipoConta' não pode ser nulo.");
        }

        /// Chama o serviço para criar a conta
        Conta conta = contaService.criarConta(clienteId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    @PutMapping("/atualizar/{contaId}")
    public ResponseEntity<Conta> atualizarConta(@PathVariable UUID contaId, @RequestBody AtualizarContaRequest request) {
        try {
            Conta contaAtualizada = contaService.atualizarConta(contaId, request);
            return ResponseEntity.ok(contaAtualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/{contaId}")
    public ResponseEntity<ContaDTO> buscarContaPorId(@PathVariable UUID contaId) {
        Conta conta = contaService.buscarContaPorId(contaId);

        if (conta != null) {
            ContaDTO contaDTO = convertToDTO(conta);
            return ResponseEntity.ok(contaDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ContaDTO>> listarContas() {
        List<Conta> contas = contaService.listarContas();
        List<ContaDTO> contasDTO = contas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(contasDTO);
    }

    @DeleteMapping("/id/{contaId}")
    public ResponseEntity<Void> excluirConta(@PathVariable UUID contaId) {
        contaService.excluirConta(contaId);
        return ResponseEntity.noContent().build();
    }

    // Método para depósito
    @PostMapping("/deposito/{contaId}")
    public ResponseEntity<Conta> depositar(@PathVariable UUID contaId, @RequestBody OperacaoRequest request) {
        try {
            Conta contaAtualizada = contaService.depositar(contaId, request.getValor());
            return ResponseEntity.ok(contaAtualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Método para saque
    @PostMapping("/saque/{contaId}")
    public ResponseEntity<Conta> sacar(@PathVariable UUID contaId, @RequestBody OperacaoRequest request) {
        try {
            Conta contaAtualizada = contaService.sacar(contaId, request.getValor());
            return ResponseEntity.ok(contaAtualizada);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Método para transferência
    @PostMapping("/transferencia/{contaId}")
    public ResponseEntity<Conta> transferir(@PathVariable UUID contaId, @RequestBody TransferenciaRequest request) {
        try {
            System.out.println("Iniciando transferência de " + contaId + " para " + request.getContaDestinoId() + " no valor de " + request.getValor());
            Conta contaAtualizada = contaService.transferir(contaId, request.getContaDestinoId(), request.getValor());
            System.out.println("Transferência concluída com sucesso.");
            return ResponseEntity.ok(contaAtualizada);
        } catch (EntityNotFoundException e) {
            System.out.println("Erro: " + e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    private ContaDTO convertToDTO(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setTipoConta(String.valueOf(conta.getTipoConta()));
        dto.setLimiteDeCredito(conta.getLimiteDeCredito());
        dto.setSaldo(conta.getSaldo());
        dto.setClienteId(conta.getCliente().getId()); // Exemplo de como obter o ID do cliente
        dto.setClienteNome(conta.getCliente().getNome());
        dto.setClienteCPF(conta.getCliente().getCpf());

        return dto;
    }

}

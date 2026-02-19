package br.com.clinica.service;

import br.com.clinica.model.*;
import br.com.clinica.repository.PacienteRepository;
import br.com.clinica.repository.PagamentoRepository;

import java.time.LocalDate;
import java.util.List;

public class PagamentoService {

    private final PagamentoRepository pagamentoRepository = new PagamentoRepository();
    private final PacienteRepository pacienteRepository = new PacienteRepository();

    public void registrarEntrada(String cpf, Double valor, String descricao) {

        Paciente paciente = pacienteRepository.buscarPorCpf(cpf);

        if (paciente == null) {
            System.out.println("❌ Paciente não encontrado.");
            return;
        }

        Pagamento pagamento = new Pagamento(
                TipoPagamento.ENTRADA,
                valor,
                descricao,
                paciente
        );

        pagamentoRepository.salvar(pagamento);

        paciente.setPagamentoEmDia(true);
        pacienteRepository.atualizar(paciente);

        System.out.println("✅ Pagamento registrado e paciente liberado.");
    }

    public void registrarSaida(Double valor, String descricao) {

        Pagamento pagamento = new Pagamento(
                TipoPagamento.SAIDA,
                valor,
                descricao,
                null
        );

        pagamentoRepository.salvar(pagamento);

        System.out.println("✅ Saída registrada.");
    }

    public void fechamentoDoDia() {

        List<Pagamento> lista = pagamentoRepository.buscarPorData(LocalDate.now());

        double entradas = 0;
        double saidas = 0;

        for (Pagamento p : lista) {
            if (p.getTipo() == TipoPagamento.ENTRADA) {
                entradas += p.getValor();
            } else {
                saidas += p.getValor();
            }
        }

        System.out.println("\n--- FECHAMENTO DO DIA ---");
        System.out.println("Entradas: R$ " + entradas);
        System.out.println("Saídas: R$ " + saidas);
        System.out.println("Saldo: R$ " + (entradas - saidas));
    }
}

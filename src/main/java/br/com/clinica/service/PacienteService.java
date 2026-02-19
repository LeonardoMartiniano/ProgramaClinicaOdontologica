package br.com.clinica.service;

import br.com.clinica.model.Paciente;
import br.com.clinica.repository.PacienteRepository;

public class PacienteService {
    private final PacienteRepository repository = new PacienteRepository();

    public void cadastrarPaciente(String nome, String cpf, String telefone) {

        if (repository.buscarPorCpf(cpf) != null) {
            System.out.println("❌ Já existe paciente com esse CPF.");
            return;
        }

        Paciente paciente = new Paciente(nome, cpf, telefone);
        repository.salvar(paciente);

        System.out.println("✅ Paciente cadastrado com sucesso!");
    }

    public Paciente buscarPorCpf(String cpf) {
        return repository.buscarPorCpf(cpf);
    }
}

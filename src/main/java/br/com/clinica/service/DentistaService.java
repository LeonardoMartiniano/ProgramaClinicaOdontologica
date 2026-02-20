package br.com.clinica.service;

import br.com.clinica.model.Dentista;
import br.com.clinica.repository.DentistaRepository;

import java.util.List;

public class DentistaService {
    private final DentistaRepository dentistaRepository = new DentistaRepository();

    public void cadastrarDentista(String nome, String cro, String telefone) {
        Dentista dentista = new Dentista(nome, cro, telefone);
        dentistaRepository.salvar(dentista);
        System.out.println("✅ Dentista cadastrado.");
    }

    public List<Dentista> listarDentistas() {
        return dentistaRepository.listarTodos();
    }
}

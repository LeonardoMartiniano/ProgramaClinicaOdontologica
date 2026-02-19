package br.com.clinica.service;

import br.com.clinica.model.Dentista;
import br.com.clinica.repository.DentistaRepository;

public class DentistaService {
    private final DentistaRepository repository = new DentistaRepository();

    public void cadastrarDentista(String nome, String cro, String telefone) {

        if (repository.buscarPorCro(cro) != null) {
            System.out.println("❌ Já existe dentista com esse CRO.");
            return;
        }

        Dentista dentista = new Dentista(nome, cro, telefone);
        repository.salvar(dentista);

        System.out.println("✅ Dentista cadastrado com sucesso!");
    }
}

package br.com.clinica.application;

import br.com.clinica.config.JPAUtil;
import br.com.clinica.menu.MenuPrincipal;
import br.com.clinica.model.Paciente;
import jakarta.persistence.EntityManager;


public class main {
    public static void main(String[] args) {

        MenuPrincipal.exibirMenu();
    }
}


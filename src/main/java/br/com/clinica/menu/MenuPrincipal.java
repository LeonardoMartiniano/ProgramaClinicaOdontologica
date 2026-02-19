package br.com.clinica.menu;

import br.com.clinica.model.StatusConsulta;
import br.com.clinica.service.ConsultaService;
import br.com.clinica.service.PacienteService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import br.com.clinica.service.DentistaService;
import br.com.clinica.service.PagamentoService;


public class MenuPrincipal {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DentistaService dentistaService = new DentistaService();
    private static final PacienteService pacienteService = new PacienteService();
    private static final PagamentoService pagamentoService = new PagamentoService();
    private static final ConsultaService consultaService = new ConsultaService();

    public static void exibirMenu() {

        int opcao;

        do {
            System.out.println("\n=== CLÍNICA ODONTOLÓGICA ===");
            System.out.println("1 - Cadastrar paciente");
            System.out.println("2 - Cadastrar dentista");
            System.out.println("3 - Registrar entrada (pagamento paciente)");
            System.out.println("4 - Registrar saída (despesa)");
            System.out.println("5 - Fechamento do dia");
            System.out.println("6 - Agendar consulta");
            System.out.println("7 - Consultas do dia");
            System.out.println("8 - Consultas do paciente");
            System.out.println("9 - Atualizar status da consulta");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarPaciente();
                case 2 -> cadastrarDentista();
                case 3 -> registrarEntrada();
                case 4 -> registrarSaida();
                case 5 -> pagamentoService.fechamentoDoDia();
                case 6 -> agendarConsulta();
                case 7 -> consultasDoDia();
                case 8 -> consultasDoPaciente();
                case 9 -> atualizarStatusConsulta();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void cadastrarPaciente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        pacienteService.cadastrarPaciente(nome, cpf, telefone);
    }
    private static void cadastrarDentista(){
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CRO: ");
        String cro = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        dentistaService.cadastrarDentista(nome, cro, telefone);
    }
    private static void registrarEntrada() {

        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();

        System.out.print("Valor: ");
        Double valor = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        pagamentoService.registrarEntrada(cpf, valor, descricao);
    }
    private static void registrarSaida() {

        System.out.print("Valor: ");
        Double valor = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        pagamentoService.registrarSaida(valor, descricao);
    }
    private static void agendarConsulta() {

        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();

        System.out.print("CRO do dentista: ");
        String cro = scanner.nextLine();

        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        System.out.print("Hora (HH:MM): ");
        LocalTime hora = LocalTime.parse(scanner.nextLine());

        System.out.print("Observação: ");
        String obs = scanner.nextLine();

        consultaService.agendarConsulta(cpf, cro, data, hora, obs);
    }

    private static void consultasDoDia() {

        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        var lista = consultaService.consultasDoDia(data);

        for (var c : lista) {
            System.out.println(
                    "ID: " + c.getId() +
                            " | Paciente: " + c.getPaciente().getNome() +
                            " | Dentista: " + c.getDentista().getNome() +
                            " | Hora: " + c.getHora() +
                            " | Status: " + c.getStatus()
            );
        }
    }

    private static void consultasDoPaciente() {

        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();

        var lista = consultaService.consultasPorPaciente(cpf);

        for (var c : lista) {
            System.out.println(
                    "ID: " + c.getId() +
                            " | Data: " + c.getData() +
                            " | Hora: " + c.getHora() +
                            " | Dentista: " + c.getDentista().getNome() +
                            " | Status: " + c.getStatus()
            );
        }
    }

    private static void atualizarStatusConsulta() {

        System.out.print("ID da consulta: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.println("1 - REALIZADA");
        System.out.println("2 - CANCELADA");
        System.out.print("Escolha: ");
        int op = Integer.parseInt(scanner.nextLine());

        var status = (op == 1)
                ? StatusConsulta.REALIZADA
                : StatusConsulta.CANCELADA;

        consultaService.atualizarStatus(id, status);
    }
}

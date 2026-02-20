package br.com.clinica.menu;

import br.com.clinica.model.Consulta;
import br.com.clinica.model.Dentista;
import br.com.clinica.model.Paciente;
import br.com.clinica.model.StatusConsulta;
import br.com.clinica.service.ConsultaService;
import br.com.clinica.service.PacienteService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import br.com.clinica.service.DentistaService;
import br.com.clinica.service.PagamentoService;
import br.com.clinica.util.AgendaUtil;


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
            System.out.println("10 - Listar pacientes");
            System.out.println("11 - Listar dentistas");
            System.out.println("12 - Agenda do dia");
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
                case 10 -> listarPacientes();
                case 11 -> listarDentistas();
                case 12 -> agendaDoDia();
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

        List<Dentista> dentistas = dentistaService.listarDentistas();

        if (dentistas.isEmpty()) {
            System.out.println("❌ Nenhum dentista cadastrado.");
            return;
        }

        System.out.println("Dentistas disponíveis:");
        for (int i = 0; i < dentistas.size(); i++) {
            Dentista d = dentistas.get(i);
            System.out.println((i + 1) + " - " + d.getNome() + " (CRO: " + d.getCro() + ")");
        }

        System.out.print("Escolha o dentista: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > dentistas.size()) {
            System.out.println("❌ Opção inválida.");
            return;
        }

        Dentista dentistaEscolhido = dentistas.get(escolha - 1);

        // =============================
        // GERAR DIAS DISPONÍVEIS (30 dias)
        // =============================
        LocalDate hoje = LocalDate.now();
        List<LocalDate> diasDisponiveis = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            LocalDate dia = hoje.plusDays(i);
            if (dia.getDayOfWeek() != DayOfWeek.SUNDAY) {
                diasDisponiveis.add(dia);
            }
        }

        System.out.println("\nDias disponíveis:");
        for (int i = 0; i < diasDisponiveis.size(); i++) {
            System.out.println((i + 1) + " - " + diasDisponiveis.get(i));
        }

        System.out.print("Escolha o dia: ");
        int diaEscolhidoIndex = scanner.nextInt();
        scanner.nextLine();

        if (diaEscolhidoIndex < 1 || diaEscolhidoIndex > diasDisponiveis.size()) {
            System.out.println("❌ Dia inválido.");
            return;
        }

        LocalDate data = diasDisponiveis.get(diaEscolhidoIndex - 1);
        DayOfWeek dow = data.getDayOfWeek();

        // =============================
        // ESCOLHER TURNO
        // =============================
        System.out.println("Turnos disponíveis:");
        System.out.println("1 - Manhã (08:00 às 12:00)");

        if (dow != DayOfWeek.SATURDAY) {
            System.out.println("2 - Tarde (13:00 às 17:00)");
        }

        System.out.print("Escolha o turno: ");
        int turno = scanner.nextInt();
        scanner.nextLine();

        List<LocalTime> horariosTurno = new ArrayList<>();

        if (turno == 1) {
            for (int h = 8; h < 12; h++) {
                horariosTurno.add(LocalTime.of(h, 0));
            }
        } else if (turno == 2 && dow != DayOfWeek.SATURDAY) {
            for (int h = 13; h < 17; h++) {
                horariosTurno.add(LocalTime.of(h, 0));
            }
        } else {
            System.out.println("❌ Turno inválido.");
            return;
        }

        // =============================
        // FILTRAR HORÁRIOS COM LIMITE 4 PACIENTES
        // =============================
        List<LocalTime> horariosDisponiveis = new ArrayList<>();

        for (LocalTime h : horariosTurno) {

            int total = consultaService.contarConsultasNoHorario(
                    data,
                    dentistaEscolhido.getCro(),
                    h
            );

            if (total < 4) {
                horariosDisponiveis.add(h);
            }
        }

        if (horariosDisponiveis.isEmpty()) {
            System.out.println("❌ Nenhum horário disponível nesse turno.");
            return;
        }

        // =============================
        // MOSTRAR HORÁRIOS DISPONÍVEIS
        // =============================
        System.out.println("\nHorários disponíveis:");
        for (int i = 0; i < horariosDisponiveis.size(); i++) {
            System.out.println((i + 1) + " - " + horariosDisponiveis.get(i));
        }

        System.out.print("Escolha o horário: ");
        int hIndex = scanner.nextInt();
        scanner.nextLine();

        if (hIndex < 1 || hIndex > horariosDisponiveis.size()) {
            System.out.println("❌ Horário inválido.");
            return;
        }

        LocalTime horaEscolhida = horariosDisponiveis.get(hIndex - 1);

        System.out.print("Observação: ");
        String obs = scanner.nextLine();

        consultaService.agendarConsulta(
                cpf,
                dentistaEscolhido.getCro(),
                data,
                horaEscolhida,
                obs
        );
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
    private static void listarPacientes() {

        List<Paciente> lista = pacienteService.listarPacientes();

        if (lista.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        System.out.println("\n--- PACIENTES ---");

        for (Paciente p : lista) {
            String status = p.isPagamentoEmDia() ? "Em dia" : "Em atraso";

            System.out.println(
                    "Nome: " + p.getNome() +
                            " | CPF: " + p.getCpf() +
                            " | Tel: " + p.getTelefone() +
                            " | Pagamento: " + status
            );
        }
    }
    private static void listarDentistas() {

        List<Dentista> lista = dentistaService.listarDentistas();

        if (lista.isEmpty()) {
            System.out.println("Nenhum dentista cadastrado.");
            return;
        }

        System.out.println("\n--- DENTISTAS ---");

        for (Dentista d : lista) {
            System.out.println(
                    "Nome: " + d.getNome() +
                            " | CRO: " + d.getCro()
            );
        }
    }
    private static void agendaDoDia() {

        LocalDate hoje = LocalDate.now();

        List<Consulta> consultas = consultaService.buscarAgendaDoDia(hoje);

        System.out.println("\n=== AGENDA DO DIA " + hoje + " ===");

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }

        for (Consulta c : consultas) {

            System.out.println(
                    c.getHora() + " | " +
                            c.getDentista().getNome() + " | " +
                            c.getPaciente().getNome() + " | " +
                            c.getStatus()
            );
        }
    }

}

package br.com.clinica.service;

import br.com.clinica.model.Consulta;
import br.com.clinica.model.Dentista;
import br.com.clinica.model.Paciente;
import br.com.clinica.model.StatusConsulta;
import br.com.clinica.repository.ConsultaRepository;
import br.com.clinica.repository.DentistaRepository;
import br.com.clinica.repository.PacienteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaService {
    private final ConsultaRepository consultaRepository = new ConsultaRepository();
    private final PacienteRepository pacienteRepository = new PacienteRepository();
    private final DentistaRepository dentistaRepository = new DentistaRepository();


    public void agendarConsulta(String cpf, String cro, LocalDate data, LocalTime hora, String obs){

        Dentista dentista = dentistaRepository.buscarPorCro(cro);
        Paciente paciente = pacienteRepository.buscarPorCpf(cpf);

        if (dentista == null || paciente == null) {
            System.out.println("❌ Paciente ou dentista não encontrado.");
            return;
        }

        // 🔒 REGRA: máximo 4 consultas no mesmo horário
        long totalNoHorario = consultaRepository.contarPorDentistaDataHora(
                dentista, data, hora
        );

        if (totalNoHorario >= 4) {
            System.out.println("❌ Horário cheio (máximo 4 pacientes). Escolha outro.");
            return;
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setDentista(dentista);
        consulta.setData(data);
        consulta.setHora(hora);
        consulta.setObservacao(obs);

        consultaRepository.salvar(consulta);

        System.out.println("✅ Consulta agendada com sucesso!");
    }

    public List<Consulta> consultasDoDia(LocalDate data) {
        return consultaRepository.buscarPorData(data);
    }

    public List<Consulta> consultasPorPaciente(String cpf) {
        return consultaRepository.buscarPorCpfPaciente(cpf);
    }

    public void atualizarStatus(Long idConsulta, StatusConsulta status) {

        Consulta consulta = consultaRepository.buscarPorId(idConsulta);

        if (consulta == null) {
            System.out.println("❌ Consulta não encontrada.");
            return;
        }

        consulta.setStatus(status);
        consultaRepository.atualizar(consulta);

        System.out.println("✅ Status atualizado.");
    }
    public List<LocalTime> gerarHorariosManha() {
        List<LocalTime> lista = new ArrayList<>();
        for (int h = 8; h < 12; h++) {
            lista.add(LocalTime.of(h, 0));
        }
        return lista;
    }

    public List<LocalTime> gerarHorariosTarde() {
        List<LocalTime> lista = new ArrayList<>();
        for (int h = 13; h < 17; h++) {
            lista.add(LocalTime.of(h, 0));
        }
        return lista;
    }
    public List<LocalTime> filtrarHorariosDisponiveis(LocalDate data, String croDentista,
            List<LocalTime> horariosTurno) {

        List<LocalTime> disponiveis = new ArrayList<>();

        for (LocalTime h : horariosTurno) {

            int total = consultaRepository.contarConsultasNoHorario(
                    data,
                    croDentista,
                    h
            );

            if (total < 4) {
                disponiveis.add(h);
            }
        }

        return disponiveis;
    }
    public int contarConsultasNoHorario(LocalDate data, String cro, LocalTime hora) {
        return consultaRepository.contarConsultasNoHorario(data, cro, hora);
    }
    public List<Consulta> buscarAgendaDoDia(LocalDate data) {
        return consultaRepository.buscarPorDataOrdenado(data);
    }


    }





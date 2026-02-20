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
import java.util.List;

public class ConsultaService {
    private final ConsultaRepository consultaRepository = new ConsultaRepository();
    private final PacienteRepository pacienteRepository = new PacienteRepository();
    private final DentistaRepository dentistaRepository = new DentistaRepository();


    public void agendarConsulta(String cpfPaciente, String croDenstista,
                                LocalDate data, LocalTime hora, String obs){

        Paciente paciente = pacienteRepository.buscarPorCpf(cpfPaciente);
        Dentista dentista = dentistaRepository.buscarPorCro(croDenstista);

        if (paciente == null) {
            System.out.println("❌ Paciente não encontrado.");
            return;
        }

        if (dentista == null) {
            System.out.println("❌ Dentista não encontrado.");
            return;
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setDentista(dentista);
        consulta.setData(data);
        consulta.setHora(hora);
        consulta.setObservacao(obs);
        consulta.setStatus(StatusConsulta.AGENDADA);

        consultaRepository.salvar(consulta);

        System.out.println("✅ Consulta agendada com sucesso.");
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


    }





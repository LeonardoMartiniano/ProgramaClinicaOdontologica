package br.com.clinica.repository;

import br.com.clinica.config.JPAUtil;
import br.com.clinica.model.Consulta;

import br.com.clinica.model.Dentista;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ConsultaRepository {
    public void salvar(Consulta consulta) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(consulta);
        em.getTransaction().commit();
        em.close();
    }

    public void atualizar(Consulta consulta) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(consulta);
        em.getTransaction().commit();
        em.close();
    }

    public Consulta buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Consulta consulta = em.find(Consulta.class, id);
        em.close();
        return consulta;
    }

    public List<Consulta> buscarPorData(LocalDate data) {
        EntityManager em = JPAUtil.getEntityManager();

        List<Consulta> lista = em.createQuery(
                        "SELECT c FROM Consulta c WHERE c.data = :data",
                        Consulta.class
                )
                .setParameter("data", data)
                .getResultList();

        em.close();
        return lista;
    }

    public List<Consulta> buscarPorCpfPaciente(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();

        List<Consulta> lista = em.createQuery(
                        "SELECT c FROM Consulta c WHERE c.paciente.cpf = :cpf",
                        Consulta.class
                )
                .setParameter("cpf", cpf)
                .getResultList();

        em.close();
        return lista;
    }
    public long contarPorDentistaDataHora(Dentista dentista, LocalDate data, LocalTime hora) {
        EntityManager em = JPAUtil.getEntityManager();

        Long total = em.createQuery(
                        "SELECT COUNT(c) FROM Consulta c " +
                                "WHERE c.dentista = :dentista " +
                                "AND c.data = :data " +
                                "AND c.hora = :hora",
                        Long.class
                )
                .setParameter("dentista", dentista)
                .setParameter("data", data)
                .setParameter("hora", hora)
                .getSingleResult();

        em.close();
        return total;
    }
    public int contarConsultasNoHorario(LocalDate data, String cro, LocalTime hora) {

        EntityManager em = JPAUtil.getEntityManager();

        Long total = em.createQuery(
                        "SELECT COUNT(c) FROM Consulta c " +
                                "WHERE c.data = :data " +
                                "AND c.hora = :hora " +
                                "AND c.dentista.cro = :cro",
                        Long.class
                )
                .setParameter("data", data)
                .setParameter("hora", hora)
                .setParameter("cro", cro)
                .getSingleResult();

        em.close();
        return total.intValue();
    }
    public List<Consulta> buscarPorDataOrdenado(LocalDate data) {

        EntityManager em = JPAUtil.getEntityManager();

        List<Consulta> lista = em.createQuery(
                        "SELECT c FROM Consulta c " +
                                "WHERE c.data = :data " +
                                "ORDER BY c.hora",
                        Consulta.class
                )
                .setParameter("data", data)
                .getResultList();

        em.close();
        return lista;
    }

}

package br.com.clinica.repository;

import br.com.clinica.config.JPAUtil;
import br.com.clinica.model.Consulta;

import jakarta.persistence.EntityManager;

import java.time.LocalDate;
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
}

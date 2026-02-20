package br.com.clinica.repository;

import br.com.clinica.config.JPAUtil;
import br.com.clinica.model.Paciente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PacienteRepository {
    public void salvar(Paciente paciente) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(paciente);
        em.getTransaction().commit();
        em.close();
    }

    public Paciente buscarPorCpf(String cpf) {
        EntityManager em = JPAUtil.getEntityManager();

        TypedQuery<Paciente> query = em.createQuery(
                "SELECT p FROM Paciente p WHERE p.cpf = :cpf",
                Paciente.class
        );
        query.setParameter("cpf", cpf);

        List<Paciente> resultado = query.getResultList();
        em.close();

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<Paciente> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Paciente> lista = em.createQuery(
                "SELECT p FROM Paciente p", Paciente.class
        ).getResultList();
        em.close();
        return lista;
    }
    public void atualizar(Paciente paciente) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(paciente);
        em.getTransaction().commit();
        em.close();
    }

}

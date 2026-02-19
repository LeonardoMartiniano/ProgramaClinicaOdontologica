package br.com.clinica.repository;

import br.com.clinica.config.JPAUtil;
import br.com.clinica.model.Dentista;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DentistaRepository {
    public void salvar(Dentista dentista) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(dentista);
        em.getTransaction().commit();
        em.close();
    }

    public Dentista buscarPorCro(String cro) {
        EntityManager em = JPAUtil.getEntityManager();

        TypedQuery<Dentista> query = em.createQuery(
                "SELECT d FROM Dentista d WHERE d.cro = :cro",
                Dentista.class
        );
        query.setParameter("cro", cro);

        List<Dentista> resultado = query.getResultList();
        em.close();

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<Dentista> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        List<Dentista> lista = em.createQuery(
                "SELECT d FROM Dentista d", Dentista.class
        ).getResultList();
        em.close();
        return lista;
    }
}

package br.com.clinica.repository;

import br.com.clinica.config.JPAUtil;
import br.com.clinica.model.Pagamento;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class PagamentoRepository {

    public void salvar(Pagamento pagamento) {
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(pagamento);
        em.getTransaction().commit();
        em.close();
    }

    public List<Pagamento> buscarPorData(LocalDate data) {
        EntityManager em = JPAUtil.getEntityManager();

        List<Pagamento> lista = em.createQuery(
                "SELECT p FROM Pagamento p WHERE p.data = :data",
                Pagamento.class
        ).setParameter("data", data).getResultList();

        em.close();
        return lista;
    }
}

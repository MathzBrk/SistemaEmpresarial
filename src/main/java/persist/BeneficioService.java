package persist;

import model.Beneficio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BeneficioService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pouso-tech");

    public BeneficioService() {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void adicionarBeneficio(Beneficio beneficio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(beneficio);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao adicionar beneficio" + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Beneficio consultarBeneficioPorID(Long id) {
        EntityManager em = getEntityManager();
        try {
            Beneficio beneficio = em.find(Beneficio.class, id);
            return beneficio;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar beneficio" + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void atualizarBeneficio(Long id, String nome, String descricao, double valor) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Beneficio novoBeneficio = em.find(Beneficio.class, id);

        if (novoBeneficio != null) {
            novoBeneficio.setNome(nome);
            novoBeneficio.setDescricao(descricao);
            novoBeneficio.setValor(valor);
            em.merge(novoBeneficio);
        } else {
            throw new RuntimeException("Erro ao atualizar beneficio");
        }

        em.getTransaction().commit();
        em.close();
    }

    public void excluirBeneficio(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Beneficio beneficio = em.find(Beneficio.class, id);
            em.remove(beneficio);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir beneficio" + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}

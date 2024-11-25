package persist;

import model.Beneficio;
import model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

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

    public List<Beneficio> buscarBeneficiosBanco() {
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT b FROM Beneficio b", Beneficio.class).getResultList();
        } catch (Exception e){
            throw new RuntimeException("Erro ao buscar os benefícios: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void listarBeneficios(){
        List<Beneficio> beneficios = buscarBeneficiosBanco();
        for(Beneficio b : beneficios){
            System.out.println("ID: " + b.getId());
            System.out.println("Beneficio: " + b.getNome());
            System.out.println("Descrição: " + b.getDescricao());
            System.out.println("Valor do beneficio: " + b.getValor());
        }

    }
}

package service;

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

    public void atualizarBeneficio(Long id, Double valor) {
        EntityManager em = getEntityManager();

        try{
            em.getTransaction().begin();
            Beneficio beneficio = em.find(Beneficio.class, id);

            if (beneficio != null) {
                beneficio.setValor(valor);
                beneficio.getFuncionarios()
                        .forEach(f -> f.setSalario(f.calcularSalario()));

                em.merge(beneficio);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar beneficio" + e.getMessage(), e);
        } finally {
            em.close();
        }

    }

    public void excluirBeneficio(Long idBeneficio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Beneficio beneficio = em.find(Beneficio.class, idBeneficio);
            if (beneficio == null) {
                throw new RuntimeException("Benefício não encontrado.");
            }
            List<Funcionario> funcionarios = beneficio.getFuncionarios();
            for (Funcionario funcionario : funcionarios) {
                funcionario.getBeneficio().remove(beneficio);
                em.merge(funcionario);
            }
            em.remove(beneficio);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao excluir o benefício.", e);
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

package persist;

import model.Cargo;
import model.Departamento;
import model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CargoService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pouso-tech");

    public CargoService() {
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Cargo adicionarCargo(Cargo cargo) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
            return cargo;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao adicionar cargo" + e.getMessage(),e);
        } finally {
            em.close();
        }
    }

    public Cargo consultarPorId(Long id) {
        EntityManager em = getEntityManager();
        try{
            return em.find(Cargo.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar cargo" + e.getMessage(),e);
        } finally {
        em.close();
    }
    }

    public void atualizarCargo(Long id, String nome, String descricao, Double salarioBase) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Cargo c = em.find(Cargo.class, id);

            if (c != null) {
                c.setNome(nome);
                c.setDescricao(descricao);
                c.setSalarioBase(salarioBase);

                em.merge(c);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar cargo" + e.getMessage(),e);
        } finally {
            em.close();
        }
    }

    public void excluirCargo(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Cargo c = em.find(Cargo.class, id);
            if (c == null) {
                throw new RuntimeException("Cargo não encontrado.");
            }
            for (Funcionario funcionario : c.getFuncionarios()) {
                funcionario.setCargo(null);
                em.merge(funcionario);
            }
            em.merge(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao excluir cargo" + e.getMessage(),e);
        } finally {
            em.close();
        }
    }

    public List<Cargo> buscarCargoBanco(){
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT c FROM Cargo c", Cargo.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(" Erro ao buscar cargos!" + e.getMessage(), e);
        } finally {
            em.close();
        }

    }

    public void listarCargos() {
        List<Cargo> cargos = buscarCargoBanco();
        for (Cargo c : cargos) {
            System.out.println("Cargo ID: " + c.getId());
            System.out.println("Cargo nome: " + c.getNome());
        }
    }
}

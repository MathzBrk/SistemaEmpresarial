package persist;

import model.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CargoService {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pouso-tech");
    EntityManager em = emf.createEntityManager();

    public CargoService() {
    }

    public Cargo adicionarCargo(Cargo cargo) {
        try{
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
            return cargo;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao adicionar cargo" + e.getMessage(),e);
        }
    }

    public Cargo consultarPorId(Long id) {
        try{
            Cargo cargo = em.find(Cargo.class, id);
            return cargo;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar cargo" + e.getMessage(),e);
        }
    }

    public void atualizarCargo(Long id, String nome, String descricao, Double salarioBase) {
        em.getTransaction().begin();
        Cargo c = em.find(Cargo.class, id);

        if(c != null){
            c.setNome(nome);
            c.setDescricao(descricao);
            c.setSalarioBase(salarioBase);

            em.merge(c);
        } else {
            em.getTransaction().rollback();
            throw new RuntimeException("ID nao encontrado");
        }

        em.getTransaction().commit();
        em.close();
    }

    public void excluirCargo(Long id) {
        try {
            em.getTransaction().begin();
            Cargo c = em.find(Cargo.class, id);
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao excluir cargo" + e.getMessage(),e);
        }
    }
}

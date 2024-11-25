package persist;

import model.Cargo;
import model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class FuncionarioService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pouso-tech");

    private CargoService cargoService;


    public FuncionarioService() {
        this.cargoService = new CargoService();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }


    public void adicionarFuncionario(Funcionario funcionario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(funcionario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao adicionar funcionario" + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Funcionario consultarFuncionarioPorId(Long id) {
        EntityManager em = getEntityManager();
        try {
            Funcionario funcionario = em.find(Funcionario.class, id);
            return funcionario;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionario" + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void alterarCargo(Long idFuncionario,Long idCargo){
        EntityManager em = getEntityManager();
        try{
            Funcionario funcionario = consultarFuncionarioPorId(idFuncionario);
            Cargo cargo = cargoService.consultarPorId(idCargo);

            if(cargo != null && funcionario != null){
                funcionario.setCargo(cargo);
            }
            else{
                System.out.println("Entidades não encontradas");
            }
            System.out.println("Funcionario alterado com sucesso");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar cargo" + e.getMessage());
        }
    }

    public void atualizarFuncionario (Long id, Double salario) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Funcionario novoFuncionario = em.find(Funcionario.class, id);

        if (novoFuncionario != null) {
            novoFuncionario.setSalario(salario);
            em.merge(novoFuncionario);
        } else {
            throw new RuntimeException("Erro ao atualizar funcionario");
        }
        em.getTransaction().commit();
        em.close();
    }

    public void excluirFuncionario(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Funcionario funcionario = em.find(Funcionario.class, id);
            em.remove(funcionario);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao excluir funcionario" + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    private List<Funcionario> buscarFuncionariosBanco() {
        EntityManager em = getEntityManager();
        try{
            return em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(" Erro ao buscar funcionarios!" + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void listarFuncionarios(){
        List<Funcionario> funcionarios = buscarFuncionariosBanco();
        for(Funcionario f : funcionarios) {
            System.out.println("ID Funcionario: " + f.getId());
            System.out.println("Nome: " + f.getNome());
        }
    }
}

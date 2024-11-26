package persist;

import model.Beneficio;
import model.Cargo;
import model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class FuncionarioService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pouso-tech");

    private CargoService cargoService;

    private BeneficioService beneficioService;


    public FuncionarioService() {
        this.cargoService = new CargoService();
        this.beneficioService = new BeneficioService();
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

    public void atualizarFuncionario (Long id, String nome) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Funcionario novoFuncionario = em.find(Funcionario.class, id);

        if (novoFuncionario != null) {
            novoFuncionario.setNome(nome);
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

    public void consultarBeneficiosFuncionario(Long id) {
        Funcionario funcionario = consultarFuncionarioPorId(id);
        List<Beneficio> beneficios = funcionario.getBeneficio();

        try {
            System.out.println("Funcionario: " + funcionario.getNome());
            if (beneficios == null || beneficios.isEmpty()) {
                System.out.println("O funcionário não tem beneficios");
            } else {
                for (Beneficio beneficio : beneficios) {
                    System.out.println(("Beneficio: " + beneficio.getNome()));
                    System.out.println("Descrição: " + beneficio.getDescricao());
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar beneficios: " + e.getMessage());
        }


    }

    public void adicionarBeneficioAoFuncionario(Long idFunc, Long idBen) {
        EntityManager em = getEntityManager();

        Funcionario funcionario = consultarFuncionarioPorId(idFunc);
        Beneficio beneficio = beneficioService.consultarBeneficioPorID(idBen);

        if (funcionario == null) {
            throw new RuntimeException("Funcionário com ID " + idFunc + " não encontrado.");
        }

        if (beneficio == null) {
            throw new RuntimeException("Benefício com ID " + idBen + " não encontrado.");
        }

        try {

            funcionario.getBeneficio().add(beneficio);
            beneficio.getFuncionarios().add(funcionario);

            funcionario.setSalario(funcionario.calcularSalario());

            em.getTransaction().begin();
            em.merge(funcionario);
            em.getTransaction().commit();
            System.out.println("Beneficio adicionado com sucesso!");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar o benefício de id " +
                    idBen + " ao funcionário de id: " + idFunc, e);
        } finally {
            em.close();
        }
    }

}

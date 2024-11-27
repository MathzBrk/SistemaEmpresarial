package service;

import model.Departamento;
import model.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import java.util.List;

public class DepartamentoService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pouso-tech");
    private FuncionarioService funcionarioService;

    public DepartamentoService() {
        this.funcionarioService = new FuncionarioService();
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Departamento adicionarDepartamento(Departamento departamento) {
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(departamento);
            em.getTransaction().commit();
            return departamento;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao adicionar Departamento" + e.getMessage(),e);
        } finally {
            em.close();
        }
    }

    public Departamento consultarDepartamentoPorId(Long id) {
        EntityManager em = getEntityManager();
        try{
            Departamento departamento = em.find(Departamento.class, id);
            return departamento;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar Departamento" + e.getMessage(),e);
        } finally {
            em.close();
        }
    }

    public void atualizarDepartamento(Long id, String nome, String descricao) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Departamento departamento = em.find(Departamento.class, id);

            if (departamento != null) {
                departamento.setNome(nome);
                departamento.setDescricao(descricao);

                em.merge(departamento);
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
                throw new RuntimeException("ID nao encontrado");
            }
        } catch (Exception e){
            System.out.println("Erro ao atualizar Departamento");
        } finally {
            em.close();
        }
    }

    public void excluirDepartamento(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Departamento departamento = em.find(Departamento.class, id);

            if (departamento == null) {
                throw new EntityNotFoundException("Departamento com ID " + id + " não encontrado.");
            }
            for (Funcionario funcionario : departamento.getFuncionarios()) {
                funcionario.setDepartamento(null);
                em.merge(funcionario);
            }
            em.remove(departamento);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao excluir Departamento" + e.getMessage(),e);
        } finally {
                em.close();
        }
    }

    public void adicionarFuncionario(Long IdDepartamento,Long idFuncionario){
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Departamento departamento = em.find(Departamento.class, IdDepartamento);

            Funcionario funcionario = funcionarioService.consultarFuncionarioPorId(idFuncionario);

            if(departamento == null) {
                throw new RuntimeException("Departamento não encontrado.");
            }
            if(funcionario == null) {
                throw new RuntimeException("Funcionário não encontrado.");
            }

            funcionario.setDepartamento(departamento);
            departamento.getFuncionarios().add(funcionario);

            em.merge(funcionario);
            em.merge(departamento);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao adicionar funcionario!" + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<Departamento> buscarDepartamentosBanco() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT d FROM Departamento d", Departamento.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar departamentos!", e);
        } finally {
            em.close();
        }
    }


    public void listarDepartamentos(){
        List<Departamento> departamentos = buscarDepartamentosBanco();
        for (Departamento departamento : departamentos) {
            System.out.println("ID Departamento: " + departamento.getId());
            System.out.println("Nome Departamento: " + departamento.getNome());
        }
    }

    public void listarFuncionariosDepartamento( Long id ) {
        Departamento departamento = consultarDepartamentoPorId(id);
        try {
            if (departamento != null) {
                if(!departamento.getFuncionarios().isEmpty()) {
                    for (Funcionario funcionario : departamento.getFuncionarios()) {
                        System.out.println("Funcionario ID: " + funcionario.getId());
                        System.out.println("Funcionario nome: " + funcionario.getNome());
                        System.out.println();
                    }
                } else{
                    System.out.println("Departamento sem funcionários!");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionarios!" + e.getMessage(), e);
        }
    }
}

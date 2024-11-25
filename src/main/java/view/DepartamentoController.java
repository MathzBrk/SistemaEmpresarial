package view;

import model.Departamento;
import model.Funcionario;
import persist.DepartamentoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepartamentoController {
    private final DepartamentoService departamentoService;
    private final Scanner scanner;

    public DepartamentoController(){
        this.departamentoService = new DepartamentoService();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuDepartamento() {
        while (true) {
            System.out.println("\n=== CRUD de Departamento ===");
            System.out.println("1. Adicionar Departamento");
            System.out.println("2. Consultar Departamento por ID");
            System.out.println("3. Atualizar Departamento");
            System.out.println("4. Excluir Departamento");
            System.out.println("5. Adicionar Funcionários a um Departamento");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = 0;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Por favor, insira um número válido.");
                scanner.nextLine(); // Limpar buffer
                continue;
            }

            switch (opcao) {
                case 1 -> adicionarDepartamento();
                case 2 -> consultarDepartamentoPorId();
                case 3 -> atualizarDepartamento();
                case 4 -> excluirDepartamento();
                case 5 -> adicionarFuncionariosAoDepartamento();
                case 6 -> {
                    System.out.println("Voltando ao Menu Principal...");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }



    private void adicionarDepartamento() {
        System.out.println("\n=== Adicionar Departamento ===");

        System.out.println("Nome do departamento: ");
        String nome = scanner.nextLine();

        System.out.println("Descrição do departamento: ");
        String descricao = scanner.nextLine();

        Departamento departamento = new Departamento();
        departamento.setNome(nome);
        departamento.setDescricao(descricao);

        departamentoService.adicionarDepartamento(departamento);
        System.out.println("Departamento adicionado com sucesso! ID: " + departamento.getId());

    }

    private void consultarDepartamentoPorId() {
        System.out.println("ID do Departamento: ");
        Long idConsultar = scanner.nextLong();

        Departamento departamentoConsultado = departamentoService.consultarDepartamentoPorId(idConsultar);
        if (departamentoConsultado != null) {
            System.out.println("\n=== Detalhes do Departamento ===");
            System.out.println("ID: " + departamentoConsultado.getId());
            System.out.println("Nome: " + departamentoConsultado.getNome());
            System.out.println("Descricao: " + departamentoConsultado.getDescricao());
            System.out.println("Funcionários: ");
            List<Funcionario> funcionarios = departamentoConsultado.getFuncionarios();
            if (funcionarios != null && !funcionarios.isEmpty()) {
                for (Funcionario f : funcionarios) {
                    System.out.println(f);
                }
            } else {
                System.out.println("Departamento sem funcionários");
            }
        }
    }

    private void atualizarDepartamento() {
        System.out.println("ID do departamento a ser atualizado: ");
        Long id = scanner.nextLong();

        System.out.println("Novo nome do Departamento: ");
        String nomeDepartamento = scanner.nextLine();
        System.out.println("Nova descrição: ");
        String novaDescricao = scanner.nextLine();

        try {
            departamentoService.atualizarDepartamento(id, nomeDepartamento, novaDescricao);
            System.out.println("Departamento atualizado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar departamento!" + e.getMessage());
        }
    }

    private void adicionarFuncionariosAoDepartamento() {
        System.out.println("Em qual departamento você deseja adicionar um funcionário: ");
        System.out.println("IDs Departamentos disponiveis: ");
        departamentoService.listarDepartamentos();


        System.out.println("Digite o ID do departamento: ");
        Long idDepartamento = scanner.nextLong();

        System.out.println("Digite o ID do funcionário que deseja adicionar: ");
        Long idFuncionario = scanner.nextLong();

        try{
            departamentoService.adicionarFuncionario(idDepartamento, idFuncionario);
            System.out.println("Funcionário adicionado com sucesso!");
        } catch (Exception e){
            System.out.println("Erro ao adicionar o funcionário!");
        }
    }

    private void excluirDepartamento() {
        System.out.println("ID do Departamento a ser excluído: ");
        Long id = scanner.nextLong();

        try{
            departamentoService.excluirDepartamento(id);
            System.out.println("Departamento excluído com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir o Departamento!" + e.getMessage());
        }
    }
}

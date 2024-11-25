package view;

import model.Cargo;
import model.Departamento;
import model.Funcionario;
import persist.CargoService;
import persist.DepartamentoService;
import persist.FuncionarioService;

import java.time.LocalDate;
import java.util.Scanner;

public class FuncionarioController {
    private FuncionarioService funcionarioService;
    private final Scanner scanner;
    private CargoService cargoService;
    private DepartamentoService departamentoService;

    public FuncionarioController(){
        this.scanner = new Scanner(System.in);
        this.funcionarioService = new FuncionarioService();
        this.cargoService = new CargoService();
        this.departamentoService = new DepartamentoService();
    }

    public void exibirMenuFuncionario(){
        while(true) {
            System.out.println("\n=== CRUD de Funcionario ===");
            System.out.println("1. Adicionar Funcionario");
            System.out.println("2. Consultar Funcionario por ID");
            System.out.println("3. Atualizar Funcionario");
            System.out.println("4. Excluir Funcionario");
            System.out.println("5. Alterar Cargo");
            System.out.println("6. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1:
                    adicionarFuncionario();
                    break;
                case 2:
                    consultarFuncionarioPorId();
                    break;
                case 3:
                    atualizarFuncionario();
                    break;
                case 4:
                    excluirFuncionario();
                    break;
                case 5:
                    alterarCargo();
                case 6:
                    System.out.println("Voltando ao Menu Principal...");
                    return;
            }
        }
    }

    private void adicionarFuncionario() {
        System.out.println("=== Adicionar Funcionario ===");

        System.out.println("Nome do funcionário: ");
        String nome = scanner.nextLine();

        System.out.println("CPF do funcionário: ");
        String cpf = scanner.nextLine();

        System.out.println("Salário do funcionário: ");
        Double salario = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Data de admissão (formato: yyyy-MM-dd): ");
        String dataInput = scanner.nextLine();
        LocalDate dataAdmissao = LocalDate.parse(dataInput);

        System.out.println("ID do cargo: ");
        Long idCargo = scanner.nextLong();
        scanner.nextLine();
        Cargo cargo = cargoService.consultarPorId(idCargo);

        if (cargo == null) {
            System.out.println("Cargo não encontrado.");
            return;
        }

        System.out.println("ID do departamento: ");
        Long idDepartamento = scanner.nextLong();
        scanner.nextLine();
        Departamento departamento = departamentoService.consultarDepartamentoPorId(idDepartamento);

        if (departamento == null) {
            System.out.println("Departamento não encontrado.");
            return;
        }

        Funcionario funcionario = new Funcionario(nome, cpf, salario, dataAdmissao, cargo, departamento);

        try {
            funcionarioService.adicionarFuncionario(funcionario);
            System.out.println("Funcionário adicionado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao adicionar funcionário: " + e.getMessage());
        }
    }

    private void alterarCargo() {
        System.out.println("ID do Funcionario que deseja alterar o cargo: ");
        Long idFuncionario = scanner.nextLong();

        System.out.println("Digite o ID do novo cargo do Funcionario: ");
        cargoService.listarCargos();
        Long idCargo = scanner.nextLong();

        try {
            funcionarioService.alterarCargo(idFuncionario, idCargo);
            System.out.println("Cargo alterado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao alterar cargo: " + e.getMessage());
        }
    }

    private void consultarFuncionarioPorId(){
        System.out.println("ID do Funcionario: ");
        Long id = scanner.nextLong();

        Funcionario funcionarioConsultado = funcionarioService.consultarFuncionarioPorId(id);
        if(funcionarioConsultado != null) {
            System.out.println(funcionarioConsultado);
        } else {
            System.out.println("Funcionario nao encontrado.");
        }
    }

    private void atualizarFuncionario() {
        System.out.println("ID do funcionario a ser atualizado");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Novo salário do funcionário: ");
        Double salario = scanner.nextDouble();
        scanner.nextLine();

        try{
            funcionarioService.atualizarFuncionario(id,salario);
            System.out.println("Salário do funcionário alterado com sucesso");
        } catch (RuntimeException e){
            System.out.println("Erro ao atualizar funcionário");
        }
    }

    private void excluirFuncionario() {
        System.out.println("ID do funcionario a ser excluido");
        Long id = scanner.nextLong();

        try {
            funcionarioService.excluirFuncionario(id);
            System.out.println("Funcionario excluido com sucesso");
        } catch (RuntimeException e){
            System.out.println("Erro ao excluir funcionario" + e.getMessage());
        }
    }
}

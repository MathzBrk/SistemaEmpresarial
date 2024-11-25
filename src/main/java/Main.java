import persist.BeneficioService;
import view.BeneficioController;
import view.CargoController;
import view.DepartamentoController;
import view.FuncionarioController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CargoController cargoController = new CargoController();
        FuncionarioController funcionarioController = new FuncionarioController();
        DepartamentoController departamentoController = new DepartamentoController();
        BeneficioController beneficioController = new BeneficioController();

        while (true) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Gerenciar Cargos");
            System.out.println("2. Gerenciar Funcionarios");
            System.out.println("3. Gerenciar Departamentos");
            System.out.println("4. Gerenciar Beneficios");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cargoController.exibirMenuCargo();
                    break;
                case 2:
                    funcionarioController.exibirMenuFuncionario();
                    break;
                case 3:
                    departamentoController.exibirMenuDepartamento();
                    break;
                case 4:
                    beneficioController.exibirMenuBeneficio();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}

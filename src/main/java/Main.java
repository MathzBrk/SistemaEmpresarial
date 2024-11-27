import view.BeneficioViewHandler;
import view.CargoViewHandler;
import view.DepartamentoViewHandler;
import view.FuncionarioViewHandler;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CargoViewHandler cargoViewHandler = new CargoViewHandler();
        FuncionarioViewHandler funcionarioViewHandler = new FuncionarioViewHandler();
        DepartamentoViewHandler departamentoViewHandler = new DepartamentoViewHandler();
        BeneficioViewHandler beneficioViewHandler = new BeneficioViewHandler();

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
                    cargoViewHandler.exibirMenuCargo();
                    break;
                case 2:
                    funcionarioViewHandler.exibirMenuFuncionario();
                    break;
                case 3:
                    departamentoViewHandler.exibirMenuDepartamento();
                    break;
                case 4:
                    beneficioViewHandler.exibirMenuBeneficio();
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

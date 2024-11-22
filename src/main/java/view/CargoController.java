package view;

import model.Cargo;
import persist.CargoService;
import java.util.Scanner;

public class CargoController {
    private final CargoService cargoService;
    private final Scanner scanner;

    public CargoController() {
        this.cargoService = new CargoService();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuCargo() {
        while (true) {
            System.out.println("\n=== CRUD de Cargo ===");
            System.out.println("1. Adicionar Cargo");
            System.out.println("2. Consultar Cargo por ID");
            System.out.println("3. Atualizar Cargo");
            System.out.println("4. Excluir Cargo");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarCargo();
                    break;
                case 2:
                    consultarCargo();
                    break;
                case 3:
                    atualizarCargo();
                    break;
                case 4:
                    excluirCargo();
                    break;
                case 5:
                    System.out.println("Voltando ao Menu Principal...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void adicionarCargo() {
        System.out.println("Nome do Cargo: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição do cargo: ");
        String descricao = scanner.nextLine();

        System.out.print("Salário base: ");
        double salarioBase = scanner.nextDouble();

        Cargo novoCargo = new Cargo();
        novoCargo.setNome(nome);
        novoCargo.setDescricao(descricao);
        novoCargo.setSalarioBase(salarioBase);

        cargoService.adicionarCargo(novoCargo);
        System.out.println("Cargo adicionado com sucesso! ID: " + novoCargo.getId());
    }

    private void consultarCargo() {
        System.out.print("ID do cargo: ");
        Long idConsulta = scanner.nextLong();

        Cargo cargoConsultado = cargoService.consultarPorId(idConsulta);
        if (cargoConsultado != null) {
            System.out.println("\n=== Detalhes do Cargo ===");
            System.out.println("ID: " + cargoConsultado.getId());
            System.out.println("Nome: " + cargoConsultado.getNome());
            System.out.println("Descrição: " + cargoConsultado.getDescricao());
            System.out.println("Salário Base: " + cargoConsultado.getSalarioBase());
        } else {
            System.out.println("Cargo não encontrado.");
        }
    }

    private void atualizarCargo() {
        System.out.print("ID do cargo a ser atualizado: ");
        Long idAtualizar = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Novo nome do cargo: ");
        String nomeAtualizar = scanner.nextLine();
        System.out.print("Nova descrição: ");
        String novaDescricao = scanner.nextLine();
        System.out.print("Novo salário base: ");
        double novoSalarioBase = scanner.nextDouble();

        try {
            cargoService.atualizarCargo(idAtualizar, nomeAtualizar, novaDescricao, novoSalarioBase);
            System.out.println("Cargo atualizado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao atualizar o cargo: " + e.getMessage());
        }
    }

    private void excluirCargo() {
        System.out.print("ID do cargo a ser excluído: ");
        Long idExcluir = scanner.nextLong();

        try {
            cargoService.excluirCargo(idExcluir);
            System.out.println("Cargo excluído com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir o cargo: " + e.getMessage());
        }
    }
}
package view;

import model.Beneficio;
import service.BeneficioService;

import java.util.Scanner;

public class BeneficioViewHandler {
    private final BeneficioService beneficioService;
    private final Scanner scanner;

    public BeneficioViewHandler() {
        this.beneficioService = new BeneficioService();
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenuBeneficio() {
        while (true) {
            System.out.println("\n=== CRUD de Beneficio ===");
            System.out.println("1. Adicionar Beneficio");
            System.out.println("2. Consultar Beneficio por ID");
            System.out.println("3. Atualizar Beneficio");
            System.out.println("4. Excluir Beneficio");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarBeneficio();
                    break;
                case 2:
                    consultarBeneficio();
                    break;
                case 3:
                    atualizarBeneficio();
                    break;
                case 4:
                    excluirBeneficio();
                    break;
                case 5:
                    System.out.println("Voltando ao Menu Principal...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void adicionarBeneficio() {
        System.out.println("Nome do Beneficio: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição do Beneficio: ");
        String descricao = scanner.nextLine();

        System.out.print("Valor: ");
        double valor = scanner.nextDouble();

        Beneficio novoBeneficio = new Beneficio();
        novoBeneficio.setNome(nome);
        novoBeneficio.setDescricao(descricao);
        novoBeneficio.setValor(valor);

        beneficioService.adicionarBeneficio(novoBeneficio);
        System.out.println("Beneficio adicionado com sucesso! ID: " + novoBeneficio.getId());
    }

    private void consultarBeneficio() {
        System.out.print("ID do Beneficio: ");
        Long idConsulta = scanner.nextLong();

        Beneficio beneficioConsultado = beneficioService.consultarBeneficioPorID(idConsulta);
        if (beneficioConsultado != null) {
            System.out.println("\n=== Detalhes do Beneficio ===");
            System.out.println("ID: " + beneficioConsultado.getId());
            System.out.println("Nome: " + beneficioConsultado.getNome());
            System.out.println("Descrição: " + beneficioConsultado.getDescricao());
            System.out.println("Valor: " + beneficioConsultado.getValor());
        } else {
            System.out.println("Beneficio não encontrado.");
        }
    }

    private void atualizarBeneficio() {
        beneficioService.listarBeneficios();
        System.out.println();
        System.out.print("ID do Beneficio a ser atualizado: ");
        Long idAtualizar = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Novo valor do beneficio: ");
        double novoValor = scanner.nextDouble();

        beneficioService.atualizarBeneficio(idAtualizar, novoValor);
        System.out.println("Beneficio atualizado com sucesso!");

    }

    private void excluirBeneficio() {
        System.out.print("ID do Beneficio a ser excluído: ");
        Long idExcluir = scanner.nextLong();

        try {
            beneficioService.excluirBeneficio(idExcluir);
            System.out.println("Beneficio excluído com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("Erro ao excluir o Beneficio: " + e.getMessage());
        }
    }
}



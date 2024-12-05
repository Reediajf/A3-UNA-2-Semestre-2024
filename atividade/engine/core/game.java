package atividade.engine.core;

import atividade.engine.core.controllers.loja;
import atividade.engine.core.controllers.player;
import atividade.engine.manager;
import atividade.engine.server;
import atividade.engine.ui.BannerFall;

public class game {
    // Método estático que inicializa o jogo
    public static void init() {
        // Executa a animação do banner
        BannerFall.run();
        // Informa ao usuário que está se conectando ao servidor
        manager.writeline("Conectando ao servidor...");
        // Inicializa o servidor
        server.init();

        // Mensagem de boas-vindas ao jogador
        manager.writeline("{verde_claro}{negrito}Bem vindo{reset} aventureiro(a)\nFaca seu login ou crie sua conta para continuar");
        String[] options = new String[]{"Iniciar Sessao", "Registrar"};

// Loop infinito para apresentar opções de login ou registro
        while (true) {
            // Exibe as opções disponíveis
            for (int x = 0; x != options.length; x++) {
                manager.writeline((x + 1) + " - " + options[x]);
            }

            // Lê a opção selecionada pelo usuário com try-catch para evitar exceções
            int selectMenu = -1; // Inicializando com um valor inválido

            try {
                // Tentativa de ler um número inteiro
                selectMenu = Integer.valueOf(manager.readline("> "));

                // Verifica se a opção está dentro do intervalo válido
                if (selectMenu == 1) {
                    // Se a opção for 1, inicia a sessão
                    authPage.init();
                    break; // Sai do loop após iniciar a sessão
                } else if (selectMenu == 2) {
                    // Se a opção for 2, inicia o registro
                    registerPage.init();
                    break; // Sai do loop após registrar
                } else {
                    // Se a opção for inválida, exibe uma mensagem de erro
                    manager.writeline("Opção inválida. Por favor, escolha uma opção válida.");
                }

            } catch (NumberFormatException e) {
                // Caso a entrada não seja um número inteiro
                manager.writeline("Entrada inválida. Por favor, escolha uma das opções.");
            }
        }

// Após login ou registro, vai para o menu principal
        mainMenu();

    }

    // Método privado para exibir o menu principal
    static void mainMenu() {
        String[] options = new String[]{"Iniciar onda", "Status do Jogador", "Inventario", "Loja", "Enciclopedia", "Sair"};

        // Loop infinito para manter o menu principal ativo
        while (true) {
            // Exibe as informações do jogador
            manager.writeline("===== " + manager.jogador.toString() + " =====");

            // Exibe as opções do menu principal
            for (int x = 0; x != options.length; x++) {
                manager.writeline((x + 1) + " - " + options[x]);
            }

            // Lê a opção selecionada pelo usuário
            String input = manager.readline("> ");
            // Verifica se a entrada está vazia
            if (input.isEmpty()) {
                manager.writeline("Por favor, selecione uma opção válida.");
                continue; // Volta ao início do loop
            }
                // Verifca  se a entrada é um inteiro
            try {
                int selectMenu = Integer.valueOf(input);

                // Executa a ação correspondente à opção escolhida
                if (selectMenu == 1) {
                    // Inicia a onda de inimigos
                    waveMode.init();
                } else if (selectMenu == 2) {
                    // Exibe os status do jogador
                    manager.jogador.Status();
                    manager.writeline(manager.readline("Aperte {azul_claro}{negrito}ENTER{reset} para continuar"));
                } else if (selectMenu == 3) {
                    // Abre o inventário
                    inventoryPage.init();
                } else if (selectMenu == 4) {
                    loja.init();
                } else if (selectMenu == 5) {
                    // Abre a enciclopédia
                    enciclopediaPage.init();
                } else if (selectMenu == 6) {
                    // Se a opção for sair, exibe mensagem de despedida e encerra o programa
                    manager.writeline("Ate logo!");
                    System.exit(0);
                }

                // Limpa a tela para o próximo menu
                manager.clear();
                // Mensagem caso a entrada não seja um inteiro
            } catch (NumberFormatException e) {
                manager.writeline("Por favor, escolha uma opção.");
            }
        }
    }
}

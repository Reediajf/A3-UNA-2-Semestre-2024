package atividade.engine.core.controllers;

import atividade.engine.manager;
import atividade.engine.server;

import java.util.InputMismatchException; // Importando a exceção específica para capturar entradas inválidas
import static atividade.engine.manager.*;
import static atividade.engine.server.*;

public class loja {

    public static void init() {
        while (true) {  // Loop infinito, o jogador pode comprar ou sair
            try {

                // Exibe a lista de itens na loja
                manager.writeline("Bem-vindo à loja, {vermelho}" + jogador.getNome() + "{reset}" +
                        "\nEscolha o item que deseja: ");

                // Exibe cada item da loja
                for (int x = 0; x < items.size(); x++) {
                    item elemento = items.get(x);
                    writeline((x + 1) + " - " + elemento.toString());
                }

                // Solicita ao jogador para escolher um item
                manager.writeline("Digite o número do item que deseja comprar ou escolha 0 para sair: ");
                int escolha;
                escolha = Integer.parseInt(manager.readline("> "));  // Pode gerar InputMismatchException

                // Se o jogador escolher 0, sai do loop
                if (escolha == 0) {
                    System.out.println("Volte sempre...");
                    break;
                }

                // Verifica se a escolha está dentro do intervalo de itens disponíveis
                if (escolha > 0 && escolha <= items.size()) {
                    item itemEscolhido = items.get(escolha - 1); // Ajusta para o índice correto (base 1)
                    player jogador = manager.jogador; // Referência ao jogador

                    int precoItem = itemEscolhido.getValor();

                    // Verifica se o jogador tem ouro suficiente
                    if (jogador.HaveGold(precoItem)) {
                        // Subtrai o ouro do jogador e "compra" o item
                        jogador.RemoveGold(precoItem);
                        manager.writeline("Você comprou: " + itemEscolhido.getNome() + " por {amarelo}" + precoItem + " Ouro!{reset}");

                        // Aqui você pode adicionar a lógica para adicionar o item ao inventário do jogador, por exemplo
                        manager.inventario.add(itemEscolhido);
                        // Salva os dados
                        server.SaveItem(itemEscolhido.getId(), manager.jogador.getId());
                        manager.writeline(manager.readline("Aperte {azul_claro}{negrito}ENTER{reset} para continuar"));
                    } else {
                        manager.writeline("Você não tem ouro suficiente para comprar este item.");
                    }
                } else {
                    manager.writeline("Opção inválida. Por favor, escolha um número válido!" +
                            "\nOu escolha 0 para sair.");
                }
            } catch (InputMismatchException e) {
                // Trata a exceção quando o jogador não insere um número válido
                manager.writeline("Erro: Por favor, insira um número válido!");
                manager.writeline(manager.readline("Aperte {azul_claro}{negrito}ENTER{reset} para continuar"));
            } catch (Exception e) {
                // Trata outras exceções gerais
                manager.writeline("Ocorreu um erro inesperado. Tente novamente.");
                e.printStackTrace(); // Opcional: imprime o erro no console para depuração
            }
        }
    }
}

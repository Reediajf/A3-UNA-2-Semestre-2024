package atividade.engine.core;

import atividade.engine.manager;
import atividade.engine.core.controllers.item;
import atividade.engine.server;
import atividade.engine.server.*;
import atividade.engine.manager.*;

import java.util.InputMismatchException; // Importando a exceção para entradas inválidas

public class inventoryPage {

    // Metodo estático que inicializa a exibição do inventário
    public static void init() {
        try {
            // Itera sobre cada item no inventário
            if (manager.inventario.isEmpty()) {
                manager.writeline("Seu inventário está vazio.");
                return;
            }

            for (int x = 0; x < manager.inventario.size(); x++) {
                // Obtém o item atual da lista de inventário
                item elemento = manager.inventario.get(x);

                // Exibe o índice (baseado em 1) e o nome do item na tela
                manager.writeline((x + 1) + " - " + elemento.getNome() + " (ID: " + elemento.getId() + ")");
            }

            // Solicita ao jogador para escolher um item pelo ID
            manager.writeline("Escolha o item pelo ID(o u digite 0 para sair): ");
            int escolha;
            escolha = Integer.parseInt(manager.readline("> "));// Pode gerar InputMismatchException

            // Se o jogador escolher 0, sai


            if (escolha == 0) {
                manager.writeline("Saindo...");
                return;
            }

            manager.writeline("Você deseja {amarelo}{negrito}equipar{reset}" + " ou {vermelho}{negrito}descartar{reset} o item?");
            manager.writeline("1. Equipar");
            manager.writeline("2. Remover");
            int escolha2;
            escolha2 = Integer.parseInt(manager.readline("> "));
            if (escolha2 == 1) {


                // Verifica se a escolha é válida
                if (escolha > 0 && escolha <= manager.inventario.size()) {
                    item itemEscolhido = manager.inventario.get(escolha - 1);
                    manager.writeline("Você equipou o item: " + itemEscolhido.getNome());

                    manager.jogador.SaveData();

                } else {
                    manager.writeline("Opção inválida. Tente novamente.");
                }
                // deleta o item se a escolha for 2
            } else if (escolha2 > 0 && escolha2 <= manager.inventario.size()) {
                item itemEscolhido = manager.inventario.get(escolha2);
                manager.writeline("Você descartou o item: " + itemEscolhido.getNome());

                server.DeleteItems(itemEscolhido.getId(), manager.jogador.getId());
            } else {
                manager.writeline("Aperte {azul_claro}{negrito}ENTER,{reset} para continuar");
            }
            // Aguarda o usuário pressionar ENTER para continuar
            manager.readline("Aperte {azul_claro}{negrito}ENTER,{reset} para continuar");
        } catch (InputMismatchException e) {
            // Trata a exceção quando o jogador não insere um número válido
            manager.writeline("Erro: Por favor, insira um número válido!");
            manager.writeline(manager.readline("Aperte {azul_claro}{negrito}ENTER,{reset} para continuar"));// Limpa o buffer de entrada para evitar loops infinitos
        } catch (Exception e) {
            // Trata outras exceções gerais
            manager.writeline("Ocorreu um erro inesperado. Tente novamente.");
            e.printStackTrace(); // Opcional: imprime o erro no console para depuração
        }
    }


}

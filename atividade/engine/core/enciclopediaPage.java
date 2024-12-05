package atividade.engine.core;

import atividade.engine.manager;
import atividade.engine.core.controllers.enemy;
import atividade.engine.core.controllers.item;

public class enciclopediaPage {
    // Método estático que inicializa a página da enciclopédia
    public static void init() {
        // Lê o filtro de pesquisa fornecido pelo usuário
        String filtro = manager.readline("Oque deseja pesquiasr? ");
        
        // Loop para pesquisar entre os inimigos
        for (int x = 0; x != manager.inimigos.size(); x++) {
            enemy elemento = manager.inimigos.get(x);
            // Verifica se o nome do inimigo contém o filtro, ignorando maiúsculas e minúsculas
            if (elemento.toString().toLowerCase().contains(filtro.toLowerCase())) {
                // Exibe a arte e as informações do inimigo
                manager.writeline(elemento.getArte() + "\n" + elemento.toString());
                
                // Lê a entrada do usuário para continuar ou sair
                if (manager.readline("Aperte {azul_claro}{negrito}ENTER{reset} para continuar ou 1 para sair\n").equals("1"))
                    break; // Sai do loop se o usuário escolher sair
            }
        }
        
        // Loop para pesquisar entre os itens
        for (int x = 0; x != manager.items.size(); x++) {
            item elemento = manager.items.get(x);
            // Verifica se o nome do item contém o filtro, ignorando maiúsculas e minúsculas
            if (elemento.toString().toLowerCase().contains(filtro.toLowerCase())) {
                // Exibe as informações do item
                manager.writeline(elemento.toString());
                // Lê a entrada do usuário para continuar ou sair
                if (manager.readline("Aperte {azul_claro}{negrito}ENTER{reset} para continuar ou 1 para sair\n").equals("1"))
                    break; // Sai do loop se o usuário escolher sair
            }
        }
    }
}

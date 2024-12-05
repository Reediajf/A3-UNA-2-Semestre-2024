package atividade.engine.core;

import atividade.engine.manager;
import atividade.engine.core.controllers.enemy;
import atividade.engine.core.controllers.waveManager;

/**
 * A classe WaveMode gerencia a lógica do modo de onda no jogo,
 * onde o jogador enfrenta uma série de inimigos.
 */
public class waveMode {
    
    /**
     * Inicializa o modo de onda, gerenciando a luta entre o jogador e os inimigos.
     * O método controla o fluxo da batalha, permitindo que o jogador ataque e use itens
     * até que todos os inimigos sejam derrotados ou o jogador morra.
     */
    public static void init() {
        waveManager waveManager = new waveManager(manager.jogador.getWave()); // Inicializa o gerenciador de ondas
        manager.clear(); // Limpa a tela

        // Enquanto houver inimigos na onda
        while (!waveManager.waveEnd()) {
            enemy enemy = waveManager.enemies.getFirst(); // Obtém o primeiro inimigo da lista

            // Enquanto o inimigo não estiver morto
            while (!enemy.isDead()) {
                manager.clear(); // Limpa a tela
                // Mostra informações do inimigo e do jogador
                manager.writeline("===== " + enemy.CurrentInfo() +
                                 " Wave: {amarelo}" + manager.jogador.getWave() +
                                 "{reset} Inimigos Restantes: {verde_claro}" + waveManager.enemies.size() +
                                 "{reset} =====\n" + enemy.getArte());
                manager.writeline("===== " + manager.jogador.CurrentInfo() + " =====");

                // Opções de ação do jogador
                manager.writeline("1 - Atacar\n2 - Usar Item");
                int action;
                do {
                    action = Integer.parseInt(manager.readline("> ")); // Lê a ação do jogador
                } while (action < 1 || action > 2); 

                // Se o jogador escolher atacar
                if (action == 1) {
                    enemy.TakeDamage(manager.jogador.getAttack()); // Aplica dano ao inimigo
                }
                if (action == 2) {

                }
                
                // Se o inimigo ainda estiver vivo, ele ataca o jogador
                if (!enemy.isDead()) {
                    manager.jogador.TakeDamage(enemy.getAtaque()); // Aplica dano ao jogador
                } else {
                    enemy.processDead(); // Processa a morte do inimigo
                }

                // Verifica se o jogador morreu
                if (manager.jogador.IsDead()) {
                    manager.readline("{vermelho_claro}{negrito}Você morreu!{reset}");
                    waveManager.enemies.clear(); // Limpa a lista de inimigos
                    break; // Sai do loop
                }

                manager.readline("Aperte {azul_claro}{negrito}ENTER{reset} para continuar");
            }

            // Se o jogador morreu, sai do loop principal
            if (manager.jogador.IsDead()) { break; }

            waveManager.enemies.removeFirst(); // Remove o inimigo derrotado da lista
        }

        // Verifica se o jogador sobreviveu à onda
        if (!manager.jogador.IsDead()) {
            manager.writeline("{verde_claro}{negrito}Wave " + manager.jogador.getWave() + "{reset} concluida com sucesso!{reset}");
            manager.jogador.NextWave(); // Avança para a próxima onda
        } else {
            manager.writeline("{vermelho}{negrito}Voce perdeu na wave " + manager.jogador.getWave() + ", boa sorte na proxima!{reset}");
            manager.jogador.ResetWave(); // Reseta a onda
        }

        manager.jogador.SaveData(); // Salva os dados do jogador
    }
}
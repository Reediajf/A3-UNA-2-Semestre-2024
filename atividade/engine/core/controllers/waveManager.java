package atividade.engine.core.controllers;

import java.util.Random;
import atividade.engine.manager;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe WaveManager gerencia as ondas de inimigos no jogo.
 * Ela é responsável por gerar inimigos de acordo com a onda atual e verificar se a onda foi concluída.
 */
public class waveManager {
    
    // Lista de inimigos na onda atual
    public List<enemy> enemies;

    /**
     * Construtor para criar um gerenciador de ondas.
     * 
     * @param wave O número da onda atual, que influencia a geração de inimigos.
     */
    public waveManager(int wave) {
        this.enemies = new ArrayList<enemy>(); // Inicializa a lista de inimigos
        generateEnemies(wave); // Gera inimigos para a onda atual
    }

    /**
     * Gera um número aleatório de inimigos com base na onda atual e os adiciona à lista de inimigos.
     * 
     * @param wave O número da onda atual, que influencia a quantidade de inimigos gerados.
     */
    public void generateEnemies(int wave) {
        Random rand = new Random();
        int numberOfEnemies = rand.nextInt(wave +1); // Número de inimigos é um número aleatório entre 0 e a onda atual


        for (int i = 0; i < numberOfEnemies; i++) {
            // Cria um novo inimigo baseado em um inimigo existente aleatório
            enemy enm = new enemy(manager.inimigos.get(rand.nextInt(0, manager.inimigos.size())));
            enemies.add(enm); // Adiciona o inimigo à lista
        }
    }

    /**
     * Verifica se a onda terminou, ou seja, se não há mais inimigos na lista.
     * 
     * @return true se a onda terminou (nenhum inimigo restante), false caso contrário.
     */
    public boolean waveEnd() {
        return enemies.size() == 0; // Retorna true se a lista de inimigos estiver vazia
    }
}

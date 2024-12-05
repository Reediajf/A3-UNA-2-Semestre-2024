package atividade.engine;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import atividade.engine.core.controllers.enemy;
import atividade.engine.core.controllers.player;
import atividade.engine.core.controllers.item;

/**
 * Classe responsável por gerenciar a lógica do jogo, incluindo
 * a manipulação de jogadores, inimigos e itens.
 */
public class manager {
    // Listas para armazenar inimigos, itens e inventário
    public static List<enemy> inimigos;
    public static List<item> items;
    public static List<item> inventario;
    public static List<item> loja;

    // Instância do jogador
    public static player jogador;
    // Identificação da conta do jogador
    public static int account_id;

    // Scanner para entrada do usuário
    static Scanner s = new Scanner(System.in);

    // Mapa para armazenar códigos de cor e seus valores ANSI correspondentes
    private static final Map<String, String> colorMap = new HashMap<>();
    
    static {
        // Códigos de formatação de texto
        colorMap.put("{reset}", "\u001B[0m");
        colorMap.put("{negrito}", "\u001B[1m");
        colorMap.put("{itálico}", "\u001B[3m");
        colorMap.put("{sublinhado}", "\u001B[4m");
        colorMap.put("{tachado}", "\u001B[9m");

        // Cores de texto
        colorMap.put("{preto}", "\u001B[30m");
        colorMap.put("{vermelho}", "\u001B[31m");
        colorMap.put("{verde}", "\u001B[32m");
        colorMap.put("{amarelo}", "\u001B[33m");
        colorMap.put("{azul}", "\u001B[34m");
        colorMap.put("{magenta}", "\u001B[35m");
        colorMap.put("{ciano}", "\u001B[36m");
        colorMap.put("{branco}", "\u001B[37m");
        colorMap.put("{cinza}", "\u001B[90m");
        colorMap.put("{vermelho_claro}", "\u001B[91m");
        colorMap.put("{verde_claro}", "\u001B[92m");
        colorMap.put("{amarelo_claro}", "\u001B[93m");
        colorMap.put("{azul_claro}", "\u001B[94m");
        colorMap.put("{magenta_claro}", "\u001B[95m");
        colorMap.put("{ciano_claro}", "\u001B[96m");
        colorMap.put("{branco_claro}", "\u001B[97m");

        // Cores de fundo
        colorMap.put("{fundo_preto}", "\u001B[40m");
        colorMap.put("{fundo_vermelho}", "\u001B[41m");
        colorMap.put("{fundo_verde}", "\u001B[42m");
        colorMap.put("{fundo_amarelo}", "\u001B[43m");
        colorMap.put("{fundo_azul}", "\u001B[44m");
        colorMap.put("{fundo_magenta}", "\u001B[45m");
        colorMap.put("{fundo_ciano}", "\u001B[46m");
        colorMap.put("{fundo_branco}", "\u001B[47m");
        colorMap.put("{fundo_cinza}", "\u07501B[100m");
        colorMap.put("{fundo_vermelho_claro}", "\u001B[101m");
        colorMap.put("{fundo_verde_claro}", "\u001B[102m");
        colorMap.put("{fundo_amarelo_claro}", "\u001B[103m");
        colorMap.put("{fundo_azul_claro}", "\u001B[104m");
        colorMap.put("{fundo_magenta_claro}", "\u001B[105m");
        colorMap.put("{fundo_ciano_claro}", "\u001B[106m");
        colorMap.put("{fundo_branco_claro}", "\u001B[107m");
    }

    /**
     * Formata o texto substituindo códigos de cor pelo valor ANSI correspondente.
     *
     * @param text O texto a ser formatado.
     * @return O texto formatado.
     */
    public static String format(String text) {
        for (Map.Entry<String, String> entry : colorMap.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }
        return text;
    }

    /**
     * Escreve texto formatado na saída padrão.
     *
     * @param text O texto a ser escrito.
     */
    public static void write(String text) {
        System.out.print(format(text));
    }

    /**
     * Escreve texto formatado com uma nova linha na saída padrão.
     *
     * @param text O texto a ser escrito.
     */
    public static void writeline(String text) {
        System.out.println(format(text));
    }

    /**
     * Limpa a tela do console.
     */
    public static void clear() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

    /**
     * Gera um número aleatório dentro de um intervalo especificado.
     *
     * @param min O valor mínimo (inclusivo).
     * @param max O valor máximo (exclusivo).
     * @return Um número aleatório entre min e max.
     */
    public static int randomnumber(int min, int max) {
        Random r = new Random();
        return r.nextInt(min, max);
    }

    /**
     * Lê uma linha de entrada do usuário e a formata.
     *
     * @param text O texto a ser exibido antes da entrada.
     * @return A entrada do usuário.
     */
    public static String readline(String text) {
        System.out.print(format(text));
        String retorno = s.nextLine();
        return retorno;
    }

}

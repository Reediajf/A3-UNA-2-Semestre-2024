package atividade.engine.ui;

import atividade.engine.manager;

public class BannerFall {
    // Método estático que inicia a execução do banner animado
    public static void run() {
        // Array que contém as linhas do arte ASCII do banner
        String[] asciiArt = {
            "{verde_claro}{negrito}.S   .S_SSSs     .S_sSSs      sSSs   .S_sSSs     .S       S.    .S_sSSs      sSSSSs    sSSs    sSSs_sSSs     .S_sSSs      sSSs",
            "   .SS  .SS~SSSSS   .SS~YS%%b    d%%SP  .SS~YS%%b   .SS       SS.  .SS~YS%%b    d%%%%SP   d%%SP   d%%SP~YS%%b   .SS~YS%%b    d%%SP  ",
            "   S%S  S%S   SSSS  S%S   `S%b  d%S'    S%S   `S%b  S%S       S%S  S%S   `S%b  d%S'      d%S'    d%S'     `S%b  S%S   `S%b  d%S'    ",
            "   S%S  S%S    S%S  S%S    S%S  S%S     S%S    S%S  S%S       S%S  S%S    S%S  S%S       S%S     S%S       S%S  S%S    S%S  S%S     ",
            "   S&S  S%S SSSS%S  S%S    d*S  S&S     S%S    S&S  S&S       S&S  S%S    S&S  S&S       S&S     S&S       S&S  S%S    S&S  S&S     ",
            "   S&S  S&S  SSS%S  S&S   .S*S  S&S_Ss  S&S    S&S  S&S       S&S  S&S    S&S  S&S       S&S_Ss  S&S       S&S  S&S    S&S  Y&Ss    ",
            "   S&S  S&S    S&S  S&S_sdSSS   S&S~SP  S&S    S&S  S&S       S&S  S&S    S&S  S&S       S&S~SP  S&S       S&S  S&S    S&S  `S&&S   ",
            "   S&S  S&S    S&S  S&S~YSY%b   S&S     S&S    S&S  S&S       S&S  S&S    S&S  S&S sSSs  S&S     S&S       S&S  S&S    S&S    `S*S  ",
            "   d*S  S*S    S&S  S*S   `S%b  S*b     S*S    d*S  S*b       d*S  S*S    S*S  S*b `S%%  S*b     S*b       d*S  S*S    S*S     l*S  ",
            "  .S*S  S*S    S*S  S*S    S%S  S*S.    S*S   .S*S  S*S.     .S*S  S*S    S*S  S*S   S%  S*S.    S*S.     .S*S  S*S    S*S    .S*P  ",
            "sdSSS   S*S    S*S  S*S    S&S   SSSbs  S*S_sdSSS    SSSbs_sdSSS   S*S    S*S   SS_sSSS   SSSbs   SSSbs_sdSSS   S*S    S*S  sSS*S   ",
            "YSSY    SSS    S*S  S*S    SSS    YSSP  SSS~YSSY      YSSP~YSSY    S*S    SSS    Y~YSSY    YSSP    YSSP~YSSY    S*S    SSS  YSS'    ",
            "               SP   SP                                             SP                                           SP                  ",
            "               Y    Y                                              Y                                            Y                   {reset}"
        };

        // Itera sobre cada linha do arte ASCII
        for (String line : asciiArt) {
            // Escreve a linha formatada na saída
            manager.writeline(line);
            try {
                // Pausa a execução por 50 milissegundos para criar o efeito de animação
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // Restaura o status de interrupção da thread em caso de interrupção
                Thread.currentThread().interrupt();
            }
        }

        // Limpa a tela após exibir o banner
        manager.write("\033[H\033[2J");
        System.out.flush(); // Garante que a saída seja impressa imediatamente
    }
}

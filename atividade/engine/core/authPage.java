package atividade.engine.core;

import atividade.engine.manager;
import atividade.engine.server;

public class authPage {
    // Método estático que inicializa a página de autenticação
    public static void init() {
        // Solicita ao usuário que insira o nome de usuário e a senha
        String user = manager.readline("Usuario > ");
        String pass = manager.readline("Senha > ");
        
        // Tenta autenticar o usuário através do servidor
        if (!server.Auth(user, pass)) {
            // Se a autenticação falhar, exibe mensagem de erro e reinicia o processo
            manager.writeline("{vermelho}Usuario{reset} ou {vermelho}senha{reset} invalido!");
            init(); // Chama o método init novamente para tentar autenticar
        } else {
            // Se a autenticação for bem-sucedida, informa o usuário
            manager.writeline("Conectado como: {verde_claro}{negrito}" + user + "{reset}");
        }
    }
}

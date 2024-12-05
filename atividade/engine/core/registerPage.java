package atividade.engine.core;

import atividade.engine.manager;
import atividade.engine.server;

public class registerPage {
    // Método estático que inicia o processo de registro
    public static void init() {
        // Solicita ao usuário que insira um nome de usuário
        String user = manager.readline("Usuario > ");
        // Solicita ao usuário que insira uma senha
        String pass = manager.readline("Senha > ");

        // Tenta registrar o usuário com os dados fornecidos
        boolean whileControl = true;
        while (whileControl) {
            if (!server.Register(user, pass)) {
                // Se o registro falhar (usuário já existente), exibe uma mensagem de erro
                manager.writeline("{vermelho}Usuario{reset} ja existente!");
                // Reinicia o processo de registro
                init();
            } else {
                // Se o registro for bem-sucedido, exibe uma mensagem de sucesso
                manager.writeline("Conta {verde_claro}criada{reset} com sucesso!");
            }
            whileControl = false;
        }

    }
}

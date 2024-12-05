package atividade.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList; 
import java.util.List;

import atividade.engine.core.controllers.enemy;
import atividade.engine.core.controllers.item;
import atividade.engine.core.controllers.player;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados
 * e realizar operações relacionadas a jogadores, itens e inimigos.
 */
public class server {
    
    /**
     * Inicializa as listas de inimigos, itens e inventário, 
     * e carrega dados do banco de dados.
     */
	
	private static String mysql_host = ""; //Host do banco de dados.
	private static String mysql_username = ""; //Usuario do banco de dados.
	private static String mysql_password = ""; //Senha do banco de dados.

    public static void init() {
        manager.inimigos = new ArrayList<enemy>();
        manager.items = new ArrayList<item>(); 
        manager.inventario = new ArrayList<item>();
        manager.loja = new ArrayList<item>();

        manager.writeline("Carregando inimigos...");
        LoadEnemies();
        manager.writeline("Carregando items...");
        LoadItems();
    }
    
    /**
     * Carrega inimigos do banco de dados e adiciona à lista de inimigos.
     */
    static void LoadEnemies() {
        try {
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            try (Statement stmt = connection.createStatement()) {
                String query = "SELECT * FROM enemies";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("name");
                    String arte = rs.getString("art");
                    int ataque = rs.getInt("attack");
                    int defesa = rs.getInt("defense");
                    int evasao = rs.getInt("evasion");
                    int vida = rs.getInt("health");
                    int mana = rs.getInt("mana");
                    int xp = rs.getInt("xp");
                    int gold = rs.getInt("gold");

                    enemy inimigo = new enemy(id, nome, arte, ataque, defesa, evasao, vida, mana, xp, gold);
                    manager.inimigos.add(inimigo);
                }	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void SaveEquipedItem(int helmet, int chestplate, int boots, int sword, int playerid) {
        try (Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
             PreparedStatement stmt = connection.prepareStatement(
                     "UPDATE saves SET `helmet` = ?, `chestplate` = ?, `boots` = ?, `sword` = ? WHERE `id` = ?;")) {
            stmt.setInt(1, helmet);
            stmt.setInt(2, chestplate);
            stmt.setInt(3, boots);
            stmt.setInt(4, sword);
            stmt.setInt(5, playerid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Carrega itens do banco de dados e adiciona à lista de itens.
     */
    static void LoadItems() {
        try {
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            try (Statement stmt = connection.createStatement()) {
                String query = "SELECT * FROM itens";
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("name");
                    String desc = rs.getString("desc");
                    int ataque = rs.getInt("ataque");
                    int defesa = rs.getInt("defesa");
                    int vida = rs.getInt("vida");
                    int mana = rs.getInt("mana");
                    int tipo = rs.getInt("tipo");
                    int equipavel = rs.getInt("equipavel");
                    int valor = rs.getInt("compra");

                    item item = new item(id, nome, desc, ataque, defesa, 0, vida, mana, tipo, (equipavel != 0), valor);
                    manager.items.add(item);
                }	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Carrega o inventário de um jogador específico a partir do banco de dados.
     *
     * @param id O ID do jogador para o qual o inventário será carregado.
     */
    public static void LoadInventory(int id) {

        try {
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            try (PreparedStatement stmt = connection.prepareStatement("SELECT itemid FROM inventarios WHERE playerid = ?")) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    for (item itemLista : manager.items) {
                        if (itemLista.getId() == rs.getInt("itemid")) { 
                            manager.inventario.add(itemLista);
                        }
                    }
                }	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adiciona um item vinculado a um jogar na Base de Dados
     *
     * @param playerid O ID do jogador para o qual o item sera salva.
     * @param itemid o ID do item que sera adiciona
     */
    public static void SaveItem(int itemid, int playerid) {
        try {
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO inventarios (`itemid`, `playerid`) VALUES (?, ?); ")) {
                stmt.setInt(1, itemid);
                stmt.setInt(2, playerid);

                stmt.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove um item do inventario do player
     *
     * @param playerid O ID do jogador para o qual o item sera salva.
     * @param itemid o ID do item que sera adiciona
     */
    public static void DeleteItems(int itemid, int playerid) {
        try (Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM inventarios WHERE playerid = ? AND itemid = ?")) {
            stmt.setInt(1, playerid);
            stmt.setInt(2, itemid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); //
        }
    }


    /**
     * Autentica um usuário com base no nome de usuário e senha.
     *
     * @param user O nome de usuário.
     * @param pass A senha do usuário.
     * @return true se a autenticação for bem-sucedida, caso contrário false.
     */
    public static boolean Auth(String user, String pass) {
        try {
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            try (PreparedStatement stmt = connection.prepareStatement("SELECT id FROM users WHERE usuario=? and senha = MD5(?)")) {
                stmt.setString(1, user);
                stmt.setString(2, pass);
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    LoadPlayer(rs.getInt("id"));
                    LoadInventory(rs.getInt("id"));
                    return true;
                }	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Carrega os dados de um jogador específico a partir do banco de dados.
     *
     * @param id O ID do jogador.
     */
    static void LoadPlayer(int id) {
        manager.writeline("Carregando dados do jogador...");
        manager.account_id = id;
        try {
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM saves WHERE id = ?")) {
                stmt.setInt(1, id);

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String nome = rs.getString("name");
                    int vida = rs.getInt("health");
                    int mana = rs.getInt("mana");
                    int defesa = rs.getInt("defense");
                    int evasao = rs.getInt("evasion");
                    int kills = rs.getInt("kills");
                    int deaths = rs.getInt("deaths");
                    int money = rs.getInt("money");
                    int wave = rs.getInt("wave");
                    int xp = rs.getInt("xp");
                    int maxVida = rs.getInt("max_health");
                    int maxMana = rs.getInt("max_mana");
                    int str = rs.getInt("str");
                    int dex = rs.getInt("dex");
                    int intelligence = rs.getInt("int");
                    int pontos = rs.getInt("pontos");
                    
                    manager.jogador = new player(id, nome, vida, mana, defesa, evasao, kills, deaths, money, wave, maxVida, maxMana, xp, pontos);
                }	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Salva os dados do jogador no banco de dados.
     */
    public static void SavePlayer() {
        try {
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            try (PreparedStatement updateStmt = connection.prepareStatement(
                    "UPDATE saves SET health = ?, mana = ?, defense = ?, evasion = ?, kills = ?, deaths = ?, money = ?, wave = ?, xp = ?, max_health = ?, max_mana = ? WHERE id = ?")) {

                player jogador = manager.jogador;

                updateStmt.setInt(1, jogador.getVida());
                updateStmt.setInt(2, jogador.getMana());
                updateStmt.setInt(3, jogador.getDefesa());
                updateStmt.setInt(4, jogador.getEvasao());
                updateStmt.setInt(5, jogador.getKills());
                updateStmt.setInt(6, jogador.getDeaths());
                updateStmt.setInt(7, jogador.getMoney());
                updateStmt.setInt(8, jogador.getWave());
                updateStmt.setInt(9, jogador.getXP());
                updateStmt.setInt(10, jogador.getMaxVida());
                updateStmt.setInt(11, jogador.getMaxMana());
                updateStmt.setInt(12, manager.account_id);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Pega os jogadores com rank maior ou igual do jogador com ID informado
     *
     * @param Quantia Quantia de jogadores que vao retornar.
     * @param ID ID do jogador que sera o ponto base.
     * @return uma list com a classe de jogador com apenas id, nome e pontos.
     */
    public static List<player> GetPlayersWithPoints(int Quantia, int ID) {
        // Cria uma lista para armazenar objetos do tipo player
        List<player> jogadores = new ArrayList<player>();

        try {
            // Estabelece uma conexão com o banco de dados MySQL
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            // Prepara uma instrução SQL para selecionar jogadores com pontos maiores ou iguais aos do jogador especificado
            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT id, name, pontos FROM saves WHERE pontos >= (SELECT pontos FROM saves WHERE id = ?) " +
                    "AND id != ? ORDER BY pontos DESC LIMIT ?;")) {
                
                // Define os parâmetros da instrução preparada
                stmt.setInt(1, ID);    // ID do jogador cujos pontos queremos comparar
                stmt.setInt(2, ID);    // Exclui esse jogador dos resultados
                stmt.setInt(3, Quantia); // Limita o número de resultados retornados

                // Executa a consulta e obtém o conjunto de resultados
                ResultSet rs = stmt.executeQuery();
                
                // Itera pelo conjunto de resultados e cria objetos player
                while (rs.next()) {
                    // Cria um novo objeto player usando os dados do conjunto de resultados
                    player player = new player(
                        rs.getInt("id"),     // ID do jogador
                        rs.getString("name"), // Nome do jogador
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // Outros parâmetros inicializados como 0 
                        rs.getInt("pontos")   // Pontos do jogador
                    );

                    // Adiciona o objeto player à lista
                    jogadores.add(player);
                }   
            } catch (SQLException e) {
                // Imprime a pilha de exceções para quaisquer erros SQL durante a execução da consulta
                e.printStackTrace();
            }
        } catch (SQLException e) {
            // Imprime a pilha de exceções para quaisquer erros SQL durante a conexão
            e.printStackTrace();
        }

        // Retorna a lista de jogadores
        return jogadores;
    }

    
    /**
     * Registra um novo usuário no banco de dados.
     *
     * @param user O nome de usuário.
     * @param pass A senha do usuário.
     * @return true se o registro for bem-sucedido, caso contrário false.
     */
    public static boolean Register(String user, String pass) {
        try {
            Connection connection = DriverManager.getConnection(mysql_host, mysql_username, mysql_password);
            try (PreparedStatement stmt = connection.prepareStatement("SELECT id FROM users WHERE usuario=?")) {
                stmt.setString(1, user);
                ResultSet rs = stmt.executeQuery();
                
                while (rs.next()) {
                    return false; // O usuário já existe
                }	
            }
            
            try (PreparedStatement stmt1 = connection.prepareStatement("INSERT INTO `users` (`usuario`, `senha`) VALUES (?, md5(?))");
                 PreparedStatement stmt2 = connection.prepareStatement("INSERT INTO `saves` (`name`, `health`, `mana`, `defense`, `evasion`, `kills`, `deaths`, `money`, `wave`, `xp`, `max_health`, `max_mana`, `str`, `dex`, `int`, `helmet`, `chestplate`, `boots`, `sword`, `pontos`) VALUES (?, 100, 50, 0, 0, 0, 0, 0, 0, 1, 100, 50, 1, 1, 1, 1, 2, 3, 4, 0)")) {
                 
                stmt1.setString(1, user);
                stmt1.setString(2, pass);
                stmt1.execute();
                
                stmt2.setString(1, user);
                stmt2.execute();
                
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}

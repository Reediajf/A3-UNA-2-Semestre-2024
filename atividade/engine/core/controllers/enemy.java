package atividade.engine.core.controllers;

import java.util.Random;

import atividade.engine.manager;

/**
 * A classe Enemy representa um inimigo no jogo com atributos como ataque, defesa, evasão, vida, mana, ouro e experiência (xp).
 * 
 * A classe implementa mecânicas para calcular a força do inimigo com base em diferentes ondas do jogo.
 */
public class enemy {
    
    // Atributos do inimigo.
    private int id; // Identificador único do inimigo
    private String nome; // Nome do inimigo
    private String arte; // Representação gráfica do inimigo
    private int ataque; // Poder de ataque do inimigo
    private int defesa; // Poder de defesa do inimigo
    private int evasao; // Chance de evitar um ataque
    private int vida; // Vida atual do inimigo
    private int mana; // Mana do inimigo
    private int ouro; // Quantidade de ouro que o inimigo possui
    private int xp; // Experiência que o inimigo oferece ao ser derrotado

    // Multiplicadores para calcular os atributos do inimigo em diferentes ondas
    double vidaModeradaMultiplier = 1.05;
    double defesaModeradaMultiplier = 1.05;
    double evasaoModeradaMultiplier = 1.05;
    double xpModeradaMultiplier = 1.05;
    double lootModeradaMultiplier = 1.10;
    double ataqueModeradaMultiplier = 1.05; 
    
    double vidaAceleradaMultiplier = 1.15;
    double defesaAceleradaMultiplier = 1.10;
    double evasaoAceleradaMultiplier = 1.10;
    double xpAceleradaMultiplier = 1.03;
    double lootAceleradaMultiplier = 1.05;
    double ataqueAceleradaMultiplier = 1.10; 
    
    double vidaExponencialMultiplier = 1.20;
    double defesaExponencialMultiplier = 1.15;
    double evasaoExponencialMultiplier = 1.15;
    double xpExponencialMultiplier = 1.01;
    double lootExponencialMultiplier = 1.03;
    double ataqueExponencialMultiplier = 1.15; 

    /**
     * Construtor para criar um novo inimigo com atributos específicos.
     * 
     * @param id Identificador único do inimigo
     * @param nome Nome do inimigo
     * @param arte Representação gráfica do inimigo
     * @param ataque Poder de ataque do inimigo
     * @param defesa Poder de defesa do inimigo
     * @param evasao Chance de evitar um ataque
     * @param vida Vida inicial do inimigo
     * @param mana Mana do inimigo
     * @param xp Experiência que o inimigo oferece ao ser derrotado
     * @param ouro Quantidade de ouro que o inimigo possui
     */
    public enemy(int id, String nome, String arte, int ataque, int defesa, int evasao, int vida, int mana, int xp, int ouro) {
        this.id = id;
        this.nome = nome;
        this.arte = arte;
        this.ataque = ataque;
        this.defesa = defesa;
        this.evasao = evasao;
        this.vida = vida;
        this.mana = mana;
        this.ouro = ouro;
        this.xp = xp;
    }

    /**
     * Construtor de cópia que cria um novo inimigo com base em um inimigo existente, aplicando cálculos nos atributos.
     * 
     * @param en O inimigo a ser copiado
     */
    public enemy(enemy en) {
        this.id = en.id;
        this.nome = en.nome;
        this.arte = en.arte;
        this.ataque = (int) calcularAtaque(en.ataque);
        this.defesa = (int) calcularDefesa(en.defesa);
        this.evasao = (int) calcularEvasao(en.evasao);
        this.vida = (int) calcularVida(en.vida);
        this.xp = (int) calcularXP(en.xp);
        this.ouro = (int) calcularLoot(en.ouro);
        this.mana = en.mana;
    }

    // Métodos de acesso (getters)
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getArte() {
        return arte;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getEvasao() {
        return evasao;
    }

    public int getVida() {
        return vida;
    }

    public int getMana() {
        return mana;
    }

    /**
     * Verifica se o inimigo está morto.
     * 
     * @return true se a vida do inimigo for menor ou igual a zero; caso contrário, false.
     */
    public boolean isDead() {
        return vida <= 0;
    }

    /**
     * Processa a morte do inimigo, concedendo experiência e ouro ao jogador e registrando a morte.
     */
    public void processDead() {
        manager.jogador.AddXp(xp);
        manager.jogador.AddGold(ouro);
        manager.jogador.AddKill();
        manager.writeline(nome + " foi {vermelho}{negrito}morto{reset} {verde_claro}{negrito}+" + xp + "XP{reset} e {amarelo}{negrito}" + ouro + " Ouros{reset}!");
    }

    /**
     * Aplica dano ao inimigo, levando em conta a chance de evasão.
     * 
     * @param damage O dano a ser aplicado.
     */
    public void TakeDamage(int damage) {
        Random r = new Random();
        
        if (evasao < r.nextInt(100)) {
            int danoreal = damage + (-defesa);

            if (danoreal <= 0)
                danoreal = 0;
            
            vida -= danoreal;    
            if (vida <= 0)
                vida = 0;
            
            manager.writeline(nome + " levou {vermelho}" + danoreal + "{reset} de dano restando {vermelho}" + vida + "{reset} de vida");
        } else {
            manager.writeline(nome + " desviou!");
        }
    }

    // Métodos privados para calcular os atributos com base nas ondas do jogo
    
    private double calcularVida(int vidaBase) {
        if (manager.jogador.getWave() <= 20) {
            return vidaBase * Math.pow(vidaModeradaMultiplier, manager.jogador.getWave());
        } else if (manager.jogador.getWave() <= 50) {
            return vidaBase * Math.pow(vidaAceleradaMultiplier, manager.jogador.getWave() - 20) * Math.pow(vidaModeradaMultiplier, 20);
        } else {
            return vidaBase * Math.pow(vidaExponencialMultiplier, manager.jogador.getWave() - 50) * Math.pow(vidaAceleradaMultiplier, 30) * Math.pow(vidaModeradaMultiplier, 20);
        }
    }

    private double calcularDefesa(int defesaBase) {
        if (manager.jogador.getWave() <= 20) {
            return defesaBase * Math.pow(defesaModeradaMultiplier, manager.jogador.getWave());
        } else if (manager.jogador.getWave() <= 50) {
            return defesaBase * Math.pow(defesaAceleradaMultiplier, manager.jogador.getWave() - 20) * Math.pow(defesaModeradaMultiplier, 20);
        } else {
            return defesaBase * Math.pow(defesaExponencialMultiplier, manager.jogador.getWave() - 50) * Math.pow(defesaAceleradaMultiplier, 30) * Math.pow(defesaModeradaMultiplier, 20);
        }
    }

    private double calcularEvasao(int evasaoBase) {
        if (manager.jogador.getWave() <= 20) {
            return evasaoBase * Math.pow(evasaoModeradaMultiplier, manager.jogador.getWave());
        } else if (manager.jogador.getWave() <= 50) {
            return evasaoBase * Math.pow(evasaoAceleradaMultiplier, manager.jogador.getWave() - 20) * Math.pow(evasaoModeradaMultiplier, 20);
        } else {
            return evasaoBase * Math.pow(evasaoExponencialMultiplier, manager.jogador.getWave() - 50) * Math.pow(evasaoAceleradaMultiplier, 30) * Math.pow(evasaoModeradaMultiplier, 20);
        }
    }

    private double calcularAtaque(int ataqueBase) {
        if (manager.jogador.getWave() <= 20) {
            return ataqueBase * Math.pow(ataqueModeradaMultiplier, manager.jogador.getWave());
        } else if (manager.jogador.getWave() <= 50) {
            return ataqueBase * Math.pow(ataqueAceleradaMultiplier, manager.jogador.getWave() - 20) * Math.pow(ataqueModeradaMultiplier, 20);
        } else {
            return ataqueBase * Math.pow(ataqueExponencialMultiplier, manager.jogador.getWave() - 50) * Math.pow(ataqueAceleradaMultiplier, 30) * Math.pow(ataqueModeradaMultiplier, 20);
        }
    }

    private double calcularXP(int xpBase) {
        if (manager.jogador.getWave() <= 20) {
            return xpBase * Math.pow(xpModeradaMultiplier, manager.jogador.getWave());
        } else if (manager.jogador.getWave() <= 50) {
            return xpBase * Math.pow(xpAceleradaMultiplier, manager.jogador.getWave() - 20) * Math.pow(xpModeradaMultiplier, 20);
        } else {
            return xpBase * Math.pow(xpExponencialMultiplier, manager.jogador.getWave() - 50) * Math.pow(xpAceleradaMultiplier, 30) * Math.pow(xpModeradaMultiplier, 20);
        }
    }

    private double calcularLoot(int lootBase) {
        if (manager.jogador.getWave() <= 20) {
            return lootBase * Math.pow(lootModeradaMultiplier, manager.jogador.getWave());
        } else if (manager.jogador.getWave() <= 50) {
            return lootBase * Math.pow(lootAceleradaMultiplier, manager.jogador.getWave() - 20) * Math.pow(lootModeradaMultiplier, 20);
        } else {
            return lootBase * Math.pow(lootExponencialMultiplier, manager.jogador.getWave() - 50) * Math.pow(lootAceleradaMultiplier, 30) * Math.pow(lootModeradaMultiplier, 20);
        }
    }
    
    /**
     * Retorna uma string com informações atuais do inimigo, incluindo seu nome e vida.
     * 
     * @return Informações do inimigo.
     */
    public String CurrentInfo() {
        return nome + " (Vida: {vermelho}" + vida + "{reset})";
    }

    @Override
    public String toString() {
        return nome + " (Vida: {vermelho}" + vida + "{reset}, Ataque: " + ataque + ", Defesa: " + defesa + ")";
    }
}

package atividade.engine.core.controllers;

import java.util.Random;
import atividade.engine.manager;
import atividade.engine.server;

/**
 * Classe que representa um jogador no jogo..
 */
public class player {
    private int id;             // Identificador único do jogador.
    private String nome;        // Nome do jogador.
    private int vida;           // Vida atual do jogador
    private int mana;           // Mana atual do jogador
    private int defesa;         // Defesa do jogador
    private int evasao;         // Chance de evasão do jogador
    private int kills;          // Número de kills do jogador
    private int deaths;         // Número de mortes do jogador
    private int money;          // Dinheiro do jogador
    private int wave;           // Onda atual no jogo
    private int pontos;         // Pontos acumulados pelo jogador
    private int maxVida;        // Vida máxima do jogador
    private int maxMana;        // Mana máxima do jogador
    private int ataque = 100;   // Ataque do jogador
    private int xp;             // Experiência do jogador
    private boolean morto = false; // Estado de morte do jogador


    // IDs de equipamentos do jogador
    private int swordID = 0;
    private int helmetID = 0;
    private int chestplateID = 0;
    private int bootsID = 0;



    /**
     * Construtor da classe player.
     *
     * @param id Identificador do jogador
     * @param nome Nome do jogador
     * @param vida Vida inicial do jogador
     * @param mana Mana inicial do jogador
     * @param defesa Defesa do jogador
     * @param evasao Evasão do jogador
     * @param kills Número de kills do jogador
     * @param deaths Número de mortes do jogador
     * @param money Dinheiro do jogador
     * @param wave Onda inicial do jogador
     * @param maxVida Vida máxima do jogador
     * @param maxMana Mana máxima do jogador
     * @param xp Experiência inicial do jogador
     * @param pontos Pontos acumulados pelo jogador
     */
    public player(int id, String nome, int vida, int mana, int defesa, int evasao,
                  int kills, int deaths, int money, int wave, int maxVida, int maxMana, int xp, int pontos) {
        this.id = id;
        this.nome = nome;
        this.vida = vida;
        this.mana = mana;
        this.defesa = defesa;
        this.evasao = evasao;
        this.kills = kills;
        this.deaths = deaths;
        this.money = money;
        this.wave = wave;
        this.xp = xp;
        this.maxVida = maxVida;
        this.maxMana = maxMana;
        this.pontos = pontos;
    }

    // Métodos de acesso (getters)
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getMana() {
        return mana;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getEvasao() {
        return evasao;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getMoney() {
        return money;
    }
    
    public int getAttack() {
        return ataque;
    }

    public int getWave() {
        return wave;
    }

    public int getMaxVida() {
        return maxVida;
    }

    public int getMaxMana() {
        return maxMana;
    }
    
    public int getXP() {
        return xp;
    }

    public  int getChestplate(){ return chestplateID;}
    public  int getHelmet(){ return helmetID;}
    public  int getSword(){ return swordID;}
    public  int getBoots(){ return bootsID;}

    public void setChestplate(int id){this.chestplateID = id;}
    public void setHelmet(int id){this.helmetID = id;}
    public void setSword(int id){this.swordID = id;}
    public void setBoots(int id){this.bootsID = id;}


    /**
     * Calcula o nível do jogador com base na experiência.
     *
     * @return Nível do jogador
     */
    public int getLevel() {
        return CalculateLevelFromXP();
    }

    /**
     * Aplica dano ao jogador e verifica se ele morre.
     *
     * @param damage Dano a ser aplicado.
     */
    public void TakeDamage(int damage) {
        Random r = new Random();
        
        // Verifica se o jogador desvia do ataque.
        if (evasao < r.nextInt(100)) {
            vida -= (damage - defesa);	
            manager.writeline(nome + " levou {vermelho}"+ (damage - defesa) +"{reset} de dano restando {vermelho}"+ vida + "{reset} de vida");
        } else {
            manager.writeline(nome + " Desviou");
        }
        
        if (vida <= 0)
            morto = true; // Jogador morreu.
    }
    
    /**
     * Calcula o nível do jogador com base na experiência.
     *
     * @return Nível calculado.
     */
    public int CalculateLevelFromXP() {
        for (int level = 1; level <= 100; level++) { 
            int requiredXP = CalculateLevel(level);
            if (xp < requiredXP) {
                return Math.max(1, level - 1); 
            }
        }
        return 100; 
    }
    
    /**
     * Calcula a experiência necessária para um nível específico.
     *
     * @param level Nível a ser calculado
     * @return Experiência necessária para o nível
     */
    public int CalculateLevel(int level) {
        if (level >= 1 && level <= 16) {
            return (int)(Math.pow(level, 2) + 6 * level);
        } else if (level >= 17 && level <= 31) {
            return (int)(2.5 * Math.pow(level, 2) - 40.5 * level + 360);
        } else if (level >= 32) {
            return (int)(4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
        } else {
            return 0;
        }
    }
    
    /**
     * Avança para a próxima onda.
     */
    public void NextWave() {
        wave++;
    }
    
    /**
     * Reseta os atributos do jogador para o início da onda.
     */
    public void ResetWave() {
        vida = maxVida;
        mana = maxMana;
        morto = false;
        wave = 1;
    }
    
    /**
     * Adiciona experiência ao jogador.
     *
     * @param amount Quantidade de experiência a ser adicionada
     */
    public void AddXp(int amount) {
        xp += amount;
    }
    
    /**
     * Incrementa o número de kills do jogador.
     */
    public void AddKill() {
        kills++;
    }
    
    /**
     * Adiciona ouro ao jogador.
     *
     * @param amount Quantidade de ouro a ser adicionada
     */
    public void AddGold(int amount) {
        money += amount;
    }
    
    /**
     * Remove ouro do jogador.
     *
     * @param amount Quantidade de ouro a ser removida
     */
    public void RemoveGold(int amount) {
        money -= amount;
    }
    
    /**
     * Verifica se o jogador tem ouro suficiente.
     *
     * @param amount Quantidade a ser verificada
     * @return Verdadeiro se o jogador tiver ouro suficiente, falso caso contrário
     */
    public boolean HaveGold(int amount) {
        return money >= amount;
    }
    
    /**
     * Verifica se o jogador está morto.
     *
     * @return Verdadeiro se o jogador estiver morto, falso caso contrário
     */
    public boolean IsDead() {
        return morto;
    }
    
    /**
     * Salva os dados do jogador.
     */
    public void SaveData() {
        try {
            server.SaveEquipedItem(helmetID, chestplateID, bootsID, swordID, id);
            server.SavePlayer();
        } catch (Exception e) {
            manager.writeline("Erro ao salvar os dados do jogador: " + e.getMessage());
        }
    }


    /**
     * Retorna informações atuais do jogador.
     *
     * @return String com informações do jogador
     */
    public String CurrentInfo() {
        return nome + " (Vida: {vermelho}"+vida+"{reset})";
    }

    
    /**
     * Usa um item que restaura vida e mana.
     *
     * @param vida Quantidade de vida a ser restaurada
     * @param mana Quantidade de mana a ser restaurada
     */
    public void UseItem(int vida, int mana) {
        this.vida += vida;
        this.mana += mana;
    }

    
    /**
     * Adiciona status ao jogador.
     *
     * @param vida Aumento de vida
     * @param mana Aumento de mana
     * @param defesa Aumento de defesa
     * @param evasao Aumento de evasão
     * @param ataque Aumento de ataque
     */
    public void AddStatus(int vida, int mana, int defesa, int evasao, int ataque) {
        this.maxVida += vida;
        this.maxMana += mana;
        this.defesa += defesa;
        this.evasao += evasao;
        this.ataque += ataque;
    }

    /**
     * Remove status do jogador.
     *
     * @param vida Redução de vida
     * @param mana Redução de mana
     * @param defesa Redução de defesa
     * @param evasao Redução de evasão
     * @param ataque Redução de ataque
     */
    public void RemoveStatus(int vida, int mana, int defesa, int evasao, int ataque) {
        this.maxVida -= vida;
        this.maxMana -= mana;
        this.defesa -= defesa;
        this.evasao -= evasao;
        this.ataque -= ataque;
    }

    @Override
    public String toString() {
        return nome + " (Vida: {vermelho}" + vida + "{reset}, Mana: {azul_claro}" + mana + "{reset}, Defesa: {cinza}" + defesa + 
               "{reset}, Evasão: {ciano}" + evasao + 
               "{reset}, Ouro: {amarelo_claro}" + money + "{reset}, Onda: {amarelo}" + wave + "{reset}, Level: {verde}" + getLevel() + "{reset})";
    }
    public void Status() {
        manager.writeline("Nome: {azul}" + nome +
                "\n{reset}, Level: {verde}" + getLevel() +
                "\n{reset}, vida: {vermelho}" + vida + "/" + maxVida +
                "\n{reset}, Mana: {azul}" + mana + "/" + maxMana +
                "\n{reset}, Attack: {vermelho}" + ataque +
                 "\n{reset}, Defesa: {azul}" + defesa +
                "\n{reset}, Evasao: {ciano}" + evasao + "{reset}");

    }


    public int getHelmetId() {
        return this.helmetID;
    }

    public int getChestplateId() {
        return this.chestplateID;
    }

    public int getBootsId() {

        return this.bootsID;
    }

    public int getSwordId() {
        return this.swordID;
    }
}

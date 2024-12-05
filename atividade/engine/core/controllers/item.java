package atividade.engine.core.controllers;

import atividade.engine.manager; // Importa a classe helper, presumivelmente para auxiliar em operações de jogador
import atividade.engine.server;


/**
 * Classe que representa um item no jogo.
 */
public class item {
    // Atributos do item
    private  int id;              // Identificador único do item
    private String nome;         // Nome do item
    private String desc;         // Descrição do item
    private  int ataque;          // Valor de ataque do item
    private  int defesa;          // Valor de defesa do item
    private  int evasao;          // Valor de evasão do item
    private  int vida;            // Quantidade de vida que o item fornece
    private  int mana;            // Quantidade de mana que o item fornece
    private  int tipo;            // Tipo do item (pode representar categorias como arma, armadura, etc.)
    private  boolean equipavel;   // Indica se o item pode ser equipado ou utilizado diretamente
    private int valor;           // Preço do item em ouro

    /**
     * Construtor da classe item.
     *
     * @param id        Identificador do item.
     * @param nome      Nome do item.
     * @param desc      Descrição do item.
     * @param ataque    Valor de ataque do item.
     * @param defesa    Valor de defesa do item.
     * @param evasao    Valor de evasão do item.
     * @param vida      Quantidade de vida que o item fornece.
     * @param mana      Quantidade de mana que o item fornece.
     * @param tipo      Tipo do item.
     * @param equipavel Indica se o item pode ser equipado.
     * @param valor     Preço do item em ouro.
     */
    public item(int id, String nome, String desc, int ataque, int defesa, int evasao, int vida, int mana, int tipo, boolean equipavel, int valor) {
        this.id = id;
        this.nome = nome;
        this.ataque = ataque;
        this.desc = desc;
        this.defesa = defesa;
        this.evasao = evasao;
        this.vida = vida;
        this.mana = mana;
        this.tipo = tipo;
        this.equipavel = equipavel;
        this.valor = valor;
    }

    // Métodos getters para acessar os atributos do item
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
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

    public String getDesc() {
        return desc;
    }

    public int getValor() {
        return valor; // Retorna o preço do item
    }

    /**
     * Ativa o item, dependendo se ele é equipável ou não.
     */
    public void Active() {
        if (equipavel)
            Equip(); // Se o item é equipável, equipa-o
        else
            Use(); // Caso contrário, usa o item
    }


    /**
     * Utiliza o item, aplicando seus efeitos ao jogador.
     */
     void Use() {
        manager.jogador.UseItem(vida, mana); // Chama o método UseItem do jogador com os efeitos do item
    }

    /**
     * Equipa o item, aplicando seus status ao jogador.
     */
    public void Equip() {
        // Verificação do tipo de item
        switch (tipo) {
            case 0: // Capacete
                if (manager.jogador.getHelmet() != 0) {
                    manager.writeline("Você já está usando um capacete. Não é possível equipar outro.");
                    return; // Retorna imediatamente, evitando múltiplos equipamentos
                }
                manager.jogador.setHelmet(id);
                manager.writeline("Capacete equipado com sucesso!");
                break;

            case 1: // Armadura
                if (manager.jogador.getChestplate() != 0) {
                    manager.writeline("Você já está usando uma armadura. Não é possível equipar outra.");
                    return;
                }
                manager.jogador.setChestplate(id);
                manager.writeline("Armadura equipada com sucesso!");
                break;

            case 2: // Botas
                if (manager.jogador.getBoots() != 0) {
                    manager.writeline("Você já está usando botas. Não é possível equipar outras.");
                    return;
                }
                manager.jogador.setBoots(id);
                manager.writeline("Botas equipadas com sucesso!");
                break;

            case 3: // Espada
                if (manager.jogador.getSword() != 0) {
                    manager.writeline("Você já está usando uma espada. Não é possível equipar outra.");
                    return;
                }
                manager.jogador.setSword(id);
                manager.writeline("Espada equipada com sucesso!");
                break;

            default:
                manager.writeline("Tipo de item inválido.");
                return;
        }

        // Atualiza o estado no banco de dados após equipar
        manager.jogador.SaveData();
    }


    /**
     * Des-equipar o item, removendo seus status do jogador.
     */
    public void UnEquip() {
        manager.jogador.RemoveStatus(vida, mana, defesa, evasao, ataque); // Chama o metodo RemoveStatus do jogador
    }

    /**
     * Retorna uma representação em string do item, incluindo seus atributos.
     *
     * @return String representando o item.
     */
    @Override
    public String toString() {
        return "Nome: " + nome + "\nDescrição: " + desc + "\n(Vida: " + vida + ", Mana: " + mana +
                ", Defesa: " + defesa + ", Ataque: " + ataque + ", Evasão: " + evasao + ")\nPreço: " + "{amarelo}" + valor + "{reset}";

    }
}

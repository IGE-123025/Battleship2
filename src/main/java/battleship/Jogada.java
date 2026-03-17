package battleship;

public class Jogada {

    private int numero;
    private String jogador;
    private String descricao;

    public Jogada(int numero, String jogador, String descricao) {
        this.numero = numero;
        this.jogador = jogador;
        this.descricao = descricao;
    }

    public int getNumero() {
        return numero;
    }

    public String getJogador() {
        return jogador;
    }

    public String getDescricao() {
        return descricao;
    }
}

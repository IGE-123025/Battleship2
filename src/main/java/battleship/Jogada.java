package battleship;

public class Jogada {

    private final int numero;
    private final String jogador;
    private final String descricao;

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

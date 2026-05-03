package battleship;

public class Jogada {

    private int numero;
    private String jogador;
    private String descricao;

    public Jogada(JogadaData data) {
        this.numero = data.numero();
        this.jogador = data.jogador();
        this.descricao = data.descricao();
    }

    public Jogada(int numero, String jogador, String descricao) {
        this(new JogadaData(numero, jogador, descricao));
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

import java.awt.Color;

/*
 * Contem todas as constantes utilizadas pelas classes
 */
public interface Config {

    public static final int LARGURA_JANELA = 880;
    public static final int ALTURA_JANELA = 500;

    public static final int LARGURA_PNLSTAT = LARGURA_JANELA-20;
    public static final int ALTURA_PNLSTAT = 40;

    public static final int LARGURA_CENA = LARGURA_JANELA-20;
    public static final int ALTURA_CENA = 440;

    public static final int TAMANHO_TILE = 20;
    public static final int COLUNAS_TILE = LARGURA_CENA / TAMANHO_TILE;
    public static final int LINHAS_TILE = ALTURA_CENA / TAMANHO_TILE;

    public static final Color COR_BRANCO = Color.WHITE;
    public static final Color COR_AZUL = Color.BLUE;
    public static final Color COR_VERDE = Color.GREEN;
    public static final Color COR_VERMELHO = Color.RED;
    public static final Color COR_PRETO = Color.BLACK;

    public static final Color COR_JOGADOR = Color.BLUE;
    public static final Color COR_LIXO = Color.GREEN;
    public static final Color COR_INIMIGO = Color.RED;
    public static final Color COR_FUNDO = Color.BLACK;
    public static final Color COR_BLOCO = Color.WHITE;
    public static final Color COR_AGUA_SUJA = Color.GREEN;
    public static final Color COR_AGUA_LIMPA = Color.BLUE;

    public static final int LARGURA_JOGADOR = 20;
    public static final int ALTURA_JOGADOR = 40;
    public static final int BUFFER_MOVIMENTO = 2;
    public static final int CONTADOR_MOVIMENTO = 7;

    public static final int X_INICIAL = 2*TAMANHO_TILE; 
    public static final int Y_INICIAL = ALTURA_CENA / 2;

    public static final int DESLOCAMENTO = 4;
    public static final int LIMITE_PULO = 20;
    public static final int ENERGIA_MAXIMA = 1000;
    public static final int ENERGIA_MAXIMA_PU2 = 200;
    public static final int ENERGIA_MAXIMA_PU3 = 200;

    public static final int MAIN_SLEEP_TIME = 18;
}
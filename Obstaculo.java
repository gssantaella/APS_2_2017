import java.awt.Rectangle;
import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obstaculo extends Tile implements Config {

    private String nomeImg;
    private int x;
    private int y;

    public Obstaculo(String nome, String nomeImg, Color c, int i, int j) {
        super(nome, i, j);
        this.nomeImg = nomeImg;
        carregaInfo();
        setCor(c);
    }

    @Override
    protected void initObjeto() {
        x = coluna * TAMANHO_TILE;
        y = linha * TAMANHO_TILE;
        setX(x);
        setY(y);
        setLargura(TAMANHO_TILE);
        setAltura(TAMANHO_TILE);
        caixaColisao = new Rectangle(x, y, TAMANHO_TILE, TAMANHO_TILE);
    }
    
    protected void carregaInfo() {
        try {
            imagem = ImageIO.read(getClass().getResource("img/"+nomeImg+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
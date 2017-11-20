import java.awt.Rectangle;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Sprite {

    private boolean visivel;
    protected int x;
    protected int y;
    protected int largura;
    protected int altura;
    protected Color cor;
    protected Rectangle caixaColisao;
    protected BufferedImage imagem;

    public Sprite() {
        visivel = true;
    }

    public void apaga() {
        visivel = false;
    }

    public boolean isVisivel() {
        return visivel;
    }

    protected void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public void setImagem(BufferedImage imagem) {
        this.imagem = imagem;
    }

    public BufferedImage getImagem() {
        return imagem;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public void setCor(Color c) {
        this.cor = c;
    }

    public Color getCor() {
        return this.cor;
    }

    public Rectangle2D getRect() {
        return new Rectangle2D.Double(
            getX(),
            getY(),
            getLargura(),
            getAltura()
        );
    }

    public Rectangle getCaixaColisao() {
        return caixaColisao;
    }
}

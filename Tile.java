
import java.awt.Rectangle;

public abstract class Tile extends Sprite implements Config {

    protected int linha;
    protected int coluna;
    protected String nome;

    public Tile(String nome, int i, int j) {
        this.nome = nome;
        this.linha = i;
        this.coluna = j;
        initObjeto();
    }
    
    protected abstract void initObjeto();

    public String getNome() {
        return nome;
    }
}
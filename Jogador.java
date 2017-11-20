import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Jogador extends Sprite implements Config {

    public int pontos = 0;

    private BufferedImage atualFrame;
    private BufferedImage[] anda_Dir;
    private BufferedImage[] anda_Esq;
    
    private int atualNumFrame = 0;
    private int contadorMovimento = 0;
    private int atualX;
    private int atualY;
    private boolean pulando;
    private boolean caindo = false;
    private int contaPulo = 0;
    private int contaEnergia = 0;
    
    private int energia = ENERGIA_MAXIMA;
    private int atualColuna = atualX / TAMANHO_TILE;
    private int atualLinha = (atualY / TAMANHO_TILE)+1;
    private int ultimaDirecao = KeyEvent.VK_RIGHT;
    
    private int deslocX = DESLOCAMENTO;
    private boolean invuneravel = false;
    private int limitePulo = LIMITE_PULO;
    private int contaColec2;
    private int contaColec3;
    private int contaObs3;

    public boolean proximaFase = false;
    
    public Jogador(int x, int y, int l, int a) {
        initJogador(x, y, l, a);
    }

    private void initJogador(int x, int y, int l, int a) {
        setX(x);
        setY(y);
        setLargura(l);
        setAltura(a);
        setCor(COR_JOGADOR);
        atualX = x;
        atualY = y;
        caixaColisao = new Rectangle(x, y, l, a);

        anda_Esq = new BufferedImage[BUFFER_MOVIMENTO];
        anda_Dir = new BufferedImage[BUFFER_MOVIMENTO];
        
        carregaInfo();
        atualFrame=anda_Dir[0];
    }

    private void carregaInfo() {
        try { 
            anda_Dir[0]=ImageIO.read(getClass().getResource("img/charR1.png"));
            anda_Dir[1]=ImageIO.read(getClass().getResource("img/charR2.png"));
            anda_Esq[0]=ImageIO.read(getClass().getResource("img/charL1.png"));
            anda_Esq[1]=ImageIO.read(getClass().getResource("img/charL2.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int direction) {
        
        switch (direction) {
            case KeyEvent.VK_LEFT:
                atualX = atualX - deslocX;
                
                if (atualX <= 0) {
                    atualX = 0;
                }

                caixaColisao.setLocation(atualX, atualY);
                setX(atualX);

                setNumeroFrame();
                atualFrame = anda_Esq[atualNumFrame];

                ultimaDirecao = KeyEvent.VK_LEFT;
                break;
            
            case KeyEvent.VK_RIGHT:
                atualX = atualX + deslocX;

                if (atualX >= LARGURA_CENA-TAMANHO_TILE) {
                    atualX = LARGURA_CENA-TAMANHO_TILE;
                }
                
                caixaColisao.setLocation(atualX, atualY);
                setX(atualX);

                setNumeroFrame();
                atualFrame = anda_Dir[atualNumFrame];

                ultimaDirecao = KeyEvent.VK_RIGHT;
                break;
            default:
                break;
        }

        // Gasta energia e poderes no movimento
        if (contaColec3 > 0) {
            contaColec3--;
        }
        else {
            invuneravel = false;
        }

        if (contaObs3 > 0) {
            contaObs3--;
        }
        else {
            deslocX = DESLOCAMENTO;
        }

        if (!invuneravel) {
            energia--;
            contaEnergia++;
        }
        else {
            contaObs3 = 0;
            limitePulo = LIMITE_PULO;
        }

        atualColuna = atualX / TAMANHO_TILE;
        atualLinha = (atualY / TAMANHO_TILE)+1;
        
        contadorMovimento++;
    }

    public void checaEstadoPulo() {
        if (pulando) {
            if (contaPulo < limitePulo) {
                
                if (contaColec2 > 0) {
                    contaColec2--;
                } else {
                    limitePulo = LIMITE_PULO;
                }
                atualY -= DESLOCAMENTO;
                
                caixaColisao.setLocation(atualX, atualY);
                setY(atualY);
            }
            
            contaPulo++;
            
            if (contaPulo >= limitePulo) {
                pulando = false;
                contaPulo = 0;
                caindo=true;
            }
        }
    }

    public void checaEstadoCaindo(){
        if (caixaColisao.getMaxY() >= ALTURA_CENA){
            energia = -1;
            reinicializa(0);
        }
        
        if (pulando){
            return;
        }
        
        if (caindo){
            atualY += DESLOCAMENTO;
            atualLinha = (atualY / TAMANHO_TILE)+1;
            caixaColisao.setLocation(atualX, atualY);
            setY(atualY);
        }
        
        int baixoEsqX = (int) caixaColisao.getMinX()+1;
        int baixoDirX = (int) caixaColisao.getMaxX()-1;
        
        int tileAbaixoDirX = baixoDirX / TAMANHO_TILE;
        int tileAbaixoEsqX = baixoEsqX / TAMANHO_TILE;
        
        if (atualLinha+1 >= LINHAS_TILE || tileAbaixoDirX >= COLUNAS_TILE){
            return;
        }
        
        // verifica se existe um Bloco embaixo de um dos pes
        // se sim, faz com que o jogador pare de cair
        if (!((Cena.tiledMap[atualLinha+1][tileAbaixoDirX]) instanceof Bloco)
            && !((Cena.tiledMap[atualLinha+1][tileAbaixoEsqX]) instanceof Bloco)){
            caindo=true;
            return;
        }
        
        caindo=false;
    }

    public void checaColisaoBloco(){
        
        int peY = (int) (caixaColisao.getMaxY());
        
        if (pulando) {
            int linhaAcima = (int) ((caixaColisao.getMinY()-1) / TAMANHO_TILE);
            int colCantoSupEsq = (int) (caixaColisao.getMinX() / TAMANHO_TILE);
            int colCantoSupDir = (int) ((caixaColisao.getMaxX()) / TAMANHO_TILE);

            if (colCantoSupDir == COLUNAS_TILE) { 
                colCantoSupDir--;
            }

            if (atualLinha >= 0) {

                // Se algum dos cantos superiores encontrar com um Bloco
                // inicia a fase de cair
                if (Cena.tiledMap[linhaAcima][colCantoSupEsq] instanceof Bloco) {
                    if (Cena.tiledMap[linhaAcima][colCantoSupEsq].getCaixaColisao().intersects(caixaColisao)) {
                        pulando = false;
                        contaPulo = 0;
                        caindo = true;
                        return;
                    }
                }
                if (Cena.tiledMap[linhaAcima][colCantoSupDir] instanceof Bloco) {
                    if (Cena.tiledMap[linhaAcima][colCantoSupDir].getCaixaColisao().intersects(caixaColisao)) {
                        pulando = false;
                        contaPulo = 0;
                        caindo = true;
                        return;
                    }
                }
            }
        }

        if (ultimaDirecao == KeyEvent.VK_RIGHT) {
            int peX = (int) caixaColisao.getMinX();
            
            // Seleciona o Tile na frente do personagem
            int linTileAFrente = ((peY-1) / TAMANHO_TILE);
            int colTileAFrente = (peX / TAMANHO_TILE)+1;
            
            if (colTileAFrente < COLUNAS_TILE) {

                // Se existe um bloco a frente do personagem, nao deixa passar
                if (Cena.tiledMap[linTileAFrente][colTileAFrente] instanceof Bloco) {
                    if (Cena.tiledMap[linTileAFrente][colTileAFrente].getCaixaColisao().intersects(caixaColisao)) {
                        atualX -= DESLOCAMENTO;
                        caixaColisao.setLocation(atualX, atualY);
                        setX(atualX);
                        atualColuna = atualX / TAMANHO_TILE;
                    }
                }
                if (Cena.tiledMap[atualLinha][atualColuna] instanceof Bloco) {
                    if (Cena.tiledMap[atualLinha][atualColuna].getCaixaColisao().intersects(caixaColisao)) {
                        atualX -= DESLOCAMENTO;
                        caixaColisao.setLocation(atualX, atualY);
                        setX(atualX);
                        atualColuna = atualX / TAMANHO_TILE;
                    }
                }
            }
        }
        else {
            int peX = (int) caixaColisao.getMaxX();
            
            int linTileAFrente = ((peY-1) / TAMANHO_TILE);
            int colTileAFrente = (peX / TAMANHO_TILE)-1;
            
            if (colTileAFrente >= 0) {
                if (Cena.tiledMap[linTileAFrente][colTileAFrente] instanceof Bloco) {
                    if (Cena.tiledMap[linTileAFrente][colTileAFrente].getCaixaColisao().intersects(caixaColisao)) {
                        atualX += DESLOCAMENTO;
                        caixaColisao.setLocation(atualX, atualY);
                        setX(atualX);
                        atualColuna = atualX / TAMANHO_TILE;
                    }
                }
                if (Cena.tiledMap[atualLinha][atualColuna] instanceof Bloco) {
                    if (Cena.tiledMap[atualLinha][atualColuna].getCaixaColisao().intersects(caixaColisao)) {
                        atualX += DESLOCAMENTO;
                        caixaColisao.setLocation(atualX, atualY);
                        setX(atualX);
                        atualColuna = atualX / TAMANHO_TILE;
                    }
                }
            }   
        }

    }

    public void checaColecionaveis() {
        checaColecionaveis(atualLinha, atualColuna);
        checaColecionaveis(atualLinha, atualColuna+1);
    }

    private void checaColecionaveis(int l, int c) {

        if ((caixaColisao.getMaxX() / TAMANHO_TILE) < COLUNAS_TILE) {

            if (!(Cena.tiledMap[l][c] instanceof Bloco) && Cena.tiledMap[l][c] != null) {
                if (Cena.tiledMap[l][c].getCaixaColisao().intersects(caixaColisao)) {
                    
                    if (Cena.tiledMap[l][c].getNome().equalsIgnoreCase("col1")) {
                        energia = ENERGIA_MAXIMA;
                        Cena.esvaziaTile(l, c);
                    }
                    else if (Cena.tiledMap[l][c].getNome().equalsIgnoreCase("col2")) {
                        contaColec2 = ENERGIA_MAXIMA_PU2;
                        limitePulo = (int) LIMITE_PULO * 2;
                        Cena.esvaziaTile(l, c);
                    }
                    else if (Cena.tiledMap[l][c].getNome().equalsIgnoreCase("col3")) {
                        contaColec3 = ENERGIA_MAXIMA_PU3;
                        invuneravel = true;
                        Cena.esvaziaTile(l, c);
                    }
                    else if (Cena.tiledMap[l][c].getNome().equalsIgnoreCase("obs1")) {
                        if (!invuneravel) {
                           energia -= 10;
                           contaEnergia += 10;
                           invuneravel = true;
                           contaColec3 = 1;
                        }
                    }
                    else if (Cena.tiledMap[l][c].getNome().equalsIgnoreCase("obs2")) {
                        if (!invuneravel) {
                           contaColec2 = 20;
                           limitePulo = (int) LIMITE_PULO / 2;
                        }
                    }
                    else if (Cena.tiledMap[l][c].getNome().equalsIgnoreCase("obs3")) {
                        if (!invuneravel) {
                            contaObs3 = 20;
                            deslocX = (int) DESLOCAMENTO / 2;
                        }
                    }
                    else if (Cena.tiledMap[l][c].getNome().equalsIgnoreCase("fim")) {
                        proximaFase = true;
                    }
                }
            }
        }

    }

    public void morre() {
        atualX = X_INICIAL;
        atualY = Y_INICIAL;
        
        atualColuna = atualX / TAMANHO_TILE;
        atualLinha = (atualY / TAMANHO_TILE)+1;
        caixaColisao.setLocation(atualX, atualY);
        setX(atualX);
        setY(atualY);
        ultimaDirecao = KeyEvent.VK_RIGHT;
        caindo = true;
        move(KeyEvent.VK_RIGHT);
        energia = ENERGIA_MAXIMA;
        contaColec2 = 0;
        contaColec3 = 0;
    }

    // Posiciona o personagem de acordo com a direcao da qual ele veio
    public void reinicializa(int dir) {
        atualY -= DESLOCAMENTO;
        atualLinha = (atualY / TAMANHO_TILE)+1;
        caindo = true;
        if (dir == 1) {
            atualX = DESLOCAMENTO;
            
            atualColuna = atualX / TAMANHO_TILE;
            caixaColisao.setLocation(atualX, atualY);
            setX(atualX);
            setY(atualY);
            ultimaDirecao = KeyEvent.VK_RIGHT;
            move(KeyEvent.VK_RIGHT);
        }
        else if (dir == -1) {
            atualX = LARGURA_CENA - TAMANHO_TILE - DESLOCAMENTO;
            
            atualColuna = atualX / TAMANHO_TILE;
            caixaColisao.setLocation(atualX, atualY);
            setX(atualX);
            setY(atualY);
            ultimaDirecao = KeyEvent.VK_LEFT;
            move(KeyEvent.VK_LEFT);
        }
    }

    public int foraDaTela() {
        if (atualX >= LARGURA_CENA-TAMANHO_TILE) {
            return 1;
        }
        else if (atualX <= 0) {
            return -1;
        }
        return 0;
    }

    private void setNumeroFrame() {
        atualNumFrame  = contadorMovimento / CONTADOR_MOVIMENTO;
        atualNumFrame %= 2;
        
        if (contadorMovimento > 2*CONTADOR_MOVIMENTO-1) {
            contadorMovimento = 0;
        }
    }

    public void pula() {
        this.pulando = true;
        this.contaPulo = 0;

        if (ultimaDirecao == KeyEvent.VK_RIGHT) {
            atualFrame = anda_Dir[1];
        }
        else {
            atualFrame = anda_Esq[1];
        }
    }

    public boolean getPulando() {
        return pulando;
    }

    public boolean getCaindo() {
        return caindo;
    }

    public int getEnergia() {
        return energia;
    }

    public int getEnergiaPU2() {
        return contaColec2;
    }

    public int getEnergiaPU3() {
        return contaColec3;
    }
    
    public int getAtualX() {
        return atualX;
    }
    
    public int getAtualY() {
        return atualY;
    }
    
    public int getEnergiaGasta() {
        return contaEnergia;
    }

    public void zeraEnergiaGasta() {
        contaEnergia = 0;
    }

    public boolean estaVivo() {
        if (energia > 0) { return true; }
        return false;
    }

    public BufferedImage getFrameAtual(){
        return atualFrame;
}
    
}
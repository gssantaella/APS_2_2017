
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Font;

public class PainelCena extends JPanel implements Config {

    private Jogador p;
    private boolean fim = false;

    public PainelCena() {
        
        this.setSize(LARGURA_CENA, ALTURA_CENA);
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(null);

        this.setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.drawImage(Cena.bg,0,0,LARGURA_CENA,ALTURA_CENA, null);

        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);

        g2.setStroke(new BasicStroke(5));
        for (int i = 0; i < LINHAS_TILE; i++){
            for (int j = 0; j < COLUNAS_TILE; j++){
                if(Cena.tiledMap[i][j] != null){
                    g2.drawImage(Cena.tiledMap[i][j].getImagem(), Cena.tiledMap[i][j].getX(), Cena.tiledMap[i][j].getY(),null);
                }
            }
        }
        
        // desenha o personagem
        g2.drawImage(p.getFrameAtual(),p.getX(),p.getY(),null);

        // mensagem de fim de jogo
        if (fim) {
            g2.setColor(COR_PRETO);
            g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 60));
            g2.drawString("PARABENS", 250, ALTURA_JANELA/2-150);
            g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 20));
            g2.drawString("Voce salvou a cidade", 275, ALTURA_JANELA/2-100);
            g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 20));
            g2.drawString("Energia gasta", 315, ALTURA_JANELA/2-50);
            g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 40));
            g2.drawString(""+p.pontos, 330, ALTURA_JANELA/2);
            g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 20));
            g2.drawString("Aperte R para recomeçar ou Q para sair", 200, ALTURA_JANELA/2+70);
        }
    }

    public void addJogador(Jogador p) {
        this.p = p;
    }
    
    public void setFim(boolean b) {
        fim = b;
    }
}
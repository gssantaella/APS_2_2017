
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class PainelStatus extends JPanel implements Config {

    Jogador p;

    public PainelStatus(){
        this.setSize(LARGURA_PNLSTAT, ALTURA_PNLSTAT);
        this.setBackground(COR_PRETO);
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // energia do jogador
        g2.setColor(COR_BRANCO);
        g2.drawString("ENERGIA", 10, 10);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(10, 15, 200, 15);
        g2.setColor(COR_VERDE);
        g2.fillRect(10, 15, p.getEnergia()/(ENERGIA_MAXIMA/200), 15);
        
        // energia do pulo aumentado ou diminuido
        g2.setColor(COR_BRANCO);
        g2.drawString("PU2: "+ p.getEnergiaPU2(), 400, 10);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(400, 15, 100, 15);
        g2.setColor(COR_AZUL);
        g2.fillRect(400, 15, p.getEnergiaPU2()/(ENERGIA_MAXIMA_PU2/100), 15);

        // energia da invulnerabilidade
        g2.setColor(COR_BRANCO);
        g2.drawString("PU3: "+ p.getEnergiaPU3(), 550, 10);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(550, 15, 100, 15);
        g2.setColor(COR_VERMELHO);
        g2.fillRect(550, 15, p.getEnergiaPU3()/(ENERGIA_MAXIMA_PU3/100), 15);
    }

    public void addJogador(Jogador p) {
        this.p = p;
    }
}
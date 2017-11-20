
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// Tela inicial com titulo e infos
public class Tela extends JPanel implements Config {

    private boolean ini = false;
    private BufferedImage img1, img2,img3,img4,img5,img6,img7;

    public Tela() {
        this.setBackground(COR_PRETO);
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (!ini) {
            pintaTitulo(g2);
        }
        else {
            carregaImg();
            pintaInfo(g2);
        }
    }

    private void pintaTitulo(Graphics2D g2) {
        g2.setColor(COR_VERDE);

        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 60));
        g2.drawString("PLATAFORMA", 250, ALTURA_JANELA/2-50);
        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 15));
        g2.drawString("- Pressione S para iniciar ou I para informações -", 200, ALTURA_JANELA/2+50);
        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 10));
        g2.drawString("Q para sair", 400, 450);
    }

    private void carregaImg() {
        try {
            img1 = ImageIO.read(getClass().getResource("img/col1.png"));
            img2 = ImageIO.read(getClass().getResource("img/col2.png"));
            img3 = ImageIO.read(getClass().getResource("img/col3.png"));

            img4 = ImageIO.read(getClass().getResource("img/obs1.png"));
            img5 = ImageIO.read(getClass().getResource("img/obs2.png"));
            img6 = ImageIO.read(getClass().getResource("img/obs3.png"));

            img7 = ImageIO.read(getClass().getResource("img/fim.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pintaInfo(Graphics2D g2) {
        g2.setColor(COR_VERDE);

        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 25));
        g2.drawString("INFORMAÇÕES", 30, 30);
        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN , 15));
        g2.drawString("Utilize as setas ESQUERDA e DIREITA para se mover e ESPAÇO para pular.", 30, 60);

        g2.drawImage(img1, 30, 75, null);
        g2.drawString("Restaura energia", 60, 90);
        g2.drawImage(img2, 30, 105, null);
        g2.drawString("Aumenta pulo", 60, 120);
        g2.drawImage(img3, 30, 135, null);
        g2.drawString("Invincibilidade", 60, 150);

        g2.drawImage(img4, 280, 75, null);
        g2.drawString("Rouba energia", 310, 90);
        g2.drawImage(img5, 280, 105, null);
        g2.drawString("Diminui velocidade", 310, 120);
        g2.drawImage(img6, 280, 135, null);
        g2.drawString("Diminui pulo", 310, 150);

        g2.drawImage(img7, 30, 165, null);
        g2.drawString("Termina fase", 60, 180);

        g2.drawString("A cidade está destruida por conta da poluição e lixo espalhado.", 30, 250);
        g2.drawString("Cabe a você reinvindicar os meios para a reconstrução.", 30, 280);

        g2.drawString("Pressione S para iniciar ou Q para sair do jogo.", 200, 450);

    }

    public void setIni(boolean b) {
        ini = b;
    }
}

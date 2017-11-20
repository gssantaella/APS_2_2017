
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

// Frame principal do jogo
public class FrameJogo extends JFrame implements Config {

    private Tela tela = new Tela();
    private PainelJogo pnlJogo;

    public FrameJogo(PainelJogo pnlJogo) {

        // encontra dimensoes da tela
        Toolkit tKit = this.getToolkit();
        Dimension dim = tKit.getScreenSize();
        this.pnlJogo = pnlJogo;

        // frame no centro da tela
        this.setLocation((dim.width - LARGURA_JANELA)/2, (dim.height - ALTURA_JANELA)/2);
        this.setSize(LARGURA_JANELA, ALTURA_JANELA);

        this.setTitle("Plataforma");
        
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.telaEmFoco(this.pnlJogo);

    }

    private void telaEmFoco(PainelJogo pnlJogo) {

        tela.setBackground(COR_PRETO);

        tela.addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    initJogo(pnlJogo);
                    tela.setVisible(false);
                }
                else if (e.getKeyCode() == KeyEvent.VK_I) {
                    tela.setIni(true);
                    tela.repaint();
                }
                else if (e.getKeyCode() == KeyEvent.VK_Q) {
                    System.exit(0);
                }
            }
            public void keyReleased(KeyEvent e){
                // --
            }
            public void keyTyped(KeyEvent e){
                // --
            }
        });

        this.add(tela);

        tela.grabFocus();
        tela.requestFocusInWindow();
    }

    private void initJogo(PainelJogo pnlJogo) {
        this.add(pnlJogo);
        pnlJogo.grabFocus();
        pnlJogo.requestFocusInWindow();
    }

}
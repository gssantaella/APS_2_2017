
import java.awt.Color;
import javax.swing.JPanel;

public class PainelJogo extends JPanel implements Config{

    private PainelStatus pnlStat = new PainelStatus();
    private PainelCena pnlCena = new PainelCena();
    private ControllerTeclado ctrl;
    private Jogador p;

    public PainelJogo() {
        this.setRequestFocusEnabled(true);
        this.setSize(LARGURA_JANELA, ALTURA_JANELA);
        this.setLayout(null);
        this.setBackground(Color.BLACK);

        this.add(pnlStat);
        pnlStat.setLocation(TAMANHO_TILE/2, TAMANHO_TILE/2);

        this.add(pnlCena);
        pnlCena.setLocation(TAMANHO_TILE/2, ALTURA_PNLSTAT+TAMANHO_TILE/2);

        ctrl = new ControllerTeclado();
        this.addKeyListener(ctrl);
    }

    public void addJogador(Jogador p) {
        this.p = p;
        pnlCena.addJogador(p);
        pnlStat.addJogador(p);
    }

    public void repintaJogo() {
        pnlCena.repaint();
        pnlStat.repaint();
    }

    public void setFim(boolean b) {
        pnlCena.setFim(b);
    }

}
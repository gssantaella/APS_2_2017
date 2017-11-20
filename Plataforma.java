

// Eh o nosso main, faz as chamadas iniciais
public class Plataforma implements Config {

    public static void main(String[] args) {

        // inicializa o PainelDeJogo
        PainelJogo pnlJogo = new PainelJogo();

        // comeca a frame principal do jogo
        FrameJogo gf = new FrameJogo(pnlJogo);

        // inicializa e comeca a thread principal
        GerenteDeJogo gm = new GerenteDeJogo(pnlJogo);
        gm.start();
        
    }
}

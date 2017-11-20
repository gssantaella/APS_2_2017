
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class GerenteDeJogo extends Thread implements Config {

    private boolean jogoRodando;
    private PainelJogo pnlJogo;
    private Cena cena;
    private int faseAtual = 1;
    private int cenaAtual = 1;
    private Jogador p;
    
    public GerenteDeJogo(PainelJogo pnlJogo) {

        this.cena = new Cena();
        cena.initFase(faseAtual, cenaAtual);
        
        this.p = new Jogador(
            X_INICIAL, 
            Y_INICIAL, 
            LARGURA_JOGADOR, 
            ALTURA_JOGADOR);

        this.pnlJogo = pnlJogo;
        this.pnlJogo.addJogador(p);

        this.jogoRodando = true;
    }
    
    @Override
    public void run() {
        while(jogoRodando) {

            // Quando termina a ultima fase contabiliza a energia gasta
            // e vai pra tela final mostrando msg de fim de jogo
            if (p.proximaFase) {
                if (p.pontos == 0 && faseAtual == 3) {
                    p.pontos = p.getEnergiaGasta();
                    pnlJogo.setFim(true);
                }
                faseAtual++;
                cenaAtual = 1;
                cena.initFase(faseAtual, cenaAtual);
                p.morre();
                p.proximaFase = false;
            }

            // Se o jogador morre, recomeca na primeira cena da fase atual
            // a energia que foi gasta se mantem
            if (!p.estaVivo()) {
                cenaAtual = 1;
                cena.initFase(faseAtual, cenaAtual);
                p.morre();
            }

            // descobre de qual direcao o personagem veio
            // para que ele possa ser colocado na posicao correta
            int dirProximaTela = p.foraDaTela();
            if (dirProximaTela != 0) {
                cenaAtual += dirProximaTela;
                // Nao existe tela 0 e nem outra depois da 5
                // entao o jogador nao pode passar por elas
                try {
                    cena.initFase(faseAtual, cenaAtual);
                    p.reinicializa(dirProximaTela);
                } catch (Exception e) {
                    System.out.println("Nada nessa direcao");
                }
                
            }

            p.checaEstadoCaindo();
            p.checaEstadoPulo();

            gerenciaTeclas();

            p.checaColecionaveis();
            p.checaColisaoBloco();
            
            pnlJogo.repintaJogo();
            
            // espera um tempo para recomecar a thread
            try {
                Thread.sleep(MAIN_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
    
    // associa acoes as teclas pressionadas
    private void gerenciaTeclas() {

        HashSet<Integer> teclasAtuais = ControllerTeclado.getChavesAtivas();
        
        if (teclasAtuais.contains(KeyEvent.VK_RIGHT)) {
            p.move(KeyEvent.VK_RIGHT);
        } 
        else if (teclasAtuais.contains(KeyEvent.VK_LEFT)) {
            p.move(KeyEvent.VK_LEFT);
        }

        if (teclasAtuais.contains(KeyEvent.VK_SPACE)) {
            // o personagem tem que ter aterrisado do ultimo pulo
            // para pular novamente
            if(!p.getPulando() && !p.getCaindo()){
                p.pula();
            }
        }

        // reinicia o jogo
        if (teclasAtuais.contains(KeyEvent.VK_R) && faseAtual == 4) {
            pnlJogo.setFim(false);
            faseAtual = 1;
            cenaAtual = 1;
            p.pontos = 0;
            p.zeraEnergiaGasta();
            cena.initFase(faseAtual, cenaAtual);
            p.morre();
        }

        // Sai do jogo
        if (teclasAtuais.contains(KeyEvent.VK_Q)) {
            System.exit(0);
        }
    }

    public Jogador getJogador() {
        return p;
    }
    
}
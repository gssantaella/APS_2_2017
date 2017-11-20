
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

/*
 * O Controller do teclado é o que escuta as teclas pressionadas
 * e as registra no chavesAtivas
 */
public class ControllerTeclado implements KeyListener {

    private static HashSet<Integer> chavesAtivas;

    public ControllerTeclado() {
        chavesAtivas = new HashSet<Integer>();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        chavesAtivas.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        chavesAtivas.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    public static HashSet<Integer> getChavesAtivas() {
        return chavesAtivas;
    }

}
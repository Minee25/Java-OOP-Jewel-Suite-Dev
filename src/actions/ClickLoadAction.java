import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClickLoadAction implements MouseListener {
    private App app;

    public ClickLoadAction(App app) {
        this.app = app;
    }

    public void mouseClicked(MouseEvent e) {
        if (!app.hasData()) {
            app.loadFile();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
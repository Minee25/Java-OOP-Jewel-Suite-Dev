import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class CellMouseAction implements MouseListener {
    private int r, c;
    private JPanel cell;
    private GridDisplay grid;

    public CellMouseAction(int r, int c, JPanel cell, GridDisplay grid) {
        this.r = r;
        this.c = c;
        this.cell = cell;
        this.grid = grid;
    }

    public void mouseEntered(MouseEvent e) {
        grid.handleMouseIn(r, c, cell);
    }

    public void mouseExited(MouseEvent e) {
        grid.handleMouseOut(r, c, cell);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

// รวมพวกแอคชั่นปุ่ม
public class Actions {


    public static class AboutAction implements ActionListener {
        private JButton btn;

        public AboutAction(JButton btn) {
            this.btn = btn;
        }

        public void actionPerformed(ActionEvent e) {
            Display.showAbout(btn);
        }
    }

    public static class CalculateAction implements ActionListener {
        private App app;

        public CalculateAction(App app) {
            this.app = app;
        }

        public void actionPerformed(ActionEvent e) {
            app.calculate();
        }
    }

    public static class ClearAction implements ActionListener {
        private App app;

        public ClearAction(App app) {
            this.app = app;
        }

        public void actionPerformed(ActionEvent e) {
            app.clearData();
        }
    }

    public static class ExitAction implements ActionListener {
        private App app;

        public ExitAction(App app) {
            this.app = app;
        }

        public void actionPerformed(ActionEvent e) {
            app.exitApp();
        }
    }

    public static class LoadAction implements ActionListener {
        private App app;

        public LoadAction(App app) {
            this.app = app;
        }

        public void actionPerformed(ActionEvent e) {
            app.loadFile();
        }
    }

    public static class SummaryAction implements ActionListener {
        private App app;

        public SummaryAction(App app) {
            this.app = app;
        }

        public void actionPerformed(ActionEvent e) {
            app.showSummary();
        }
    }

    public static class ThemeAction implements ActionListener {
        private App app;
        private JButton btn;

        public ThemeAction(App app, JButton btn) {
            this.app = app;
            this.btn = btn;
        }

        public void actionPerformed(ActionEvent e) {
            app.changeTheme(btn);
        }
    }

    public static class CellMouseAction implements MouseListener {
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
            grid.detailCell(this.r, this.c);
        }
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    public static class ClickLoadAction implements MouseListener {
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


}
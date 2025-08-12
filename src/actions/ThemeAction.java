import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ThemeAction implements ActionListener {
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
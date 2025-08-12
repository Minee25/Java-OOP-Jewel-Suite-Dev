import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class AboutAction implements ActionListener {
    private JButton btn;
    
    public AboutAction(JButton btn) {
        this.btn = btn;
    }
    
    public void actionPerformed(ActionEvent e) {
        Display.showAbout(btn);
    }
}
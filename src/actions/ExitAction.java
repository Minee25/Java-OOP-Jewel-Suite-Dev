import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitAction implements ActionListener {
    private App app;
    
    public ExitAction(App app) {
        this.app = app;
    }
    
    public void actionPerformed(ActionEvent e) {
        app.exitApp();
    }
}
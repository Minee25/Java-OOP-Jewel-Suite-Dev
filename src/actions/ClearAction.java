import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearAction implements ActionListener {
    private App app;
    
    public ClearAction(App app) {
        this.app = app;
    }
    
    public void actionPerformed(ActionEvent e) {
        app.clearData();
    }
}
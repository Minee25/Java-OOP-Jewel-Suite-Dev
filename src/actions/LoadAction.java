import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadAction implements ActionListener {
    private App app;
    
    public LoadAction(App app) {
        this.app = app;
    }
    
    public void actionPerformed(ActionEvent e) {
        app.loadFile();
    }
}
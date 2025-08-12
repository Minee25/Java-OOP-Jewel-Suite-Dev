import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculateAction implements ActionListener {
    private App app;
    
    public CalculateAction(App app) {
        this.app = app;
    }
    
    public void actionPerformed(ActionEvent e) {
        app.calculate();
    }
}
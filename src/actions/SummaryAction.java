import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SummaryAction implements ActionListener {
    private App app;
    
    public SummaryAction(App app) {
        this.app = app;
    }
    
    public void actionPerformed(ActionEvent e) {
        app.showSummary();
    }
}
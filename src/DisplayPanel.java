import javax.swing.*;

public abstract class DisplayPanel extends JPanel {
    protected FileData data;

    public DisplayPanel(FileData data) {
        this.data = data;
    }

    public abstract void refresh();
}


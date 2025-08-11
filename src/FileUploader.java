import javax.swing.*;
import java.awt.*;

public class FileUploader extends JPanel {
    private FileData data;

    public FileUploader(FileData data) {
        this.data = data;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Settings.COLOR_GRAY, 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        setOpaque(true);

        add(Box.createVerticalStrut(150));

        JLabel icon = new JLabel("?", SwingConstants.CENTER);
        icon.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 32));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel(Settings.FILE_TITLE, SwingConstants.CENTER);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel(Settings.FILE_SUB, SwingConstants.CENTER);
        sub.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(icon);
        add(Box.createVerticalStrut(10));
        add(title);
        add(Box.createVerticalStrut(5));
        add(sub);
    }

    public void refresh() {
        revalidate();
        repaint();
    }
}

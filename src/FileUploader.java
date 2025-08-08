import javax.swing.*;
import java.awt.*;

public class FileUploader extends DisplayPanel {

    public FileUploader(FileData data) {
        super(data);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        setOpaque(true);
        setBackground(Color.WHITE);
        add(Box.createVerticalStrut(150));
        JLabel icon = new JLabel("📁", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel(Settings.FILE_TITLE, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel(Settings.FILE_SUB, SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(icon);
        add(Box.createVerticalStrut(10));
        add(title);
        add(Box.createVerticalStrut(5));
        add(sub);
    }

    @Override
    public void refresh() {
        revalidate();
        repaint();
    }
}

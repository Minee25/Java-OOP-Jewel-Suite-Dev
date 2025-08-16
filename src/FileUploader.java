import javax.swing.*;
import java.awt.*;

// หน้ากรอบแสดงให้อัพโหลดไฟล์หน้าแรก
public class FileUploader extends JPanel {
    private FileData data;
    private boolean isDarkTheme;

    private JLabel iconLabel;
    private JLabel titleLabel;
    private JLabel subLabel;

    public FileUploader(FileData data) {
        this.data = data;
        this.isDarkTheme = isCurrentThemeDark();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Settings.COLOR_GRAY, 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20))); // ทำเส้นและกรอบ
        setOpaque(true);

        add(Box.createVerticalStrut(150));

        iconLabel = new JLabel("?", SwingConstants.CENTER);
        iconLabel.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 32));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (isDarkTheme) {
            iconLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            iconLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }

        titleLabel = new JLabel(Settings.FILE_TITLE, SwingConstants.CENTER);
        titleLabel.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (isDarkTheme) {
            titleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            titleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        subLabel = new JLabel(Settings.FILE_SUB, SwingConstants.CENTER);
        subLabel.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (isDarkTheme) {
            subLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            subLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }

        add(iconLabel);
        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(5));
        add(subLabel);
    }

    // เช็คว่าตอนนี้ใช้ธีมอะไร
    private boolean isCurrentThemeDark() {
        return UIManager.getLookAndFeel().getName().contains("Monokai");
    }

    public void refresh() {
        this.isDarkTheme = isCurrentThemeDark();
        updateTextColors();
        revalidate();
        repaint();
    }

    private void updateTextColors() {
        if (isDarkTheme) {
            if (iconLabel != null) {
                iconLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
            }
            if (titleLabel != null) {
                titleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
            }
            if (subLabel != null) {
                subLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
            }
        } else {
            if (iconLabel != null) {
                iconLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
            }
            if (titleLabel != null) {
                titleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
            }
            if (subLabel != null) {
                subLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
            }
        }
    }
}

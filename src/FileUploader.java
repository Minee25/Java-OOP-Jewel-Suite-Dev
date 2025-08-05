import javax.swing.*;
import java.awt.*;

public class FileUploader extends DisplayPanel {

    public FileUploader(FileData data) {
        super(data);
        setLayout(null);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int w = getWidth();
        int h = getHeight();

        // วาดกรอบ
        g.setColor(UIManager.getColor("Component.borderColor"));
        g.drawRect(20, 20, w-40, h-40);

        // ไอคอน
        g.setColor(UIManager.getColor("Label.foreground"));
        g.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        String icon = "📁";
        FontMetrics fm = g.getFontMetrics();
        int iconX = (w - fm.stringWidth(icon)) / 2;
        int iconY = h/2 - 10;
        g.drawString(icon, iconX, iconY);

        // ข้อความหลัก
        g.setFont(new Font("Segoe UI", Font.BOLD, 14));
        String title = Settings.FILE_TITLE;
        fm = g.getFontMetrics();
        int titleX = (w - fm.stringWidth(title)) / 2;
        int titleY = h/2 + 20;
        g.drawString(title, titleX, titleY);

        // ข้อความรอง
        g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        String sub = Settings.FILE_SUB;
        fm = g.getFontMetrics();
        int subX = (w - fm.stringWidth(sub)) / 2;
        int subY = h/2 + 40;
        g.drawString(sub, subX, subY);
    }

    @Override
    public void refresh() {
        revalidate();
        repaint();
    }


}
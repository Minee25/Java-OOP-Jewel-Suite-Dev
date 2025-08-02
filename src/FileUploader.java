import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

// อัพโหลดไฟล์
public class FileUploader extends JPanel {

    private FileData fileData;
    private boolean hasData = false;
    private DropTarget dropTarget;

    public FileUploader(FileData data) {
        this.fileData = data;
        this.hasData = (data.getRows() > 0 && data.getCols() > 0);

        setOpaque(false);
        setLayout(null);

    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Colors.BG_PANEL);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        
        g2.setColor(new Color(255, 255, 255, 60));
        g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/3, 19, 19);

        drawDropZone(g2);
        g2.dispose();
    }
    // ส่วนวางไฟล์ กรอบ
    private void drawDropZone(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();


        g2.setColor(Colors.BLUE);
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{12, 8}, 0));
        g2.drawRoundRect(30, 30, width-60, height-60, 20, 20);


        g2.setColor(Colors.BLUE);
        Font iconFont = new Font("SF Pro Display", Font.PLAIN, 48);
        g2.setFont(iconFont);
        String icon = "📁";
        FontMetrics fm = g2.getFontMetrics();
        int iconX = (width - fm.stringWidth(icon)) / 2;
        int iconY = height/2 - 20;
        g2.drawString(icon, iconX, iconY);


        g2.setColor(Colors.TEXT_DARK);
        g2.setFont(new Font("SF Pro Display", Font.BOLD, 16));
        String title = Settings.FILE_TITLE;
        fm = g2.getFontMetrics();
        int titleX = (width - fm.stringWidth(title)) / 2;
        int titleY = height/2 + 20;
        g2.drawString(title, titleX, titleY);


        g2.setColor(Colors.TEXT_LIGHT);
        g2.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        String subtitle = Settings.FILE_SUB;
        fm = g2.getFontMetrics();
        int subX = (width - fm.stringWidth(subtitle)) / 2;
        int subY = height/2 + 45;
        g2.drawString(subtitle, subX, subY);
    }

    public void refresh() {
        this.hasData = (fileData.getRows() > 0 && fileData.getCols() > 0);
        revalidate();
        repaint();
    }


}
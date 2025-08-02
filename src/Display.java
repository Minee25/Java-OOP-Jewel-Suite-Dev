import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// คลาสแจ้งเตือนหรืออื่นๆ
public class Display {

    // แจ้วเตือนข้อความ
    public static void showMessage(Component parent, String title, String message, int type) {
        JOptionPane.showMessageDialog(parent, message, title, type);

    }

    // abot
    public static void showAbout(Component parent) {
        JDialog aboutDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "About Jewel Suite", true);
        aboutDialog.setSize(850, 480);
        aboutDialog.setLocationRelativeTo(parent);
        aboutDialog.setResizable(false);
        aboutDialog.setUndecorated(true);

        JPanel main = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Colors.BG_MAIN);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2.setColor(new Color(255, 255, 255, 80));
                g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/3, 19, 19);
                
                g2.setColor(Colors.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                
                g2.dispose();
            }
        };
        main.setOpaque(false);
        main.setBorder(new EmptyBorder(35, 35, 35, 35));

        JLabel title = new JLabel(Settings.ABOUT_TITLE, SwingConstants.CENTER);
        title.setFont(new Font("SF Pro Display", Font.BOLD, 32));
        title.setForeground(Colors.BLUE);

        JLabel subtitle = new JLabel(Settings.ABOUT_DISTRIBUTION, SwingConstants.CENTER);
        subtitle.setFont(new Font("SF Pro Display", Font.PLAIN, 16));
        subtitle.setForeground(Colors.TEXT_LIGHT);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.add(title);
        header.add(Box.createVerticalStrut(8));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(30));

        JPanel team = new JPanel(new GridLayout(1, 3, 25, 0));
        team.setOpaque(false);

        team.add(makeMemberBox(Settings.IMG1, Settings.MEMBER1, Settings.ID1, Settings.JOB1));
        team.add(makeMemberBox(Settings.IMG2, Settings.MEMBER2, Settings.ID2, Settings.JOB2));
        team.add(makeMemberBox(Settings.IMG3, Settings.MEMBER3, Settings.ID3, Settings.JOB3));

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBorder(new EmptyBorder(20, 0, 0, 0));
        buttons.setOpaque(false);

        JButton closeButton = ButtonHelper.createButton(Settings.BTN_CLOSE_ABOUT, Colors.DANGER, 100, 45);
        closeButton.addActionListener(e -> aboutDialog.dispose());
        buttons.add(closeButton);

        main.add(header, BorderLayout.NORTH);
        main.add(team, BorderLayout.CENTER);
        main.add(buttons, BorderLayout.SOUTH);

        aboutDialog.add(main);
        aboutDialog.setVisible(true);
    }

    // ฟังชั่นสร้างผู้พัฒนา
    private static JPanel makeMemberBox(String imagePath, String name, String studentId, String position) {
        JPanel memberPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Colors.BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                
                g2.setColor(new Color(255, 255, 255, 80));
                g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/3, 15, 15);
                
                g2.setColor(Colors.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                
                g2.dispose();
            }
        };
        memberPanel.setLayout(new BoxLayout(memberPanel, BoxLayout.Y_AXIS));
        memberPanel.setOpaque(false);
        memberPanel.setBorder(new EmptyBorder(25, 20, 25, 20));

        JLabel imageLabel = makeImage(imagePath);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("SF Pro Display", Font.BOLD, 15));
        nameLabel.setForeground(Colors.TEXT_DARK);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("Studant ID: " + studentId, SwingConstants.CENTER);
        idLabel.setFont(new Font("SF Pro Display", Font.PLAIN, 13));
        idLabel.setForeground(Colors.TEXT_LIGHT);
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel positionLabel = new JLabel("Post: " + position, SwingConstants.CENTER);
        positionLabel.setFont(new Font("SF Pro Display", Font.PLAIN, 13));
        positionLabel.setForeground(Colors.PURPLE);
        positionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        memberPanel.add(imageLabel);
        memberPanel.add(Box.createVerticalStrut(15));
        memberPanel.add(nameLabel);
        memberPanel.add(Box.createVerticalStrut(8));
        memberPanel.add(idLabel);
        memberPanel.add(Box.createVerticalStrut(8));
        memberPanel.add(positionLabel);

        return memberPanel;
    }

    // สร้างรูป
    private static JLabel makeImage(String imagePath) {
        try {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            
            JLabel imageLabel = new JLabel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    g2.setColor(new Color(0, 0, 0, 20));
                    g2.fillOval(2, 2, getWidth()-4, getHeight()-4);
                    
                    g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, getWidth(), getHeight()));
                    super.paintComponent(g);
                    
                    g2.dispose();
                }
            };
            imageLabel.setIcon(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            return imageLabel;
        } catch (Exception e) {
            JLabel defaultImage = new JLabel("?", SwingConstants.CENTER);
            defaultImage.setFont(new Font("SF Pro Display", Font.PLAIN, 72));
            return defaultImage;
        }
    }
}
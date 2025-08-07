import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Display {
    public static void showMessage(Component parent, String title, String msg, int type) {
        JOptionPane.showMessageDialog(parent, msg, title, type);
    }
    //โชว์หน้าต่างของabout
    public static void showAbout(Component parent) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "About " + Settings.APP_TITLE,
                true);
        dialog.setSize(850, 550);
        dialog.setLocationRelativeTo(null);//เซ็ตให้แสดงตรงกลาง
        dialog.setResizable(false);//เอาไว้ไม่ให้ปรับขนาดได้

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel(Settings.ABOUT_TITLE, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel sub = new JLabel(Settings.ABOUT_DISTRIBUTION, SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(title);
        top.add(Box.createVerticalStrut(8));
        top.add(sub);
        top.add(Box.createVerticalStrut(25));

        JPanel team = new JPanel(new GridLayout(1, 3, 25, 0));

        team.add(makeMember(Settings.IMG1, Settings.MEMBER1, Settings.ID1, Settings.JOB1));
        team.add(makeMember(Settings.IMG2, Settings.MEMBER2, Settings.ID2, Settings.JOB2));
        team.add(makeMember(Settings.IMG3, Settings.MEMBER3, Settings.ID3, Settings.JOB3));

        JPanel btns = new JPanel(new FlowLayout());
        btns.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton close = new JButton(Settings.BTN_CLOSE_ABOUT);
        close.setPreferredSize(new Dimension(Settings.ABOUT_CLOSE_BTN_WIDTH, Settings.ABOUT_CLOSE_BTN_HEIGHT));
        close.setFont(new Font("Segoe UI", Font.BOLD, 14));
        close.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        btns.add(close);

        main.add(top, BorderLayout.NORTH);
        main.add(team, BorderLayout.CENTER);
        main.add(btns, BorderLayout.SOUTH);

        dialog.add(main);
        dialog.setVisible(true);
    }
    //สร้างหน้าmemberขึ้นมา
    private static JPanel makeMember(String img, String name, String id, String job) {
        JPanel box = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();//
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//แต่งความเนียน

                // วาดพื้นหลังโปร่งใส
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // วาดเส้นขอบ
                g2.setColor(new Color(200, 200, 200, 100));
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

                g2.dispose();
            }
        };
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(new EmptyBorder(30, 25, 30, 25));
        box.setOpaque(false);

        JLabel pic = makePic(img);
        if (pic != null) {
            pic.setAlignmentX(Component.CENTER_ALIGNMENT);
            box.add(pic);
            box.add(Box.createVerticalStrut(20));
        }

        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("Student ID: " + id, SwingConstants.CENTER);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel jobLabel = new JLabel("Role: " + job, SwingConstants.CENTER);
        jobLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jobLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(nameLabel);
        box.add(Box.createVerticalStrut(10));
        box.add(idLabel);
        box.add(Box.createVerticalStrut(8));
        box.add(jobLabel);

        return box;
    }

    private static JLabel makePic(String path) {
        try {
            ImageIcon orig = new ImageIcon(path);
            Image scaled = orig.getImage().getScaledInstance(Settings.ABOUT_PIC_SIZE, Settings.ABOUT_PIC_SIZE,
                    Image.SCALE_SMOOTH);

            JLabel pic = new JLabel();
            pic.setIcon(new ImageIcon(scaled));
            pic.setHorizontalAlignment(SwingConstants.CENTER);
            return pic;
        } catch (Exception e) {
            // ถ้าไม่มีรูป ให้แสดงไอคอนเริ่มต้น
            JLabel def = new JLabel("👤", SwingConstants.CENTER);
            def.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            def.setPreferredSize(new Dimension(Settings.ABOUT_PIC_SIZE, Settings.ABOUT_PIC_SIZE));
            return def;
        }
    }

}
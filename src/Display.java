import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// คลาสไว้แสดง แจ้งเตือน และ about 
public class Display {
    public static void showMessage(Component parent, String title, String msg, int type) {
        JOptionPane.showMessageDialog(parent, msg, title, type);
    }

    public static void showAbout(Component parent) {
        boolean isDarkTheme = UIManager.getLookAndFeel().getName().contains("Monokai");
        
        JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "About " + Settings.APP_TITLE,
                true);
        dlg.setSize(850, 550);
        dlg.setLocationRelativeTo(null);
        dlg.setUndecorated(true);
        dlg.setResizable(false);

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel(Settings.ABOUT_TITLE, SwingConstants.CENTER);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_TITLE));
        
        if (isDarkTheme) {
            title.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            title.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        JLabel sub = new JLabel(Settings.ABOUT_DISTRIBUTION, SwingConstants.CENTER);
        sub.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 14));
        
        if (isDarkTheme) {
            sub.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            sub.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(title);
        top.add(Box.createVerticalStrut(8));
        top.add(sub);
        top.add(Box.createVerticalStrut(25));

        JPanel team = new JPanel(new GridLayout(1, 3, 25, 0));

        team.add(makePerson(Settings.IMG1, Settings.MEMBER1, Settings.ID1, Settings.JOB1));
        team.add(makePerson(Settings.IMG2, Settings.MEMBER2, Settings.ID2, Settings.JOB2));
        team.add(makePerson(Settings.IMG3, Settings.MEMBER3, Settings.ID3, Settings.JOB3));

        JPanel btns = new JPanel(new FlowLayout());
        btns.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton close = new JButton(Settings.BTN_CLOSE_ABOUT);
        close.setPreferredSize(new Dimension(Settings.ABOUT_CLOSE_BTN_WIDTH, Settings.ABOUT_CLOSE_BTN_HEIGHT));
        close.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.dispose();
            }
        });
        btns.add(close);

        main.add(top, BorderLayout.NORTH);
        main.add(team, BorderLayout.CENTER);
        main.add(btns, BorderLayout.SOUTH);

        dlg.add(main);
        dlg.setVisible(true);
    }

    private static JPanel makePerson(String img, String name, String id, String job) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Settings.COLOR_GRAY, 2, true),
                new EmptyBorder(30, 25, 30, 25)));
        box.setOpaque(false);

        JLabel pic = getPic(img);
        if (pic != null) {
            pic.setAlignmentX(Component.CENTER_ALIGNMENT);
            box.add(pic);
            box.add(Box.createVerticalStrut(20));
        }

        boolean isDarkTheme = UIManager.getLookAndFeel().getName().contains("Monokai");
        
        JLabel nameLbl = new JLabel(name, SwingConstants.CENTER);
        nameLbl.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 16));
        nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (isDarkTheme) {
            nameLbl.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            nameLbl.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        JLabel idLbl = new JLabel("Student ID: " + id, SwingConstants.CENTER);
        idLbl.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 14));
        idLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (isDarkTheme) {
            idLbl.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            idLbl.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }

        JLabel jobLbl = new JLabel("Role: " + job, SwingConstants.CENTER);
        jobLbl.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 14));
        jobLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (isDarkTheme) {
            jobLbl.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            jobLbl.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }

        box.add(nameLbl);
        box.add(Box.createVerticalStrut(10));
        box.add(idLbl);
        box.add(Box.createVerticalStrut(8));
        box.add(jobLbl);

        return box;
    }

    // ทำรูปโปรไฟล์
    private static JLabel getPic(String path) {
        try {
            ImageIcon icon = new ImageIcon(path);
            Image scaled = icon.getImage().getScaledInstance(Settings.ABOUT_PIC_SIZE, Settings.ABOUT_PIC_SIZE,
                    Image.SCALE_SMOOTH);
            JLabel lbl = new JLabel();
            lbl.setIcon(new ImageIcon(scaled));
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            return lbl;
        } catch (Exception e) {
            JLabel def = new JLabel("?", SwingConstants.CENTER);
            def.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_HUGE));
            def.setPreferredSize(new Dimension(Settings.ABOUT_PIC_SIZE, Settings.ABOUT_PIC_SIZE));
            return def;
        }
    }

}
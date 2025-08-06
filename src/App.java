import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.intellijthemes.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.BorderLayout;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


// App หลัก
public class App extends JFrame {
    //ประกาศตัวแปรต่างๆ
    private FileData data;
    private DisplayPanel panel;
    private JTextField input;
    private JLabel total;
    private JLabel status;
    private JButton loadBtn;
    private JButton clearBtn;
    private JPanel btnBox;
    private JLabel infoLabel; // JLabel สำหรับแสดงข้อมูล hover
    //แสดงหน้าแอพขึ้นมา
    public App() {
        data = new FileData();
        init();
        setVisible(true);
    }
    //เซ็ตค่าต่างๆ
    private void init() {
        setTheme();
        setIcon();
        makeWindow();
        makeUI();
        update();
    }
    //เซ็ตธีม
    private void setTheme() {
        changeTheme(Settings.CURRENT_THEME);
    }
    //เปลี่ยนะีมต่างๆภายในแอพโดยก่ารใช้ libraryปรับธีม
    private void changeTheme(String name) {
        try {
            switch (name) {
                case "ARC_ORANGE":
                    UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
                    break;
                case "DARK":
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
                case "DRACULA":
                    UIManager.setLookAndFeel(new FlatDraculaIJTheme());
                    break;
                case "MONOKAI":
                    UIManager.setLookAndFeel(new FlatMonokaiProIJTheme());
                    break;
                case "NORD":
                    UIManager.setLookAndFeel(new FlatNordIJTheme());
                    break;
                case "GRUVBOX_DARK":
                    UIManager.setLookAndFeel(new FlatGruvboxDarkHardIJTheme());
                    break;
                case "SOLARIZED_DARK":
                    UIManager.setLookAndFeel(new FlatSolarizedDarkIJTheme());
                    break;
                case "SOLARIZED_LIGHT":
                    UIManager.setLookAndFeel(new FlatSolarizedLightIJTheme());
                    break;
                case "XCODE_DARK":
                    UIManager.setLookAndFeel(new FlatXcodeDarkIJTheme());
                    break;
                case "VUESION":
                    UIManager.setLookAndFeel(new FlatVuesionIJTheme());
                    break;
                case "HIBERBEE_DARK":
                    UIManager.setLookAndFeel(new FlatHiberbeeDarkIJTheme());
                    break;
                case "HIGH_CONTRAST":
                    UIManager.setLookAndFeel(new FlatHighContrastIJTheme());
                    break;
                case "CARBON":
                    UIManager.setLookAndFeel(new FlatCarbonIJTheme());
                    break;
                case "COBALT_2":
                    UIManager.setLookAndFeel(new FlatCobalt2IJTheme());
                    break;
                case "CYAN_LIGHT":
                    UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
                    break;
                default:
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
            }
            updateUI();
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                updateUI();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //ตามชื่อเลยคือการอัปเดตUI
    private void updateUI() {
        SwingUtilities.updateComponentTreeUI(this);
        if (btnBox != null) {
            SwingUtilities.updateComponentTreeUI(btnBox);
        }
        repaint();
    }
    //ตั้งค่าหน้าต่างหลัก
    private void makeWindow() {
        setTitle(Settings.APP_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Settings.WIN_W, Settings.WIN_H);//ขนาดหน้าต่างหลักกว้าง × สูง
        setLocationRelativeTo(null);    //เช็ตให้หน้าต่างมาอยู่ตรงกลางหน้าจอ
        setResizable(true); // ปรับขนาดหน้าต่าง
        setUndecorated(Settings.WINDOW_MENU);//ซ่อนขอบหน้าต่าง
    }

    private void makeUI() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        makeTop(main);
        makeMiddle(main);

        add(main);
    }

    private void makeTop(JPanel parent) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel icon = getIcon();
        JLabel title = new JLabel(Settings.APP_TITLE);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_TITLE));

        JLabel ver = new JLabel(Settings.APP_VERSION);
        ver.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.add(title);
        titleBox.add(ver);

        if (icon != null) {
            left.add(icon);
            left.add(Box.createHorizontalStrut(10));
        }
        left.add(titleBox);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton theme = new JButton("Theme");
        theme.setPreferredSize(new Dimension(90, 35));
        theme.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        theme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showThemeMenu(theme);
            }
        });


        JButton about = new JButton(Settings.BTN_ABOUT);
        about.setPreferredSize(new Dimension(100, 35));
        about.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Display.showAbout(about);
            }
        });

        JButton exit = new JButton(Settings.BTN_EXIT);
        exit.setPreferredSize(new Dimension(80, 35));
        exit.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });

        right.add(theme);
        right.add(about);
        right.add(exit);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        parent.add(top, BorderLayout.NORTH);
    }

    private void makeMiddle(JPanel parent) {
        JPanel mid = new JPanel(new BorderLayout(15, 15));
        mid.setBorder(new EmptyBorder(15, 0, 15, 0));

        makeLeft(mid);
        makeRight(mid);

        parent.add(mid, BorderLayout.CENTER);
    }

    private void makeLeft(JPanel parent) {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(20, 20, 20, 20));
        left.setPreferredSize(new Dimension(320, 0));

        JLabel title = new JLabel("Control");
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(title);
        left.add(Box.createVerticalStrut(25));
        left.add(makeInput());
        left.add(Box.createVerticalStrut(25));
        left.add(makeColors());
        left.add(Box.createVerticalStrut(25));
        left.add(makeResult());
        left.add(Box.createVerticalGlue());

        parent.add(left, BorderLayout.WEST);
    }

    private JPanel makeInput() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(Settings.INPUT_LABEL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        input = new JTextField(String.valueOf(data.getFluidLevel()));
        input.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.INPUT_HEIGHT));
        input.setAlignmentX(Component.LEFT_ALIGNMENT);
        input.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_INPUT));

        JButton calc = new JButton(Settings.BTN_CALC);
        calc.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_BIG));
        calc.setAlignmentX(Component.LEFT_ALIGNMENT);
        calc.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_BUTTON));
        calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calc();
            }
        });

        loadBtn = new JButton(Settings.BTN_LOAD);
        loadBtn.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        loadBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadBtn.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        loadBtn.setBackground(null);
        loadBtn.setForeground(null);
        loadBtn.setBorder(null);

        clearBtn = new JButton(Settings.BTN_CLEAR);
        clearBtn.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        clearBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        clearBtn.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        clearBtn.setBackground(null);
        clearBtn.setForeground(null);
        clearBtn.setBorder(null);

        btnBox = new JPanel();
        btnBox.setLayout(new BoxLayout(btnBox, BoxLayout.Y_AXIS));
        btnBox.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        btnBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(label);
        box.add(Box.createVerticalStrut(8));
        box.add(input);
        box.add(Box.createVerticalStrut(15));
        box.add(calc);
        box.add(Box.createVerticalStrut(10));
        box.add(btnBox);

        return box;
    }

    private JPanel makeColors() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(Settings.LEGEND_TITLE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(title);
        box.add(Box.createVerticalStrut(10));
        box.add(makeColor(Settings.NO_GAS, Color.RED));
        box.add(Box.createVerticalStrut(5));
        box.add(makeColor(Settings.LOW_GAS, Color.YELLOW));
        box.add(Box.createVerticalStrut(5));
        box.add(makeColor(Settings.HIGH_GAS, Color.GREEN));

        return box;
    }

    private JPanel makeColor(String text, Color color) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setMaximumSize(new Dimension(Settings.BTN_WIDTH, 30));

        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(24, 24));
        box.setBackground(color);
        box.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel label = new JLabel(text);
        label.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_NORMAL));

        row.add(box);
        row.add(Box.createHorizontalStrut(12));
        row.add(label);

        return row;
    }

    private JPanel makeResult() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(Settings.RESULT_TITLE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        total = new JLabel(Settings.TOTAL_GAS);
        total.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        total.setAlignmentX(Component.LEFT_ALIGNMENT);

        status = new JLabel(Settings.STATUS_READY);
        status.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(title);
        box.add(Box.createVerticalStrut(10));
        box.add(total);
        box.add(Box.createVerticalStrut(8));
        box.add(status);

        return box;
    }

    private void makeRight(JPanel parent) {
        JPanel right = new JPanel(new BorderLayout(0, 10)); // เพิ่ม gap ระหว่าง components
        right.setBorder(new EmptyBorder(10, 20, 10, 20)); // เพิ่ม padding กลับมา

        JLabel title = new JLabel(Settings.GRID_TITLE);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // เพิ่ม padding ด้านล่างกลับมา

        // สร้าง panel เริ่มต้น
        panel = new FileUploader(data);
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!hasData()) {
                    load();
                }
            }
        });

        infoLabel = new JLabel("...");
        infoLabel.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JPanel gridPanel = new JPanel(new BorderLayout());
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gridPanel.add(panel, BorderLayout.CENTER);

        right.add(title, BorderLayout.NORTH);
        right.add(gridPanel, BorderLayout.CENTER);
        right.add(infoLabel, BorderLayout.SOUTH);

        parent.add(right, BorderLayout.CENTER);
    }

    private boolean hasData() {
        return data.getRows() > 0 && data.getCols() > 0;
    }

    private void load() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(Settings.SELECT_FILE_TITLE);
        fc.setFileFilter(new FileNameExtensionFilter(Settings.FILE_OF_TYPE, "txt"));
        fc.setCurrentDirectory(new File("src"));

        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (data.loadFromFile(file.getAbsolutePath())) {
                update();
                status.setText(Settings.STATUS_LOAD + file.getName());
            } else {
                status.setText(Settings.STATUS_FAIL);
                Display.showMessage(this, Settings.STATUS_ERROR, Settings.STATUS_CHECK, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void calc() {
        if (!hasData()) {
            status.setText("Please load file first");
            return;
        }

        try {
            double level = Double.parseDouble(input.getText());
            data.setFluidLevel(level);
            update();
            status.setText("Calculated with fluid level: " + level);
        } catch (NumberFormatException e) {
            status.setText("Invalid number format");
        }
    }

    private void clear() {
        data.clearData();
        input.setText(String.valueOf(Settings.DEFAULT_FLUID));
        update();
        updateBtn();
        status.setText("File cleared");
    }

    private void updateBtn() {
        btnBox.removeAll();

        if (hasData()) {
            clearBtn.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
            clearBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            clearBtn.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
            clearBtn.setBackground(null);
            clearBtn.setForeground(null);
            clearBtn.setBorder(null);
            clearBtn.updateUI();
            btnBox.add(clearBtn);
        } else {
            loadBtn.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
            loadBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            loadBtn.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
            loadBtn.setBackground(null);
            loadBtn.setForeground(null);
            loadBtn.setBorder(null);
            loadBtn.updateUI();
            btnBox.add(loadBtn);
        }

        // อัปเดตธีมของ container
        SwingUtilities.updateComponentTreeUI(btnBox);
        btnBox.revalidate();
        btnBox.repaint();
    }

    private void update() {
        double sum = data.getTotalVolume();
        total.setText(String.format(Settings.TOTAL_GAS, sum));

        // สลับ panel
        if (panel != null) {
            JPanel right = (JPanel) panel.getParent();
            JLabel info = null;

            // หา JLabel info จาก right panel
            for (Component comp : right.getComponents()) {
                if (comp instanceof JLabel && comp != right.getComponent(0)) { // ไม่ใช่ title
                    info = (JLabel) comp;
                    break;
                }
            }

            right.remove(panel);

            if (hasData()) {
                GridDisplay gridDisplay = new GridDisplay(data);
                gridDisplay.setInfoLabel(infoLabel);

                // สร้าง scroll pane ถ้า grid ใหญ่เกินไป
                Dimension gridSize = gridDisplay.getPreferredSize();
                if (gridSize.width > 800 || gridSize.height > 600) {
                    JScrollPane scrollPane = new JScrollPane(gridDisplay);
                    scrollPane.setPreferredSize(new Dimension(800, 600));

                    panel = new DisplayPanel(data) {
                        @Override
                        public void refresh() {
                            gridDisplay.refresh();
                        }
                    };
                    panel.setLayout(new BorderLayout());
                    panel.add(scrollPane, BorderLayout.CENTER);
                } else {
                    panel = gridDisplay;
                }
            } else {
                panel = new FileUploader(data);
                panel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        load();
                    }
                });
            }

            right.add(panel, BorderLayout.CENTER);
            right.revalidate();
            right.repaint();
        } else {
            if (panel != null) {
                panel.refresh();
            }
        }

        updateBtn();
    }

    private void close() {
        int result = JOptionPane.showConfirmDialog(this, Settings.EXIT_MSG, Settings.EXIT_TITLE,
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void setIcon() {
        try {
            ImageIcon icon = new ImageIcon(Settings.ICON_APP);
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // ไม่มีไอคอน
        }
    }

    private JLabel getIcon() {
        try {
            ImageIcon icon = new ImageIcon(Settings.APP_ICON_PATH);
            Image scaled = icon.getImage().getScaledInstance(Settings.ICON_SIZE_SMALL, Settings.ICON_SIZE_SMALL,
                    Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaled));
        } catch (Exception e) {
            return null;
        }
    }

    private void showThemeMenu(JButton themeBtn) {
        JPopupMenu themeMenu = new JPopupMenu();

        String[][] themes = {
                { "LIGHT", "Light Theme" },
                { "DARK", "Dark Theme" },
                { "ARC_ORANGE", "Arc Orange" },
                { "DRACULA", "Dracula" },
                { "MONOKAI", "Monokai Pro" },
                { "NORD", "Nord" },
                { "GRUVBOX_DARK", "Gruvbox Dark" },
                { "SOLARIZED_DARK", "Solarized Dark" },
                { "SOLARIZED_LIGHT", "Solarized Light" },
                { "XCODE_DARK", "Xcode Dark" },
                { "VUESION", "Vuesion" },
                { "HIBERBEE_DARK", "Hiberbee Dark" },
                { "HIGH_CONTRAST", "High Contrast" },
                { "CARBON", "Carbon" },
                { "COBALT_2", "Cobalt 2" },
                { "CYAN_LIGHT", "Cyan Light" }
        };

        for (String[] theme : themes) {
            JMenuItem item = new JMenuItem(theme[1]);
            item.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_NORMAL));

            if (theme[0].equals(Settings.CURRENT_THEME)) {
                item.setText("+" + theme[1]);
                item.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
            }

            final String themeCode = theme[0];
            final String themeName = theme[1];

            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Settings.CURRENT_THEME = themeCode;
                    changeTheme(themeCode);
                    updateBtn();
                    status.setText("Theme: " + themeName);
                }
            });
                    themeMenu.add(item);
        }

        themeMenu.addSeparator();

        JMenuItem reset = new JMenuItem("Reset to Default");
        reset.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_NORMAL));
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.CURRENT_THEME = "ARC_ORANGE";
                changeTheme("ARC_ORANGE");
                updateBtn();
                status.setText("Reset to default theme");
            }
        });
                themeMenu.add(reset);

        themeMenu.show(themeBtn, 0, themeBtn.getHeight());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App();
        });
    }
}
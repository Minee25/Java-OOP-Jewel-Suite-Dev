import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.io.*;

// คลาสแม่ไว้รันโปรแกรมหลัก สืบทอดม JFrame
public class App extends JFrame {

    private FileData data;
    private JPanel panel;
    private GridDisplay gridPanel;
    private FileUploader filePanel;
    private JTextField input;
    private JLabel total;
    private JLabel status;
    private JButton load;
    private JButton clear;
    private JPanel btns;
    private JLabel info;
    private JLabel statsLabel;
    private String settingsFile = Settings.DB;
    private JButton sum;
    private boolean isDark = false;
    private JCheckBox Check;

    private JLabel appTitleLabel;
    private JLabel appVersionLabel;
    private JLabel controlTitleLabel;
    private JLabel inputLabel;
    private JLabel legendTitleLabel;
    private JLabel resultTitleLabel;
    private JLabel gridTitleLabel;

    private JLabel noGasLabel;
    private JLabel lowGasLabel;
    private JLabel highGasLabel;

    public App() {
        showLoadingScreen();
        data = new FileData();
        setup();
        setVisible(true);
        setExtendedState(Settings.FULL_S_W); // ขนาดจอตอนรันเสร็จ
    }

    private void setup() {
        icon();
        window();
        ui();
        theme();
        update();
    }

    private void theme() {
        String[] loaded = loadSettings();
        String savedTheme = loaded[0];

        setLaf(savedTheme);
        isDark = savedTheme.equals("MONOKAI");

        if (isDark) {
            updateTextColorsForDarkTheme();
        } else {
            updateTextColorsForLightTheme();
        }
    }

    // เช็ตธีม จากlib UIManager.setLookAndFeel
    // repaintAll(); สร้างใหม่ทั้งหมดเมื่อเช็ตธีมเสร็จ
    // setForeground ไว้เช็ตสีตามธีม
    private void setLaf(String name) {
        try {
            if ("MONOKAI".equals(name)) {
                UIManager.setLookAndFeel(new FlatMonokaiProIJTheme());
            } else {
                UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
            }
            repaintAll();
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
                repaintAll();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void repaintAll() {
        SwingUtilities.updateComponentTreeUI(this);
        if (btns != null) {
            SwingUtilities.updateComponentTreeUI(btns);
        }

        if (isDark) {
            updateTextColorsForDarkTheme();
        } else {
            updateTextColorsForLightTheme();
        }

        repaint();
    }
    //เอาไว้เซ็ตหน้าวินโดวหน้าหลัก
    private void window() {
        setTitle(Settings.APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Settings.WIN_W, Settings.WIN_H);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(Settings.WINDOW_MENU);
    }
    //สร้างโครงของui
    private void ui() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        top(main);
        middle(main);

        add(main);
    }
    //เซ็ตค่าต่างๆของพาแนลบนๆ
    private void top(JPanel p) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel icon = getIcon();
        appTitleLabel = new JLabel(Settings.APP_TITLE);
        appTitleLabel.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_TITLE));
        if (isDark) {
            appTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            appTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        appVersionLabel = new JLabel(Settings.APP_VERSION);
        appVersionLabel.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        if (isDark) {
            appVersionLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            appVersionLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }

        JPanel titles = new JPanel();
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.add(appTitleLabel);
        titles.add(appVersionLabel);

        if (icon != null) {
            left.add(icon);
            left.add(Box.createHorizontalStrut(10));
        }
        left.add(titles);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton theme = new JButton(Settings.THEME_DARK);
        theme.setPreferredSize(new Dimension(90, 35));
        theme.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        theme.addActionListener(new Actions.ThemeAction(this, theme));

        JButton about = new JButton(Settings.BTN_ABOUT);
        about.setPreferredSize(new Dimension(100, 35));
        about.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        about.addActionListener(new Actions.AboutAction(about));

        JButton exit = new JButton(Settings.BTN_EXIT);
        exit.setPreferredSize(new Dimension(80, 35));
        exit.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        exit.addActionListener(new Actions.ExitAction(this));

        right.add(theme);
        right.add(about);
        right.add(exit);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        p.add(top, BorderLayout.NORTH);
    }
    //เซ็ตค่าต่างๆของพาแนลกลางๆ
    private void middle(JPanel p) {
        JPanel mid = new JPanel(new BorderLayout(15, 15));
        mid.setBorder(new EmptyBorder(15, 0, 15, 0));

        left(mid);
        right(mid);

        p.add(mid, BorderLayout.CENTER);
    }
    //ทำงานในส่วนpanelฝั่งด้านขวา
    private void left(JPanel p) {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(20, 20, 20, 20));
        left.setPreferredSize(new Dimension(320, 0));

        controlTitleLabel = new JLabel(Settings.CONTROL_TITLE);
        controlTitleLabel.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_LARGE));
        controlTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (isDark) {
            controlTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            controlTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        left.add(controlTitleLabel);
        left.add(Box.createVerticalStrut(25));
        left.add(inputPanel());
        left.add(Box.createVerticalStrut(25));
        left.add(colorPanel());
        left.add(Box.createVerticalStrut(25));
        left.add(resultPanel());
        left.add(Box.createVerticalGlue());

        p.add(left, BorderLayout.WEST);
    }
    //
    private JPanel inputPanel() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        inputLabel = new JLabel(Settings.INPUT_LABEL);
        inputLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (isDark) {
            inputLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            inputLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        JPanel inp = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        inp.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.INPUT_HEIGHT));
        inp.setAlignmentX(Component.LEFT_ALIGNMENT);

        input = new JTextField(String.valueOf(data.getFluidLevel()), 10);
        input.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_INPUT));
        inp.add(input);

        JButton calc = new JButton(Settings.BTN_CALC);
        calc.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_BIG));
        calc.setAlignmentX(Component.LEFT_ALIGNMENT);
        calc.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_BUTTON));
        calc.addActionListener(new Actions.CalculateAction(this));

        btns = new JPanel();
        btns.setLayout(new BoxLayout(btns, BoxLayout.Y_AXIS));
        btns.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        btns.setAlignmentX(Component.LEFT_ALIGNMENT);

        load = new JButton(Settings.BTN_LOAD);
        load.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        load.setAlignmentX(Component.LEFT_ALIGNMENT);
        load.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        load.addActionListener(new Actions.LoadAction(this));
        btns.add(load);
        btns.add(Box.createVerticalStrut(5));

        sum = new JButton(Settings.BTN_SUMMARY);
        sum.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        sum.setAlignmentX(Component.LEFT_ALIGNMENT);
        sum.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        sum.addActionListener(new Actions.SummaryAction(this));
        btns.add(sum);
        btns.add(Box.createVerticalStrut(5));

        clear = new JButton(Settings.BTN_CLEAR);
        clear.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        clear.setAlignmentX(Component.LEFT_ALIGNMENT);
        clear.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        clear.addActionListener(new Actions.ClearAction(this));
        btns.add(clear);

        box.add(inputLabel);
        box.add(Box.createVerticalStrut(8));
        box.add(inp);
        box.add(Box.createVerticalStrut(15));
        box.add(calc);
        box.add(Box.createVerticalStrut(10));
        box.add(btns);

        return box;
    }

    private JPanel colorPanel() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        legendTitleLabel = new JLabel(Settings.LEGEND_TITLE);
        legendTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (isDark) {
            legendTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            legendTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        box.add(legendTitleLabel);
        box.add(Box.createVerticalStrut(10));

        // Create color items and store label references
        JPanel noGasItem = createColorItem(Settings.NO_GAS, Settings.COLOR_NO_GAS);
        noGasLabel = getColorItemLabel(noGasItem);
        box.add(noGasItem);

        box.add(Box.createVerticalStrut(5));

        JPanel lowGasItem = createColorItem(Settings.LOW_GAS, Settings.COLOR_LOW_GAS);
        lowGasLabel = getColorItemLabel(lowGasItem);
        box.add(lowGasItem);

        box.add(Box.createVerticalStrut(5));

        JPanel highGasItem = createColorItem(Settings.HIGH_GAS, Settings.COLOR_HIGH_GAS);
        highGasLabel = getColorItemLabel(highGasItem);
        box.add(highGasItem);

        return box;
    }

    private JPanel createColorItem(String txt, Color c) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setMaximumSize(new Dimension(Settings.BTN_WIDTH, 30));

        JPanel colorBox = new JPanel();
        colorBox.setPreferredSize(new Dimension(24, 24));
        colorBox.setBackground(c);
        colorBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel lbl = new JLabel(txt);
        lbl.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));

        if (isDark) {
            lbl.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            lbl.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }

        row.add(colorBox);
        row.add(Box.createHorizontalStrut(12));
        row.add(lbl);

        return row;
    }

    private JLabel getColorItemLabel(JPanel colorItem) {
        // Get the label from the color item panel (it's the 3rd component)
        Component[] components = colorItem.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel) {
                return (JLabel) comp;
            }
        }
        return null;
    }

    private JPanel resultPanel() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        resultTitleLabel = new JLabel(Settings.RESULT_TITLE);
        resultTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (isDark) {
            resultTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            resultTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        total = new JLabel(Settings.TOTAL_GAS);
        total.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        total.setAlignmentX(Component.LEFT_ALIGNMENT);

        status = new JLabel(Settings.STATUS_READY);
        status.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (isDark) {
            status.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            status.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        statsLabel = new JLabel("Statistics: -");
        statsLabel.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        statsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (isDark) {
            statsLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            statsLabel.setForeground(Settings.LIGHT_TEXT_MUTED);
        }

        box.add(resultTitleLabel);
        box.add(Box.createVerticalStrut(10));
        box.add(total);
        box.add(Box.createVerticalStrut(8));
        box.add(statsLabel);
        box.add(Box.createVerticalStrut(8));
        box.add(status);

        return box;
    }

    private void right(JPanel p) {
        JPanel right = new JPanel(new BorderLayout(0, 10));
        right.setBorder(new EmptyBorder(10, 20, 10, 20));

        gridTitleLabel = new JLabel(Settings.GRID_TITLE);
        gridTitleLabel.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 18));
        gridTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gridTitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        if (isDark) {
            gridTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        } else {
            gridTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        filePanel = new FileUploader(data);
        panel = filePanel;
        panel.addMouseListener(new Actions.ClickLoadAction(this));

        info = new JLabel("");
        info.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        if (isDark) {
            info.setForeground(Settings.DARK_TEXT_SECONDARY);
        } else {
            info.setForeground(Settings.LIGHT_TEXT_MUTED);
        }

        JPanel grid = new JPanel(new BorderLayout());
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        grid.add(panel, BorderLayout.CENTER);

        right.add(gridTitleLabel, BorderLayout.NORTH);
        right.add(grid, BorderLayout.CENTER);
        right.add(info, BorderLayout.SOUTH);

        p.add(right, BorderLayout.CENTER);
    }

    public boolean hasData() {
        return data.getRowCount() > 0 && data.getColumnCount() > 0;
    }

    public void loadFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(Settings.SELECT_FILE_TITLE);
        fc.setFileFilter(new FileNameExtensionFilter(Settings.FILE_OF_TYPE, "txt"));
        fc.setCurrentDirectory(new File("src/data"));

        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (data.loadFromFile(file.getAbsolutePath())) {
                update();
                int zeroCount = data.countZeroCells();
                if (zeroCount > 0) {
                    status.setText(Settings.STATUS_LOAD + file.getName() + " (Warn: " + zeroCount + " invalid)");
                } else {
                    status.setText(Settings.STATUS_LOAD + file.getName());
                }
            } else {
                status.setText(Settings.STATUS_FAIL);
                Display.showMessage(this, Settings.STATUS_ERROR, Settings.STATUS_CHECK, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void calculate() {
        if (!hasData()) {
            status.setText(Settings.LOAD_FIRST_MSG);
            Display.showMessage(this, Settings.STATUS_ERROR, Settings.LOAD_FIRST_MSG, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double inputValue = Double.parseDouble(input.getText());
            data.setFluidLevel(inputValue);
            status.setText(Settings.CALC_WITH_LEVEL + inputValue + " M");
            update();
        } catch (NumberFormatException e) {
            status.setText(Settings.INVALID_NUMBER_MSG);
        }
    }

    public void clearData() {
        int result = JOptionPane.showConfirmDialog(this, "Are you Sure Clear file?", "Clear File",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            data.clearAllData();
            input.setText(String.valueOf(Settings.DEFAULT_FLUID));
            update();
            updateBtns();
            status.setText(Settings.FILE_CLEARED_MSG);
            info.setText("");
        }

    }

    private void updateBtns() {
        btns.removeAll();

        if (hasData()) {
            clear.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
            clear.setAlignmentX(Component.LEFT_ALIGNMENT);
            clear.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
            clear.updateUI();
            btns.add(clear);

            btns.add(Box.createVerticalStrut(5));

            sum.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
            sum.setAlignmentX(Component.LEFT_ALIGNMENT);
            sum.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
            sum.updateUI();
            btns.add(sum);
        } else {
            load.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
            load.setAlignmentX(Component.LEFT_ALIGNMENT);
            load.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
            load.updateUI();
            btns.add(load);
        }

        SwingUtilities.updateComponentTreeUI(btns);
        btns.revalidate();
        btns.repaint();
    }

    private void update() {
        double vol = data.calculateTotalGasVolume();
        DecimalFormat fmt = new DecimalFormat("#,##0.00");

        String totalText = String.format(Settings.TOTAL_GAS, fmt.format(vol)) + " " + Settings.UNIT_M3;
        total.setText(totalText);
        showStats();

        if (panel != null) {
            JPanel right = (JPanel) panel.getParent();
            right.remove(panel);

            if (hasData()) {
                gridPanel = new GridDisplay(data);
                gridPanel.setInfoLabel(info);

                Dimension size = gridPanel.getPreferredSize();
                if (size.width > 800 || size.height > 600) {
                    JScrollPane scroll = new JScrollPane(gridPanel);
                    scroll.setPreferredSize(new Dimension(800, 600));
                    panel = new JPanel(new BorderLayout());
                    panel.add(scroll, BorderLayout.CENTER);
                } else {
                    panel = gridPanel;
                }
                filePanel = null;
            } else {
                filePanel = new FileUploader(data);
                panel = filePanel;
                panel.addMouseListener(new Actions.ClickLoadAction(this));
                gridPanel = null;
            }
            right.add(panel, BorderLayout.CENTER);
            right.revalidate();
            right.repaint();
        } else {
            if (gridPanel != null) {
                gridPanel.refresh();
            } else if (filePanel != null) {
                filePanel.refresh();
            }
        }
        updateBtns();
    }

    // ? เดะมาแก้ต่อ
    private void showStats() {
        if (!hasData())
            return;

        int noGas = 0, lowGas = 0, highGas = 0;
        for (int r = 0; r < data.getRowCount(); r++) {
            for (int c = 0; c < data.getColumnCount(); c++) {
                int level = data.getGasLevel(r, c);
                if (level == 0)
                    noGas++;
                else if (level == 1)
                    lowGas++;
                else
                    highGas++;
            }
        }

        int totalCells = data.getRowCount() * data.getColumnCount();
        int gasArea = lowGas + highGas;
        double gasPct = (gasArea * 100.0) / totalCells;

        String statsText = Settings.STATS_PREFIX + gasArea + Settings.SLASH + totalCells + Settings.STATS_CELLS
                + Settings.OPEN_BRACKET + String.format("%.1f", gasPct) + Settings.PERCENT_SIGN
                + Settings.CLOSE_BRACKET;

        if (statsLabel != null) {
            statsLabel.setText(statsText);
        }
    }

    public void exitApp() {
        int result = JOptionPane.showConfirmDialog(this, Settings.EXIT_MSG, Settings.EXIT_TITLE,
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    // ไอไนท์ทำ แต่มันไม่ขึ้นในmac วินโดวน่าจะขึ้นปกติ
    private void icon() {
        try {
            ImageIcon icon = new ImageIcon(Settings.ICON_APP);
            setIconImage(icon.getImage());
        } catch (Exception e) {
        }
    }

    // โลโก้โปรแกรม Image.SCALE_SMOOTH ไม่บีบขนาดมั้งนะ
    private JLabel getIcon() {
        try {
            ImageIcon appIcon = new ImageIcon(Settings.APP_ICON_PATH);
            Image scaledIcon = appIcon.getImage().getScaledInstance(Settings.ICON_SIZE_SMALL, Settings.ICON_SIZE_SMALL,
                    Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledIcon));
        } catch (Exception e) {
            return null;
        }
    }

    public void changeTheme(JButton btn) {
        isDark = !isDark;
        if (isDark) {
            setLaf("MONOKAI");
            updateTextColorsForDarkTheme();
            status.setText(Settings.THEME_STATUS_DARK);
            btn.setText(Settings.THEME_LIGHT);
        } else {
            setLaf("ARC_ORANGE");
            updateTextColorsForLightTheme();
            status.setText(Settings.THEME_STATUS_LIGHT);
            btn.setText(Settings.THEME_DARK);
        }
        saveDB();
    }

    private void updateTextColorsForDarkTheme() {
        if (statsLabel != null) {
            statsLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        }
        if (info != null) {
            info.setForeground(Settings.DARK_TEXT_SECONDARY);
        }
        if (status != null) {
            status.setForeground(Settings.DARK_TEXT_PRIMARY);
        }
        if (total != null) {
            total.setForeground(Settings.DARK_TEXT_PRIMARY);
        }

        if (appTitleLabel != null) {
            appTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        }
        if (appVersionLabel != null) {
            appVersionLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        }
        if (controlTitleLabel != null) {
            controlTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        }
        if (inputLabel != null) {
            inputLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        }
        if (legendTitleLabel != null) {
            legendTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        }
        if (resultTitleLabel != null) {
            resultTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        }
        if (gridTitleLabel != null) {
            gridTitleLabel.setForeground(Settings.DARK_TEXT_PRIMARY);
        }

        if (noGasLabel != null) {
            noGasLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        }
        if (lowGasLabel != null) {
            lowGasLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        }
        if (highGasLabel != null) {
            highGasLabel.setForeground(Settings.DARK_TEXT_SECONDARY);
        }

        if (filePanel != null) {
            filePanel.refresh();
        }
    }

    private void updateTextColorsForLightTheme() {
        if (statsLabel != null) {
            statsLabel.setForeground(Settings.LIGHT_TEXT_MUTED);
        }
        if (info != null) {
            info.setForeground(Settings.LIGHT_TEXT_MUTED);
        }
        if (status != null) {
            status.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        if (total != null) {
            total.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        if (appTitleLabel != null) {
            appTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        if (appVersionLabel != null) {
            appVersionLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }
        if (controlTitleLabel != null) {
            controlTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        if (inputLabel != null) {
            inputLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        if (legendTitleLabel != null) {
            legendTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        if (resultTitleLabel != null) {
            resultTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }
        if (gridTitleLabel != null) {
            gridTitleLabel.setForeground(Settings.LIGHT_TEXT_PRIMARY);
        }

        if (noGasLabel != null) {
            noGasLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }
        if (lowGasLabel != null) {
            lowGasLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }
        if (highGasLabel != null) {
            highGasLabel.setForeground(Settings.LIGHT_TEXT_SECONDARY);
        }
    }

    // หน้าโหลด
    private void showLoadingScreen() {
        JDialog loading = new JDialog();
        loading.setTitle("Loading...");
        loading.setSize(300, 200);
        loading.setUndecorated(true);
        loading.setLocationRelativeTo(null);
        loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel icon = getIcon();
        JLabel text = new JLabel("Loading Gas Distribution System...");
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel(Settings.APP_TITLE);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_TITLE));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (icon != null) {
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(icon);
            panel.add(Box.createVerticalStrut(10));
        }

        panel.add(title);
        panel.add(Box.createVerticalStrut(10));
        panel.add(text);

        loading.add(panel);
        loading.setVisible(true);

        try {
            Thread.sleep(2000); // waitรอ 2วิ
            loading.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // ====== db ====== //
    // บันทึกฐานข้อมูล และดึงค่าจากฐานข้อมมูล ini
    private void saveSettings(String theme) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(settingsFile))) {
            bw.write("THEME=" + theme);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] loadSettings() {
        String theme = Settings.CURRENT_THEME;

        try (BufferedReader br = new BufferedReader(new FileReader(settingsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("THEME=")) {
                    theme = line.substring(6).trim();
                }
            }
        } catch (IOException e) {

        }

        return new String[] { theme };
    }

    private void saveDB() {
        String themeToSave;
        if (isDark) {
            themeToSave = "MONOKAI";
        } else {
            themeToSave = "ARC_ORANGE";
        }

        saveSettings(themeToSave);
    }

    public void showSummary() {
        if (data == null || data.getRows() <= 0 || data.getCols() <= 0) {
            JOptionPane.showMessageDialog(this, Settings.LOAD_FIRST_MSG);
            return;
        }

        int rows = data.getRows();
        int cols = data.getCols();
        int total = rows * cols;

        int[] n = new int[3];
        double[] vol = new double[3];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int lv = data.getLevel(r, c);
                if (lv < 0 || lv > 2)
                    lv = 0;
                n[lv]++;
                vol[lv] += data.getVolume(r, c);
            }
        }

        new SummaryView(this, n, vol, total).setVisible(true);
    }



}
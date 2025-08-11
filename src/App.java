
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

//คลาสแม่หลัก หลักสืบทอดจาก JFrame
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
    private JCheckBox km;
    private boolean isKm;
    private JLabel unitLabel;
    private JLabel statsLabel;
    // แสดงหน้าแอพขึ้นมา

    public App() {
        showLoadingScreen();
        data = new FileData();
        setup();
        setVisible(true);
        setExtendedState(Settings.FULL_S_W);
    }

    private void setup() {
        theme();
        icon();
        window();
        ui();
        update();
    }

    private void theme() {
        setLaf(Settings.CURRENT_THEME);
    }

    // UIManager.setLookAndFeel ให้lib จัดการgui 
    // repaintAll สร้างใหม่ทั้งหมด ใช้กับเปลี่บนธีม
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
        repaint();
    }

    private void window() {
        setTitle(Settings.APP_TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Settings.WIN_W, Settings.WIN_H);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(Settings.WINDOW_MENU);
    }

    private void ui() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        top(main);
        middle(main);

        add(main);
    }

    private void top(JPanel p) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel icon = getIcon();
        JLabel title = new JLabel(Settings.APP_TITLE);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_TITLE));

        JLabel ver = new JLabel(Settings.APP_VERSION);
        ver.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));

        JPanel titles = new JPanel();
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        titles.add(title);
        titles.add(ver);

        if (icon != null) {
            left.add(icon);
            left.add(Box.createHorizontalStrut(10));
        }
        left.add(titles);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton theme = new JButton(Settings.THEME_DARK);
        theme.setPreferredSize(new Dimension(90, 35));
        theme.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        theme.addActionListener(new ThemeButtonListener(theme));

        JButton about = new JButton(Settings.BTN_ABOUT);
        about.setPreferredSize(new Dimension(100, 35));
        about.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        about.addActionListener(new AboutButtonListener(about));

        JButton exit = new JButton(Settings.BTN_EXIT);
        exit.setPreferredSize(new Dimension(80, 35));
        exit.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_NORMAL));
        exit.addActionListener(new ExitButtonListener());

        right.add(theme);
        right.add(about);
        right.add(exit);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        p.add(top, BorderLayout.NORTH);
    }

    private void middle(JPanel p) {
        JPanel mid = new JPanel(new BorderLayout(15, 15));
        mid.setBorder(new EmptyBorder(15, 0, 15, 0));

        left(mid);
        right(mid);

        p.add(mid, BorderLayout.CENTER);
    }

    private void left(JPanel p) {
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(20, 20, 20, 20));
        left.setPreferredSize(new Dimension(320, 0));

        JLabel title = new JLabel(Settings.CONTROL_TITLE);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_LARGE));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(title);
        left.add(Box.createVerticalStrut(25));
        left.add(inputPanel());
        left.add(Box.createVerticalStrut(25));
        left.add(colorPanel());
        left.add(Box.createVerticalStrut(25));
        left.add(resultPanel());
        left.add(Box.createVerticalGlue());

        p.add(left, BorderLayout.WEST);
    }

    private JPanel inputPanel() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel(Settings.INPUT_LABEL);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel inp = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        inp.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.INPUT_HEIGHT));
        inp.setAlignmentX(Component.LEFT_ALIGNMENT);

        input = new JTextField(String.valueOf(data.getFluidLevel()), 10);
        input.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_INPUT));

        inp.add(input);

        JPanel unit = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        unit.setMaximumSize(new Dimension(Settings.BTN_WIDTH, 40));
        unit.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel unitLbl = new JLabel(Settings.UNIT_LABEL);
        unitLbl.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_NORMAL));

        km = new JCheckBox(Settings.UNIT_KM_TEXT);
        km.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, Settings.FONT_SIZE_NORMAL));
        km.addActionListener(new UnitCheckboxListener());

        unit.add(unitLbl);
        unit.add(km);

        JButton calc = new JButton(Settings.BTN_CALC);
        calc.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_BIG));
        calc.setAlignmentX(Component.LEFT_ALIGNMENT);
        calc.setFont(new Font(Settings.FONT_NAME, Font.BOLD, Settings.FONT_SIZE_BUTTON));
        calc.addActionListener(new CalculateButtonListener());

        load = new JButton(Settings.BTN_LOAD);
        load.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        load.setAlignmentX(Component.LEFT_ALIGNMENT);
        load.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        load.addActionListener(new LoadButtonListener());

        clear = new JButton(Settings.BTN_CLEAR);
        clear.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        clear.setAlignmentX(Component.LEFT_ALIGNMENT);
        clear.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        clear.addActionListener(new ClearButtonListener());

        btns = new JPanel();
        btns.setLayout(new BoxLayout(btns, BoxLayout.Y_AXIS));
        btns.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
        btns.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(lbl);
        box.add(Box.createVerticalStrut(8));
        box.add(inp);
        box.add(Box.createVerticalStrut(5));
        box.add(unit);
        box.add(Box.createVerticalStrut(15));
        box.add(calc);
        box.add(Box.createVerticalStrut(10));
        box.add(btns);

        return box;
    }

    private void changeUnit(boolean km) {
        try {
            double val = Double.parseDouble(input.getText());

            if (isKm != km) {
                if (km) {
                    val = val / 1000.0;
                } else {
                    val = val * 1000.0;
                }
                input.setText(String.format("%.3f", val));
            }

            isKm = km;
            String txt;
            if (isKm) {
                txt = Settings.UNIT_KM;
            } else {
                txt = Settings.UNIT_METER;
            }
            showUnit(txt);

        } catch (NumberFormatException e) {
            status.setText(Settings.INVALID_INPUT_MSG);
        }
    }

    private JPanel colorPanel() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(Settings.LEGEND_TITLE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(title);
        box.add(Box.createVerticalStrut(10));
        box.add(colorItem(Settings.NO_GAS,Settings.COLOR_NO_GAS));
        box.add(Box.createVerticalStrut(5));
        box.add(colorItem(Settings.LOW_GAS, Settings.COLOR_LOW_GAS));
        box.add(Box.createVerticalStrut(5));
        box.add(colorItem(Settings.HIGH_GAS, Settings.COLOR_HIGH_GAS));

        return box;
    }

    private JPanel colorItem(String txt, Color c) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setMaximumSize(new Dimension(Settings.BTN_WIDTH, 30));

        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(24, 24));
        box.setBackground(c);
        box.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JLabel lbl = new JLabel(txt);
        lbl.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        row.add(box);
        row.add(Box.createHorizontalStrut(12));
        row.add(lbl);

        return row;
    }

    private JPanel resultPanel() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(Settings.RESULT_TITLE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        total = new JLabel(Settings.TOTAL_GAS);
        total.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
        total.setAlignmentX(Component.LEFT_ALIGNMENT);

        status = new JLabel(Settings.STATUS_READY);
        status.setAlignmentX(Component.LEFT_ALIGNMENT);

        unitLabel = new JLabel(Settings.UNIT_METER);
        unitLabel.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        unitLabel.setForeground(Settings.COLOR_GRAY);
        unitLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsLabel = new JLabel("Statistics: -");
        statsLabel.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        statsLabel.setForeground(Settings.COLOR_GRAY);
        statsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(title);
        box.add(Box.createVerticalStrut(10));
        box.add(total);
        box.add(Box.createVerticalStrut(8));
        box.add(unitLabel);
        box.add(Box.createVerticalStrut(5));
        box.add(statsLabel);
        box.add(Box.createVerticalStrut(8));
        box.add(status);

        return box;
    }

    private void right(JPanel p) {
        JPanel right = new JPanel(new BorderLayout(0, 10));
        right.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel title = new JLabel(Settings.GRID_TITLE);
        title.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        filePanel = new FileUploader(data);
        panel = filePanel;
        panel.addMouseListener(new ClickLoad());

        info = new JLabel("Hover");
        info.setFont(new Font(Settings.FONT_NAME, Font.PLAIN, 12));
        info.setHorizontalAlignment(SwingConstants.CENTER);
        info.setForeground(Color.GRAY);
        info.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JPanel grid = new JPanel(new BorderLayout());
        grid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        grid.add(panel, BorderLayout.CENTER);

        right.add(title, BorderLayout.NORTH);
        right.add(grid, BorderLayout.CENTER);
        right.add(info, BorderLayout.SOUTH);

        p.add(right, BorderLayout.CENTER);
    }

    private boolean hasData() {
        return data.getRowCount() > 0 && data.getColumnCount() > 0;
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

    // คำนวณ
    private void calc() {
        if (!hasData()) { 
            status.setText(Settings.LOAD_FIRST_MSG);
            Display.showMessage(this, Settings.STATUS_ERROR, Settings.LOAD_FIRST_MSG, JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double inputValue = Double.parseDouble(input.getText());
            double fluidLevelInMeters;
            if (isKm) {
                fluidLevelInMeters = inputValue * 1000.0;
                String calcText = Settings.CALC_WITH_LEVEL + inputValue + " KM (" + fluidLevelInMeters + " M)";
                if (calcText.length() > 35) {
                    calcText = Settings.CALC_WITH_LEVEL + inputValue + " KM\n(" + fluidLevelInMeters + " M)";
                }
                status.setText(calcText);
            } else {
                fluidLevelInMeters = inputValue;
                status.setText(Settings.CALC_WITH_LEVEL + inputValue + " M");
            }
            

            data.setFluidLevel(fluidLevelInMeters);
            update();
        } catch (NumberFormatException e) {
            status.setText(Settings.INVALID_NUMBER_MSG);
        }
    }

    private void clear() {
        data.clearAllData();
        input.setText(String.valueOf(Settings.DEFAULT_FLUID));

        isKm = false;
        km.setSelected(false);
        showUnit(Settings.UNIT_METER);

        update();
        updateBtns();
        status.setText(Settings.FILE_CLEARED_MSG);
    }

    private void updateBtns() {
        btns.removeAll();

        if (hasData()) {
            clear.setMaximumSize(new Dimension(Settings.BTN_WIDTH, Settings.BTN_HEIGHT_MID));
            clear.setAlignmentX(Component.LEFT_ALIGNMENT);
            clear.setFont(new Font(Settings.FONT_NAME, Font.BOLD, 14));
            clear.updateUI();
            btns.add(clear);
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

        String unit;
        String txt;
        if (isKm) {
            unit = Settings.UNIT_KM3;
            txt = Settings.UNIT_KM;
        } else {
            unit = Settings.UNIT_M3;
            txt = Settings.UNIT_METER;
        }

        double display = vol;
        if (isKm) {
            display = vol / 1000000000.0;
        }

        String totalText = String.format(Settings.TOTAL_GAS, fmt.format(display)) + " " + unit;
        total.setText(totalText);
        showUnit(txt);
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
                panel.addMouseListener(new ClickLoad());
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

    private void showUnit(String txt) {
        if (unitLabel != null) {
            unitLabel.setText(txt);
        }
    }

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

    private void exit() {
        int result = JOptionPane.showConfirmDialog(this, Settings.EXIT_MSG, Settings.EXIT_TITLE,
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void icon() {
        try {
            ImageIcon icon = new ImageIcon(Settings.ICON_APP);
            setIconImage(icon.getImage());
        } catch (Exception e) {
        }
    }

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

    private class ClickLoad implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if (!hasData())
                load();
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    private boolean isDark = false;

    private void showThemes(JButton btn) {
        isDark = !isDark;

        if (isDark) {
            setLaf("MONOKAI");
            status.setText(Settings.THEME_STATUS_DARK);
            btn.setText(Settings.THEME_LIGHT);
        } else {
            setLaf("ARC_ORANGE");
            status.setText(Settings.THEME_STATUS_LIGHT);
            btn.setText(Settings.THEME_DARK);
        }
    }

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
            Thread.sleep(2000);
            loading.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ThemeButtonListener implements ActionListener {
        private JButton button;

        public ThemeButtonListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            showThemes(button);
        }
    }

    private class AboutButtonListener implements ActionListener {
        private JButton button;

        public AboutButtonListener(JButton button) {
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) {
            Display.showAbout(button);
        }
    }

    private class ExitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            exit();
        }
    }

    private class UnitCheckboxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            changeUnit(km.isSelected());
        }
    }

    private class CalculateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            calc();
        }
    }

    private class LoadButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            load();
        }
    }

    private class ClearButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clear();
        }
    }


}
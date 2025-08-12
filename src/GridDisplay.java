import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.Border;

public class GridDisplay extends JPanel {
    private FileData data;
    private int size = 40;
    private JPanel grid;
    private JPanel[][] cells;
    private int hoverR = -1;
    private int hoverC = -1;
    private JLabel info;
    private Border normal;
    private Border hover;
    private boolean isDarkTheme;

    public GridDisplay(FileData data) {
        this.data = data;
        this.isDarkTheme = isCurrentThemeDark();
        makeBorder();
        makeGrid();
        calcCellSize();
        makeCells();
        setLayout(new BorderLayout());
        add(grid, BorderLayout.CENTER);
    }
    
    private boolean isCurrentThemeDark() {
        return UIManager.getLookAndFeel().getName().contains("Monokai");
    }

    private void makeBorder() {
        normal = BorderFactory.createLineBorder(Settings.COLOR_LIGHT_GRAY, 1);
        hover = BorderFactory.createLineBorder(Settings.COLOR_BLACK, 2);
    }

    private void makeGrid() {
        grid = new JPanel();
        grid.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"))));
    }

    private void calcCellSize() {
        int cols = data.getColumnCount();
        int rows = data.getRowCount();

        if (cols > 0 && rows > 0) {
            int w = Settings.GRID_MAX_WIDTH / cols;
            int h = Settings.GRID_MAX_HEIGHT / rows;

            size = Math.min(w, h);
            size = Math.max(30, Math.min(size, Settings.GRID_MAX_CELL_SIZE));
        }
    }

    private void makeCells() {
        int rows = data.getRowCount();
        int cols = data.getColumnCount();

        if (rows == 0 || cols == 0) {
            grid.setLayout(new BorderLayout());
            JLabel empty = new JLabel(Settings.NO_DATA_MSG, SwingConstants.CENTER);
            grid.add(empty, BorderLayout.CENTER);
            return;
        }

        grid.setLayout(new BorderLayout());

        JPanel mainGrid = new JPanel(new GridLayout(rows, cols, 1, 1));
        cells = new JPanel[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JPanel cell = makeCell(r, c);
                cells[r][c] = cell;
                mainGrid.add(cell);
            }
        }

        JPanel gridWithCoords = addNumbers(mainGrid, rows, cols);
        grid.add(gridWithCoords, BorderLayout.CENTER);

        int totalW = cols * size + 60;
        int totalH = rows * size + 60;
        grid.setPreferredSize(new Dimension(totalW + 40, totalH + 40));

        setPreferredSize(new Dimension(totalW + 60, totalH + 60));
        setMinimumSize(new Dimension(Settings.GRID_MIN_SIZE_WIDTH, Settings.GRID_MIN_SIZE_HEIGHT));
        setMaximumSize(new Dimension(Settings.GRID_MAX_SIZE_WIDTH, Settings.GRID_MAX_SIZE_HEIGHT));
    }

    private JPanel addNumbers(JPanel mainGrid, int rows, int cols) {
        JPanel wrapper = new JPanel(new BorderLayout());

        JPanel topNumbers = new JPanel(new GridLayout(1, cols, 1, 1));
        topNumbers.setBorder(BorderFactory.createEmptyBorder(0, 30, 5, 0));
        for (int c = 0; c < cols; c++) {
            JLabel num = new JLabel(String.valueOf(c), SwingConstants.CENTER);
            num.setFont(new Font(Font.MONOSPACED, Font.BOLD, Settings.FONT_SIZE_TINY));
            if (isDarkTheme) {
                num.setForeground(Settings.DARK_TEXT_SECONDARY);
            } else {
                num.setForeground(Settings.LIGHT_TEXT_MUTED);
            }
            topNumbers.add(num);
        }

        JPanel leftNumbers = new JPanel(new GridLayout(rows, 1, 1, 1));
        leftNumbers.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        for (int r = 0; r < rows; r++) {
            JLabel num = new JLabel(String.valueOf(r), SwingConstants.CENTER);
            num.setFont(new Font(Font.MONOSPACED, Font.BOLD, Settings.FONT_SIZE_TINY));
            if (isDarkTheme) {
                num.setForeground(Settings.DARK_TEXT_SECONDARY);
            } else {
                num.setForeground(Settings.LIGHT_TEXT_MUTED);
            }
            num.setPreferredSize(new Dimension(25, size));
            leftNumbers.add(num);
        }

        JPanel gridArea = new JPanel(new BorderLayout());
        gridArea.add(mainGrid, BorderLayout.CENTER);
        gridArea.add(leftNumbers, BorderLayout.WEST);

        wrapper.add(topNumbers, BorderLayout.NORTH);
        wrapper.add(gridArea, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel makeCell(int r, int c) {
        JPanel cell = new JPanel(new BorderLayout());
        cell.setPreferredSize(new Dimension(size, size));
        cell.setBackground(getColor(r, c));
        cell.setBorder(normal);
        cell.setOpaque(true);

        double pct = data.calculateGasPercentage(r, c) * 100;
        String txt;
        if (Double.isNaN(pct) || Double.isInfinite(pct)) {
            txt = "0%";
        } else {
            txt = String.format("%.0f%%", pct);
        }

        JLabel lbl = new JLabel(txt, SwingConstants.CENTER);
        lbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, Math.max(Settings.FONT_SIZE_SMALL, size / 6)));
        
        Color cellBg = getColor(r, c);
        if (isColorDark(cellBg)) {
            lbl.setForeground(Color.WHITE);
        } else {
            lbl.setForeground(Color.BLACK);
        }
        
        cell.add(lbl, BorderLayout.CENTER);
        cell.addMouseListener(new CellMouse(r, c, cell));
        return cell;
    }

    private class CellMouse implements MouseListener {
        private int r, c;
        private JPanel cell;

        public CellMouse(int r, int c, JPanel cell) {
            this.r = r;
            this.c = c;
            this.cell = cell;
        }

        public void mouseEntered(MouseEvent e) {
            mouseIn(r, c, cell);
        }

        public void mouseExited(MouseEvent e) {
            mouseOut(r, c, cell);
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    private void mouseIn(int r, int c, JPanel cell) {
        clearHover();

        hoverR = r;
        hoverC = c;

        cell.setBorder(hover);
        Color hoverColor = getColor(r, c).brighter();
        cell.setBackground(hoverColor);

        showInfo(r, c);
    }

    private void mouseOut(int r, int c, JPanel cell) {
        if (hoverR == r && hoverC == c) {
            cell.setBorder(normal);
            Color normalColor = getColor(r, c);
            cell.setBackground(normalColor);

            if (info != null)
                info.setText(Settings.GRID_HOVER_MESSAGE);
        }
    }

    public void setInfoLabel(JLabel lbl) {
        this.info = lbl;
    }

    private void clearHover() {
        if (hoverR != -1 && hoverC != -1 && cells != null &&
                hoverR < cells.length && hoverC < cells[0].length) {
            JPanel prev = cells[hoverR][hoverC];
            if (prev != null) {
                prev.setBorder(normal);
                Color normalColor = getColor(hoverR, hoverC);
                prev.setBackground(normalColor);

            }
        }
        hoverR = -1;
        hoverC = -1;
        if (info != null)
            info.setText(Settings.GRID_HOVER_MESSAGE);
    }

    private void showInfo(int r, int c) {
        if (info != null) {
            String txt = makeInfo(r, c);
            info.setText(txt);
        }
    }

    private Color getColor(int r, int c) {
        int level = data.getGasLevel(r, c);

        if (level == 0)
            return Settings.COLOR_NO_GAS;
        if (level == 1)
            return Settings.COLOR_LOW_GAS;
        if (level == 2)
            return Settings.COLOR_HIGH_GAS;
        return Settings.COLOR_WHITE;
    }

    private String makeInfo(int r, int c) {
        int level = data.getGasLevel(r, c);
        double pct = data.calculateGasPercentage(r, c) * 100;
        double vol = data.calculateGasVolume(r, c);
        
        if (Double.isNaN(pct) || Double.isInfinite(pct)) {
            pct = 0.0;
        }
        if (Double.isNaN(vol) || Double.isInfinite(vol)) {
            vol = 0.0;
        }

        DecimalFormat pctFmt = new DecimalFormat("0.00");
        DecimalFormat volFmt = new DecimalFormat("#,##0.00");

        String volTxt = volFmt.format(vol);
        String pctTxt = pctFmt.format(pct);

        String status;
        if (level == 0) {
            status = Settings.GRID_NO_GAS_STATUS;
        } else if (level == 1) {
            status = Settings.GRID_LOW_GAS_STATUS;
        } else {
            status = Settings.GRID_HIGH_GAS_STATUS;
        }

        return "Cell (" + r + "," + c + ") - " + status +
                " | " + pctTxt + "% | " + volTxt + " CB.M ";
    }



    private boolean isColorDark(Color color) {
      
        return color.equals(Settings.COLOR_NO_GAS);
    }

    public void refresh() {
        this.isDarkTheme = isCurrentThemeDark();
        grid.removeAll();
        calcCellSize();
        makeCells();
        revalidate();
        repaint();
    }
}
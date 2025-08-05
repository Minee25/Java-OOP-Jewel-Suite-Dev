import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class GridDisplay extends DisplayPanel {
    private int cellSize = 40;
    private JPanel gridPanel;
    private JPanel[][] cellPanels;
    private int hoverRow = -1;
    private int hoverCol = -1;
    private JLabel infoLabel;
    private Border normalBorder;
    private Border hoverBorder;

    public GridDisplay(FileData data) {
        super(data);
        initializeBorders();
        setupGridPanel();
        calculateCellSize();
        createCellPanels();
        setLayout(new BorderLayout());
        add(gridPanel, BorderLayout.CENTER);
    }

    private void initializeBorders() {
        normalBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        hoverBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
    }

    private void setupGridPanel() {
        gridPanel = new JPanel();
        gridPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"))
        ));
    }

    private void calculateCellSize() {
        int cols = data.getCols();
        int rows = data.getRows();

        if (cols > 0 && rows > 0) {
            int widthSize = Settings.GRID_MAX_WIDTH / cols;
            int heightSize = Settings.GRID_MAX_HEIGHT / rows;

            cellSize = Math.min(widthSize, heightSize);
            cellSize = Math.max(Settings.GRID_MIN_CELL_SIZE, Math.min(cellSize, Settings.GRID_MAX_CELL_SIZE));
        }
    }

    private void createCellPanels() {
        int rows = data.getRows();
        int cols = data.getCols();

        if (rows == 0 || cols == 0) {
            gridPanel.setLayout(new BorderLayout());
            JLabel emptyLabel = new JLabel("No data to display", SwingConstants.CENTER);
            gridPanel.add(emptyLabel, BorderLayout.CENTER);
            return;
        }

        gridPanel.setLayout(new GridLayout(rows, cols, 1, 1));
        cellPanels = new JPanel[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JPanel cellPanel = createCellPanel(r, c);
                cellPanels[r][c] = cellPanel;
                gridPanel.add(cellPanel);
            }
        }

        // Set preferred size for the grid panel
        int totalWidth = cols * cellSize + (cols + 1);
        int totalHeight = rows * cellSize + (rows + 1);
        gridPanel.setPreferredSize(new Dimension(totalWidth + 40, totalHeight + 40));

        setPreferredSize(new Dimension(totalWidth + 60, totalHeight + 60));
        setMinimumSize(new Dimension(Settings.GRID_MIN_SIZE_WIDTH, Settings.GRID_MIN_SIZE_HEIGHT));
        setMaximumSize(new Dimension(Settings.GRID_MAX_SIZE_WIDTH, Settings.GRID_MAX_SIZE_HEIGHT));
    }

    private JPanel createCellPanel(int row, int col) {
        JPanel cellPanel = new JPanel();
        cellPanel.setPreferredSize(new Dimension(cellSize, cellSize));
        cellPanel.setBackground(getColor(row, col));
        cellPanel.setBorder(normalBorder);
        cellPanel.setOpaque(true);

        // Add mouse listeners for hover effects
        cellPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                handleCellHover(row, col, cellPanel);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                handleCellExit(row, col, cellPanel);
            }
        });

        return cellPanel;
    }

    private void handleCellHover(int row, int col, JPanel cellPanel) {
        // Clear previous hover state
        clearHover();

        // Set new hover state
        hoverRow = row;
        hoverCol = col;

        // Update visual appearance
        cellPanel.setBorder(hoverBorder);
        cellPanel.setBackground(getColor(row, col).brighter());

        // Show info
        showInfo(row, col);
    }

    private void handleCellExit(int row, int col, JPanel cellPanel) {
        if (hoverRow == row && hoverCol == col) {
            cellPanel.setBorder(normalBorder);
            cellPanel.setBackground(getColor(row, col));
            infoLabel.setText("...");
        }
    }

    public void setInfoLabel(JLabel label) {
        this.infoLabel = label;
    }

    public void clearInfo() {
        if (infoLabel != null) {
            infoLabel.setText(Settings.GRID_HOVER_MESSAGE);
        }
    }

    private void clearHover() {
        if (hoverRow != -1 && hoverCol != -1 && cellPanels != null &&
                hoverRow < cellPanels.length && hoverCol < cellPanels[0].length) {
            JPanel previousPanel = cellPanels[hoverRow][hoverCol];
            if (previousPanel != null) {
                previousPanel.setBorder(normalBorder);
                previousPanel.setBackground(getColor(hoverRow, hoverCol));
            }
        }
        hoverRow = -1;
        hoverCol = -1;
        clearInfo();
    }

    private void showInfo(int row, int col) {
        if (infoLabel != null) {
            String info = makeInfoText(row, col);
            infoLabel.setText(info);
        }
    }

    private Color getColor(int r, int c) {
        int level = data.getLevel(r, c);
        if (level == 0) {
            return Settings.COLOR_NO_GAS;
        } else if (level == 1) {
            return Settings.COLOR_LOW_GAS;
        } else if (level == 2) {
            return Settings.COLOR_HIGH_GAS;
        } else {
            return Color.WHITE;
        }
    }

    private String makeInfoText(int r, int c) {
        int level = data.getLevel(r, c);
        double percent = data.getPercent(r, c) * 100;
        double volume = data.getVolume(r, c);

        String status;
        if (level == 0) {
            status = Settings.GRID_NO_GAS_STATUS;
        } else if (level == 1) {
            status = Settings.GRID_LOW_GAS_STATUS;
        } else {
            status = Settings.GRID_HIGH_GAS_STATUS;
        }

        return String.format(Settings.GRID_INFO_FORMAT, r, c, status, percent, volume);
    }

    @Override
    public void refresh() {
        // Remove all components
        gridPanel.removeAll();

        // Recalculate cell size
        calculateCellSize();

        // Recreate cell panels
        createCellPanels();

        // Refresh the display
        revalidate();
        repaint();
    }

    // Optional: Method to update a specific cell
    public void updateCell(int row, int col) {
        if (cellPanels != null && row >= 0 && row < cellPanels.length &&
                col >= 0 && col < cellPanels[0].length) {
            JPanel cellPanel = cellPanels[row][col];
            if (cellPanel != null) {
                cellPanel.setBackground(getColor(row, col));
                cellPanel.repaint();
            }
        }
    }

    // Optional: Method to get cell panel for further customization
    public JPanel getCellPanel(int row, int col) {
        if (cellPanels != null && row >= 0 && row < cellPanels.length &&
                col >= 0 && col < cellPanels[0].length) {
            return cellPanels[row][col];
        }
        return null;
    }
}
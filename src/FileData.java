import java.io.*;
import java.util.*;
public class FileData {
    private double[][] grid;
    private double level;
    private int rows;
    private int cols;

    public FileData() {
        level = Settings.DEFAULT_FLUID;
        load();
    }

    public boolean load() {
        return loadFromFile("dept.txt");
    }

    public boolean loadFromFile(String path) {
        try {
            Scanner scan = new Scanner(new File(path));
            List<Double> nums = new ArrayList<>();

            while (scan.hasNextDouble()) {
                nums.add(scan.nextDouble());
            }
            scan.close();

            rows = Settings.GRID_H;
            cols = Settings.GRID_W;
            grid = new double[rows][cols];

            int i = 0;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (i < nums.size()) {
                        grid[r][c] = nums.get(i++);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double getBottom(int r, int c) {
        if (r >= 0 && r < rows && c >= 0 && c < cols) {
            return grid[r][c];
        }
        return 0;
    }

    public double getTop(int r, int c) {
        return getBottom(r, c) - Settings.TOP_BASE;
    }

    public double getVolume(int r, int c) {
        double top = getTop(r, c);
        double bottom = getBottom(r, c);

        if (level <= top) {
            return 0;
        }

        double depth = Math.min(level, bottom) - top;
        if (depth <= 0) return 0;

        return Settings.CELL_SIZE * Settings.CELL_SIZE * depth;
    }

    public double getPercent(int r, int c) {
        double top = getTop(r, c);
        double bottom = getBottom(r, c);
        double total = bottom - top;

        if (total <= 0) return 0;

        double gas = Math.max(0, Math.min(level, bottom) - top);
        return gas / total;
    }

    public int getLevel(int r, int c) {
        double percent = getPercent(r, c);

        if (percent <= 0) return 0;
        if (percent < Settings.GAS_LIMIT) return 1;
        return 2;
    }

    public double getTotalVolume() {
        double total = 0.0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                total += getVolume(r, c);
            }
        }
        return total;
    }

    public void setFluidLevel(double val) {
        level = val;
    }

    public double getFluidLevel() {
        return level;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void clearData() {
        grid = new double[0][0];
        rows = 0;
        cols = 0;
        level = Settings.DEFAULT_FLUID;
    }
}
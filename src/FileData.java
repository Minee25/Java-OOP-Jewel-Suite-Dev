import java.io.*;
import java.util.*;

public class FileData {
    private double[][] data;
    private double fluid;
    private int rows;
    private int cols;

    public FileData() {
        fluid = Settings.DEFAULT_FLUID;
        load();
    }

    public boolean load() {
        return readFile("dept.txt");
    }

    public boolean readFile(String path) {
        try (Scanner sc = new Scanner(new File(path))) {
            List<Double> nums = new ArrayList<>();

            while (sc.hasNextDouble()) {
                nums.add(sc.nextDouble());
            }

            rows = Settings.GRID_H;
            cols = Settings.GRID_W;
            data = new double[rows][cols];

            int i = 0;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (i < nums.size()) {
                        data[r][c] = nums.get(i++);
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
            return data[r][c];
        }
        return 0;
    }

    public double getTop(int r, int c) {
        return getBottom(r, c) - Settings.TOP_BASE;
    }

    public double getVolume(int r, int c) {
        double top = getTop(r, c);
        double bottom = getBottom(r, c);

        if (fluid <= top)
            return 0;

        double depth = Math.min(fluid, bottom) - top;
        if (depth <= 0)
            return 0;

        return Settings.CELL_SIZE * Settings.CELL_SIZE * depth;
    }

    public double getPercent(int r, int c) {
        double top = getTop(r, c);
        double bottom = getBottom(r, c);
        double total = bottom - top;

        if (total <= 0)
            return 0;

        double gas = Math.max(0, Math.min(fluid, bottom) - top);
        return gas / total;
    }

    public int getLevel(int r, int c) {
        double pct = getPercent(r, c);

        if (pct <= 0)
            return 0;
        if (pct < Settings.GAS_LIMIT)
            return 1;
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

    public void setFluid(double level) {
        fluid = level;
    }

    public double getFluid() {
        return fluid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void clear() {
        data = new double[0][0];
        rows = 0;
        cols = 0;
        fluid = Settings.DEFAULT_FLUID;
    }


    public double getBottomDepth(int r, int c) {
        return getBottom(r, c);
    }

    public double getTopDepth(int r, int c) {
        return getTop(r, c);
    }

    public double calculateGasVolume(int r, int c) {
        return getVolume(r, c);
    }

    public double calculateGasPercentage(int r, int c) {
        return getPercent(r, c);
    }

    public int getGasLevel(int r, int c) {
        return getLevel(r, c);
    }

    public double calculateTotalGasVolume() {
        return getTotalVolume();
    }

    public int getRowCount() {
        return getRows();
    }

    public int getColumnCount() {
        return getCols();
    }

    public double getFluidLevel() {
        return getFluid();
    }

    public void setFluidLevel(double level) {
        setFluid(level);
    }

    public void clearAllData() {
        clear();
    }

    public boolean loadFromFile(String path) {
        return readFile(path);
    }
}
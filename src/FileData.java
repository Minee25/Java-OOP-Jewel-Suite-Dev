import java.io.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FileData extends JFrame {
    private double[][] data;
    private double fluid;
    private int rows;
    private int cols;

    public FileData() {
        fluid = Settings.DEFAULT_FLUID;

    }

    // test ไว้เทสเฉยๆๆ
    public boolean load() {
        return readFile("data/dept.txt");
    }

    public boolean readFile(String path) {
        try (Scanner sc = new Scanner(new File(path))) {
            List<String> lines = new ArrayList<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }

            if (lines.isEmpty()) {
                return false;
            }

            rows = lines.size();

            int maxCols = 0;
            for (String line : lines) {
                String[] values = line.split("\\s+");

                maxCols = Math.max(maxCols, values.length); // หาคอลัมมากสุดมาใช้สำหรับคำนวณ
            }

            cols = maxCols;
            data = new double[rows][cols];

            int invalidCount = 0;

            // isNaN ป้องกันไม่ใช่ตัวเลข isInfinite เลขจำนวนเยอะเกิน
            for (int r = 0; r < rows; r++) {
                String[] values = lines.get(r).split("\\s+");
                for (int c = 0; c < Math.min(cols, values.length); c++) {
                    try {
                        String value = values[c].trim();
                        if (value.isEmpty()) {
                            data[r][c] = Double.NaN;
                            invalidCount++;
                        } else {
                            double parsedValue = Double.parseDouble(value);
                            if (Double.isNaN(parsedValue) || Double.isInfinite(parsedValue)) {
                                data[r][c] = Double.NaN;
                                invalidCount++;
                                if (Settings.debugMode) {
                                    System.out.println("value at [" + r + "," + c + "]: '" + value
                                            + "' -> set to NaN (invalid)");
                                }

                            } else {
                                data[r][c] = parsedValue;
                            }
                        }
                    } catch (NumberFormatException e) {
                        data[r][c] = Double.NaN;
                        invalidCount++;
                        if (Settings.debugMode) {
                            System.out.println(
                                    "data at [" + r + "," + c + "]: '" + values[c] + "' -> set to NaN (parse error)");
                        }

                    }
                }

                for (int c = values.length; c < cols; c++) {
                    data[r][c] = Double.NaN;
                    invalidCount++;
                }
            }

            if (invalidCount > 0) {
                Display.showMessage(this, "Warning: Found ",
                        invalidCount + " invalid values, marked as invalid (will show as gray)",
                        JOptionPane.WARNING_MESSAGE);

            }

            return true;
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    // ? คำนวณยากรอถามรุ้นพี่
    // =====================

    // เช็คว่าค่าในช่องนี้เป็นค่าที่ไม่ถูกต้องหรือไม่
    public boolean isInvalidCell(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return true;
        }
        return Double.isNaN(data[r][c]);
    }

    // Base Horizon ค่าดั่งเดิมจากไฟล์ในแต่ละช่อง
    public double getBottom(int r, int c) {
        return data[r][c];
    }

    // Top Horizon - Settings.TOP_BASE(200) ตามที่กำหนดให้
    public double getTop(int r, int c) {
        return getBottom(r, c) - Settings.TOP_BASE;
    }

    /*
     * ดังนั้นหากขุดเจาะที่บริเวณนี้ จะได้แก๊สเป็นปริมาตร กว้าง x ยาว x ลึก
     * 200 x 200 x (3000 – (3125 - 200)) = 3,000,000 ลบ.เมตร.
     * Settings.CELL_SIZE (150 ) ตามที่กำหนดมา
     * หาค่าน้อย Math.min(fluid, bottom)
     */
    public double getVolume(int r, int c) {
        // ถ้าเป็นค่าที่ไม่ถูกต้อง ไม่คำนวณ
        if (isInvalidCell(r, c))
            return 0;

        double top = getTop(r, c);
        double bottom = getBottom(r, c);
        if (bottom <= 0)
            return 0;

        if (fluid <= top)
            return 0;

        double depth = fluid - top;
        if (depth <= 0 || Double.isNaN(depth) || Double.isInfinite(depth))
            return 0;

        double result = Settings.CELL_SIZE * Settings.CELL_SIZE * depth;

        return result;
    }

    private void debugPer(String msg) {
        if (Settings.debugMode) {
            System.out.println(msg);
        }
    }

    public double getPercent(int r, int c) {
        debugPer("==== DEBUG getPercent() ====");
        debugPer("Row=" + r + ", Col=" + c);

        if (isInvalidCell(r, c)) {
            debugPer("Invalid cell - returning NaN");
            debugPer("============================");
            return Double.NaN;
        }

        double top = getTop(r, c);
        double bottom = getBottom(r, c);
        if (bottom <= 0)
            return 0;

        debugPer("top=" + top + ", bottom=" + bottom);

        double total = bottom - top;
        debugPer("total=" + total);

        double gas = Math.max(0, Math.min(fluid, bottom) - top);
        debugPer("gas=" + gas);

        double result = gas / total;
        debugPer("result=" + result);
        debugPer("============================");

        return result;
    }

    // หาเปอและไว้ทำสี
    public int getLevel(int r, int c) {
        if (isInvalidCell(r, c)) {
            return -1;
        }

        double pct = getPercent(r, c);

        if (Double.isNaN(pct) || pct <= 0)
            return 0;
        if (pct < Settings.GAS_LIMIT)
            return 1;
        return 2;
    }

    public double getTotalVolume() {
        double total = 0.0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!isInvalidCell(r, c)) {
                    total += getVolume(r, c);
                }
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

    public boolean isValidNumber(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int countZeroCells() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!isInvalidCell(r, c) && data[r][c] == 0.0) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countInvalidCells() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isInvalidCell(r, c)) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countValidCells() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!isInvalidCell(r, c)) {
                    count++;
                }
            }
        }
        return count;
    }
}
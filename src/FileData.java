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
            String[] firstRow = lines.get(0).split("\\s+");
            cols = firstRow.length;
            data = new double[rows][cols];
            
            for (int r = 0; r < rows; r++) {
                String[] values = lines.get(r).split("\\s+");
                for (int c = 0; c < Math.min(cols, values.length); c++) {
                    data[r][c] = Double.parseDouble(values[c]);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
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
    ดังนั้นหากขุดเจาะที่บริเวณนี้ จะไดQแกGสเป1นปริมาตร กวQาง x ยาว x ลึก
    200 x 200 x (3000 – (3125 - 200)) = 3,000,000 ลบ.เมตร.
    Settings.CELL_SIZE (150 ) ตามที่กำหนดมา
    หาค่าน้อย Math.min(fluid, bottom)
    */ 
    public double getVolume(int r, int c) {
        double top = getTop(r, c);
        double bottom = getBottom(r, c);

        if (fluid <= top)
            return 0;


        double depth = fluid - top;
        System.out.println("debug "+bottom+" "+r+" "+c+" "+depth+" "+Settings.CELL_SIZE * Settings.CELL_SIZE * depth);
        if (depth <= 0)
            return 0;

        return Settings.CELL_SIZE * Settings.CELL_SIZE * depth;
    }

    // หาเปอ 
    public double getPercent(int r, int c) {
        double top = getTop(r, c);
        double bottom = getBottom(r, c);
        double total = bottom - top; 

        if (total <= 0)
            return 0;

        double gas = Math.max(0, Math.min(fluid, bottom) - top);
        return gas / total;
    }

    // หาเปอและไว้ทำสี
    public int getLevel(int r, int c) {
        double pct = getPercent(r, c);

        if (pct <= 0)
            return 0;
        if (pct < Settings.GAS_LIMIT)
            return 1;
        return 2;
    }

    // ทังหมดบวกกัน
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
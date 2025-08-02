import java.io.*;
import java.util.*;
// คลาสสำหรับคำนวณ
public class FileData {

    private double[][] data;        // ข้อมูลจากไฟล์
    private double FluidLevel;      // ระดับ Fluid contact
    private int rowCount;           // จำนวนแถว
    private int colCount;           // จำนวนคอลัมน์

    public FileData() {
        this.FluidLevel = Settings.DEFAULT_FLUID;
        loadFile();
    }

    public boolean loadFile() {
        return loadFromFile("dept.txt");
    }

    public boolean loadFromFile(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            List<Double> numbers = new ArrayList<>();

            while (scanner.hasNextDouble()) {
                numbers.add(scanner.nextDouble());
            }
            scanner.close();

            this.rowCount = Settings.GRID_H;
            this.colCount = Settings.GRID_W;
            this.data = new double[rowCount][colCount];

            int index = 0;
            for (int row = 0; row < rowCount; row++) {
                for (int col = 0; col < colCount; col++) {
                    if (index < numbers.size()) {

                        data[row][col] = numbers.get(index++);
                        System.out.println(data[row][col] );
                    }
                }
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public double getBottom(int row, int col) {
        if (row >= 0 && row < rowCount && col >= 0 && col < colCount) {
            return data[row][col];
        }
        return 0;
    }

    public double getTop(int row, int col) {
        return getBottom(row, col) - Settings.TOP_BASE;
    }

    public double getVolume(int row, int col) {
        double top = getTop(row, col);
        double bottom = getBottom(row, col);

        if (FluidLevel <= top) {
            return 0;
        }

        double depth = Math.min(FluidLevel, bottom) - top;
        if (depth <= 0) return 0;

        return Settings.CELL_SIZE * Settings.CELL_SIZE * depth;
    }

    public double getPercent(int row, int col) {
        double top = getTop(row, col);
        double bottom = getBottom(row, col);
        double total = bottom - top;

        if (total <= 0) return 0;

        double gas = Math.max(0, Math.min(FluidLevel, bottom) - top);
        return gas / total;
    }

    public int getLevel(int row, int col) {
        double percent = getPercent(row, col);

        if (percent <= 0) return 0;
        if (percent < Settings.GAS_LIMIT) return 1;
        return 2;
    }

    public double getTotalVolume() {
        double total = 0.0;
        return total;
    }

    public void setFluidLevel(double level) {
        this.FluidLevel = level;
    }

    public double getFluidLevel() {
        return FluidLevel;
    }

    public int getRows() {
        return rowCount;
    }

    public int getCols() {
        return colCount;
    }

    public void clearData() {
        this.data = new double[0][0];
        this.rowCount = 0;
        this.colCount = 0;
        this.FluidLevel = Settings.DEFAULT_FLUID;
    }
}
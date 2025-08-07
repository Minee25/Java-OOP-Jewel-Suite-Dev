import java.io.*;
import java.util.*;

public class FileData {
    // ตัวแปรเก็บข้อมูล
    private double[][] grid; // เก็บค่าความลึกของชั้นหินล่าง (Base Horizon) ของแต่ละช่อง
    private double level;    // ระดับ Fluid Contact
    private int rows;        // จำนวนแถวในตาราง (10 แถว)
    private int cols;        // จำนวนหลักในตาราง (20 หลัก)

    // ฟังก์ชันสร้างออบเจ็กต์ - ตั้งค่าเริ่มต้น
    public FileData() {
        level = Settings.DEFAULT_FLUID; // ตั้งระดับน้ำเริ่มต้น
        load(); // โหลดข้อมูลจากไฟล์
    }

    // ฟังก์ชันโหลดข้อมูลจากไฟล์ dept.txt
    public boolean load() {
        return loadFromFile("dept.txt");
    }

    // ฟังก์ชันอ่านข้อมูลจากไฟล์
    public boolean loadFromFile(String path) {
        try (Scanner scan = new Scanner(new File(path))) {
            // สร้าง List
            List<Double> nums = new ArrayList<>();

            while (scan.hasNextDouble()) {
                nums.add(scan.nextDouble());
            }

            // กำหนดขนาดตาราง (10 แถว x 20 หลัก = 200 ช่อง)
            rows = Settings.GRID_H;
            cols = Settings.GRID_W;
            grid = new double[rows][cols];

            int i = 0;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (i < nums.size()) {
                        grid[r][c] = nums.get(i++);
                        System.out.println(grid[r][c]);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //หาความลึกของชั้นหินล่าง (Base Horizon) ค่าเดิมจากไฟล์
    public double getBottom(int r, int c) {
        // ตรวจสอบว่าตำแหน่งที่ขออยู่ในตารางไหม
        if (r >= 0 && r < rows && c >= 0 && c < cols) {

            return grid[r][c];
        }
        return 0;
    }

    //ความลึกของชั้นหินบน (Top Horizon)
    public double getTop(int r, int c) {
        // ชั้นหินบน = ชั้นหินล่าง - ความหนาของชั้นหิน 200
        return getBottom(r, c) - Settings.TOP_BASE;
    }

    // คำนวณก๊าซในแต่ละช่อง
    public double getVolume(int r, int c) {
        double top = getTop(r, c);       // ความลึกชั้นหินบน
        double bottom = getBottom(r, c); // ความลึกชั้นหินล่าง

        // ไม่มี
        if (level <= top) return 0;

        // ความลึกของก๊าซ
        // min(ระดับน้ำ, ชั้นล่าง)
        // ความสูงก๊าซ = min(2500, 2549) - 2449 = 2500 - 2449 = 51 เมตร
        double depth = Math.min(level, bottom) - top; // ความลึก
        if (depth <= 0) return 0;

        // คำนวณปริมาตร = พื้นที่ช่อง × ความสูงก๊าซ
        return Settings.CELL_SIZE * Settings.CELL_SIZE * depth;
    }

    // เปอร์เซ็นต์ก๊าซในแต่ละช่อง
    public double getPercent(int r, int c) {
        double top = getTop(r, c);       // ความลึกชั้นหินบน
        double bottom = getBottom(r, c); // ความลึกชั้นหินล่าง
        double total = bottom - top;     // ความหนาชั้นหินทั้งหมด (เช่น 2549-2449=100 เมตร)

        if (total <= 0) return 0; // ถ้าความหนา = 0 → ไม่มีชั้นหิน

        // คำนวณส่วนที่มีก๊าซ (เหมือนฟังก์ชัน getVolume แต่ไม่คูณพื้นที่)
        // ตัวอย่าง: ชั้นบน = 2449, ระดับน้ำ = 2500, ชั้นล่าง = 2549
        //          ส่วนที่มีก๊าซ = min(2500, 2549) - 2449 = 51 เมตร
        double gas = Math.max(0, Math.min(level, bottom) - top);

        // คำนวณเปอร์เซ็นต์ = ส่วนที่มีก๊าซ ÷ ความหนาทั้งหมด
        // ตัวอย่าง: เปอร์เซ็นต์ = 51 ÷ 100 = 0.51 หรือ 51%
        return gas / total;
    }

    // ฟังก์ชันกำหนดระดับสีสำหรับแสดงผล
    public int getLevel(int r, int c) {
        double percent = getPercent(r, c); // หาเปอร์เซ็นต์ก๊าซ

        // กำหนดเงื่อนไขการแสดงสี
        if (percent <= 0) return 0;                    // ไม่มีก๊าซ (0%) → ระดับ 0 (สีขาว)
        if (percent < Settings.GAS_LIMIT) return 1;    // ก๊าซน้อย (เช่น <50%) → ระดับ 1 (สีเหลือง)
        return 2;                                      // ก๊าซเยอะ (เช่น ≥50%) → ระดับ 2 (สีแดง)
    }

    // ฟังก์ชันคำนวณปริมาตรก๊าซทั้งหมดในพื้นที่
    public double getTotalVolume() {
        double total = 0.0; // ตัวแปรเก็บผลรวม

        // วนลูปทุกช่องในตาราง (200 ช่อง)
        for (int r = 0; r < rows; r++) {        // วนแถว (0-9)
            for (int c = 0; c < cols; c++) {    // วนหลัก (0-19)
                total += getVolume(r, c);       // บวกปริมาตรก๊าซของแต่ละช่อง
            }
        }
        return total; // คืนผลรวมปริมาตรก๊าซทั้งหมด
    }

    // ฟังก์ชันปรับค่าระดับ Fluid Contact (เส้นแบ่งน้ำ-ก๊าซ)
    public void setFluidLevel(double val) {
        level = val; // เปลี่ยนระดับน้ำ (จะส่งผลต่อการคำนวณปริมาตรก๊าซ)
    }

    // ฟังก์ชันดูระดับ Fluid Contact ปัจจุบัน
    public double getFluidLevel() {
        return level;
    }

    // ฟังก์ชันดูจำนวนแถว
    public int getRows() {
        return rows;
    }

    // ฟังก์ชันดูจำนวนหลัก
    public int getCols() {
        return cols;
    }

    // ฟังก์ชันล้างข้อมูลทั้งหมด
    public void clearData() {
        grid = new double[0][0]; // สร้างตารางว่าง
        rows = 0;                // รีเซ็ตจำนวนแถว
        cols = 0;                // รีเซ็ตจำนวนหลัก
        level = Settings.DEFAULT_FLUID; // รีเซ็ตระดับน้ำกลับเป็นค่าเริ่มต้น
    }
}
import java.awt.Color;

// ตั้งค่า
public class Settings {

    // ธีม
    public static String CURRENT_THEME = "ARC_ORANGE";

    // ขนาดปุ่ม
    public static final int BTN_WIDTH = 320;
    public static final int BTN_HEIGHT_BIG = 50; // ปุ่มใหญ่
    public static final int BTN_HEIGHT_MID = 42; // ปุ่มกลาง
    public static final int INPUT_HEIGHT = 45;

    // ข้อมูล
    public static final int GRID_W = 22;
    public static final int GRID_H = 10;
    public static final double CELL_SIZE = 150.0;
    public static final double DEFAULT_FLUID = 2500.0;
    public static final double TOP_BASE = 200.0;
    public static final double GAS_LIMIT = 0.5;

    // สี
    public static final Color COLOR_NO_GAS = Color.RED;
    public static final Color COLOR_LOW_GAS = Color.YELLOW;
    public static final Color COLOR_HIGH_GAS = Color.GREEN;

    // ฟอนต์
    public static final String FONT_NAME = "Segoe UI";
    public static final int FONT_SIZE_TITLE = 24;
    public static final int FONT_SIZE_NORMAL = 13;
    public static final int FONT_SIZE_INPUT = 15;
    public static final int FONT_SIZE_BUTTON = 16;

    // หน้าต่าง
    public static final int WIN_W = 1350;
    public static final int WIN_H = 800;
    public static final Boolean WINDOW_MENU = true;

    // ไอคอน
    public static final String APP_ICON_PATH = "./src/res/icon.png";
    public static final int ICON_SIZE_SMALL = 96;
    public static final String ICON_APP = "./src/res/icon2.png";

    // ส่วนหัว
    public static final String APP_TITLE = "Jewel Suite";
    public static final String APP_VERSION = "v1.0.0";
    public static final String BTN_ABOUT = "About";
    public static final String BTN_CLOSE_ABOUT = "CLOSE";
    public static final String BTN_EXIT = "Exit";
    public static final String EXIT_TITLE = "Exit Confirmation";
    public static final String EXIT_MSG = "Are you sure you want to exit?";

    // ส่วนควบคุม
    public static final String INPUT_LABEL = "Liquid depth (M.)";
    public static final String BTN_CALC = "Calculate";
    public static final String BTN_LOAD = "Load File";
    public static final String BTN_CLEAR = "Clear File";

    // คำอธิบาย
    public static final String LEGEND_TITLE = "Description";
    public static final String NO_GAS = "NO GAS (0%)";
    public static final String LOW_GAS = "Low Gas (<50%)";
    public static final String HIGH_GAS = "Lots of Gas (>50%)";

    // ผลลัพธ์
    public static final String RESULT_TITLE = "Resultant:";
    public static final String TOTAL_GAS = "Total Gas %.2f CB.M";
    public static final String STATUS_READY = "Ready";

    // สถานะ
    public static final String STATUS_LOAD = "Load File : ";
    public static final String STATUS_FAIL = "Failed loading file!";
    public static final String STATUS_ERROR = "Error!";
    public static final String STATUS_CHECK = "Unable to load file. Please check file format.";

    // อัพโหลดไฟล์
    public static final String GRID_TITLE = "Gas Distribution Table";

    public static final String FILE_TITLE = "Place the dept.txt file here";
    public static final String FILE_SUB = "or click to select file";
    public static final String SELECT_FILE_TITLE = "Select file dept.txt";
    public static final String FILE_OF_TYPE = "File Type (*.txt)";

    // Grid Display
    public static final int GRID_MAX_WIDTH = 800;
    public static final int GRID_MAX_HEIGHT = 600;
    public static final int GRID_MIN_CELL_SIZE = 22;
    public static final int GRID_MAX_CELL_SIZE = 50;
    public static final int GRID_MIN_SIZE_WIDTH = 600;
    public static final int GRID_MIN_SIZE_HEIGHT = 400;
    public static final int GRID_MAX_SIZE_WIDTH = 1000;
    public static final int GRID_MAX_SIZE_HEIGHT = 800;
    public static final String GRID_HOVER_MESSAGE = "Hover over grid cells to see information.";
    public static final String GRID_NO_GAS_STATUS = "No Gas";
    public static final String GRID_LOW_GAS_STATUS = "Low Gas";
    public static final String GRID_HIGH_GAS_STATUS = "High Gas";
    public static final String GRID_INFO_FORMAT = "Position: [%d,%d] | Status: %s | Percentage: %.1f%% | Volume: %.2f CB.M";

    // Display
    public static final int ABOUT_CLOSE_BTN_WIDTH = 120;
    public static final int ABOUT_CLOSE_BTN_HEIGHT = 40;
    public static final int ABOUT_PIC_SIZE = 140;

    // สมาชิกทีม 1
    public static final String MEMBER1 = "Wachirawit Wongsaeng";
    public static final String ID1 = "67011212055";
    public static final String JOB1 = "Project Manager";
    public static final String IMG1 = "src/res/team/member_king.jpg";
    // สมาชิกทีม 2
    public static final String MEMBER2 = "Chindanai Phuhatsuan";
    public static final String ID2 = "67011212026";
    public static final String JOB2 = "Lead Developer";
    public static final String IMG2 = "src/res/team/member2.jpg";
    // สมาชิกทีม 3
    public static final String MEMBER3 = "Seranee Punapo";
    public static final String ID3 = "67011212143";
    public static final String JOB3 = "UI/UX Designer";
    public static final String IMG3 = "src/res/team/member_mint.jpg";

    // เกี่ยวกับ
    public static final String ABOUT_TITLE = "Gas distribution simulation system ";
    public static final String ABOUT_DISTRIBUTION = "By C++ TEAM";
}
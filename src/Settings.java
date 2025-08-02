import java.awt.*;

// คลาสConfig ตั้งค่ารวม
public class Settings {

    // ข้อมูลพื้นฐาน
    public static final int GRID_W = 22;
    public static final int GRID_H = 10;
    public static final double CELL_SIZE = 150.0;
    public static final double DEFAULT_FLUID = 2500.0;
    public static final double TOP_BASE = 200.0;
    public static final double GAS_LIMIT = 0.5;

    // หน้าต่าง
    public static final int WIN_W = 1350;
    public static final int WIN_H = 800;
    public static final int CELL_DRAW = 40;
    public static final Boolean WINDOW_MENU = false;

    // ฟอนต์
    public static final Font BIG_FONT = new Font("Tahoma", Font.BOLD, 26);
    public static final Font MID_FONT = new Font("Tahoma", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Tahoma", Font.PLAIN, 13);
    public static final Font TINY_FONT = new Font("Tahoma", Font.PLAIN, 11);

    // ไอคอน
    public static final String APP_ICON_PATH = "./src/res/icon.png";
    public static final int ICON_SIZE_SMALL = 96;
    public static final String ICON_BTN_CALC = "src/res/icons/synergy.png";
    public static final String ICON_BTN_LOAD = "src/res/icons/folder.png";
    public static final String ICON_BTN_CLEAR = "src/res/icons/clear-format.png";
    public static final String ICON_BTN_ABOUT = "";
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
    public static final String GRID_INFO = "Click the file or drag and drop the dept.txt file.";
    public static final String FILE_TITLE = "Place the dept.txt file here";
    public static final String FILE_SUB = "or click to select file";
    public static final String SELECT_FILE_TITLE = "Select file dept.txt";
    public static final String FILE_OF_TYPE = "File Type (*.txt)";

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
    public static final String MEMBER3= "Seranee Punapo";
    public static final String ID3 = "67011212143";
    public static final String JOB3 = "UI/UX Designer";
    public static final String IMG3 = "src/res/team/member_mint.jpg";

    // เกี่ยวกับ
    public static final String ABOUT_TITLE = "Gas distribution simulation system ";
    public static final String ABOUT_DISTRIBUTION = "By C++ TEAM";
}
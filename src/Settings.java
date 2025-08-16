import java.awt.Color;
import static java.awt.Frame.*;

// ตั้งค่า
public interface Settings {

    String DB = "src/db/settings.ini";
    // ธีม
    String CURRENT_THEME = "ARC_ORANGE";

    // ขนาดปุ่ม
    int BTN_WIDTH = 320;
    int BTN_HEIGHT_BIG = 50; // ปุ่มใหญ่
    int BTN_HEIGHT_MID = 42; // ปุ่มกลาง
    int INPUT_HEIGHT = 45;

    // ข้อมูล
    double CELL_SIZE = 150.0;
    double DEFAULT_FLUID = 2500.0;
    double TOP_BASE = 200.0;
    double GAS_LIMIT = 0.5;

    // สี
    Color COLOR_NO_GAS = new Color(231, 76, 60);
    Color COLOR_LOW_GAS = new Color(241, 196, 15);
    Color COLOR_HIGH_GAS = new Color(46, 204, 113);

    String FONT_NAME = "Tahoma";
    int FONT_SIZE_TITLE = 24;
    int FONT_SIZE_NORMAL = 13;
    int FONT_SIZE_INPUT = 15;
    int FONT_SIZE_BUTTON = 16;

    // หน้าต่าง
    int WIN_W = 1350;
    int WIN_H = 800;
    Boolean WINDOW_MENU = true;
    int FULL_S_W = MAXIMIZED_BOTH;

    // ไอคอน
    String APP_ICON_PATH = "./src/res/icon.png";
    int ICON_SIZE_SMALL = 96;
    String ICON_APP = "./src/res/icon2.png";

    // ส่วนหัว
    String APP_TITLE = "Gas Distribution System";
    String APP_VERSION = "v1.0.0";
    String BTN_ABOUT = "About";
    String BTN_CLOSE_ABOUT = "Close";
    String BTN_EXIT = "Exit";
    String EXIT_TITLE = "Exit";
    String EXIT_MSG = "Exit application?";

    // ส่วนควบคุม
    String INPUT_LABEL = "Liquid Depth (M)";
    String BTN_CALC = "Calculate";
    String BTN_LOAD = "Load File";
    String BTN_CLEAR = "Clear";

    // คำอธิบาย
    String LEGEND_TITLE = "Legend";
    String NO_GAS = "No Gas (0%)";
    String LOW_GAS = "Low Gas (<50%)";
    String HIGH_GAS = "High Gas (>50%)";

    // ผลลัพธ์
    String RESULT_TITLE = "Result:";
    String TOTAL_GAS = "Total Gas %s ";

    String STATUS_READY = "Ready";

    // สถานะ
    String STATUS_LOAD = "File: ";
    String STATUS_FAIL = "Load failed!";
    String STATUS_ERROR = "Error!";
    String STATUS_CHECK = "Invalid file format";

    // อัพโหลดไฟล์
    String GRID_TITLE = "Gas Distribution Grid";

    String FILE_TITLE = "open file here";
    String FILE_SUB = "or click to select";
    String SELECT_FILE_TITLE = "Select dept.txt";
    String FILE_OF_TYPE = "Text Files (*.txt)";

    // Grid Display
    int GRID_MAX_WIDTH = 800;
    int GRID_MAX_HEIGHT = 600;
    int GRID_MAX_CELL_SIZE = 50;
    int GRID_MIN_SIZE_WIDTH = 600;
    int GRID_MIN_SIZE_HEIGHT = 400;
    int GRID_MAX_SIZE_WIDTH = 1000;
    int GRID_MAX_SIZE_HEIGHT = 800;
    String GRID_HOVER_MESSAGE = "Hover for details";
    String GRID_NO_GAS_STATUS = "No Gas";
    String GRID_LOW_GAS_STATUS = "Low Gas";
    String GRID_HIGH_GAS_STATUS = "High Gas";

    // Display
    int ABOUT_CLOSE_BTN_WIDTH = 120;
    int ABOUT_CLOSE_BTN_HEIGHT = 40;
    int ABOUT_PIC_SIZE = 140;

    // สมาชิกทีม 1
    String MEMBER1 = "Wachirawit Wongsaeng";
    String ID1 = "67011212055";
    String JOB1 = "Project Manager";
    String IMG1 = "src/res/team/member_king.jpg";
    // สมาชิกทีม 2
    String MEMBER2 = "Chindanai Phuhatsuan";
    String ID2 = "67011212026";
    String JOB2 = "Lead Developer";
    String IMG2 = "src/res/team/member2.jpg";
    // สมาชิกทีม 3
    String MEMBER3 = "Seranee Punapo";
    String ID3 = "67011212143";
    String JOB3 = "UI/UX Designer";
    String IMG3 = "src/res/team/member_mint.jpg";

    // เกี่ยวกับ
    String ABOUT_TITLE = "Gas Distribution System";
    String ABOUT_DISTRIBUTION = "By C++ Team";

    String UNIT_METER = "Unit: M ";
    String UNIT_KM = "Unit: KM ";
    String UNIT_LABEL = "Unit:";
    String UNIT_KM_TEXT = "(KM)";
    String UNIT_M3 = "M³";
    String UNIT_KM3 = "KM³";
    String CONTROL_TITLE = "Control";
    String THEME_LIGHT = "Light";
    String THEME_DARK = "Dark";
    String THEME_STATUS_LIGHT = "Theme: Light";
    String THEME_STATUS_DARK = "Theme: Dark";
    String NO_DATA_MSG = "No data";
    String LOAD_FIRST_MSG = "Load file first";
    String INVALID_NUMBER_MSG = "Invalid number";
    String INVALID_INPUT_MSG = "Enter valid number";
    String FILE_CLEARED_MSG = "Cleared";
    String CALC_WITH_LEVEL = "Calc: ";
    String STATS_PREFIX = "Gas Area: ";
    String STATS_CELLS = " cells ";
    String OPEN_BRACKET = "(";
    String CLOSE_BRACKET = ")";
    String PERCENT_SIGN = "%";
    String SLASH = "/";

    int FONT_SIZE_SMALL = 8;
    int FONT_SIZE_TINY = 10;
    int FONT_SIZE_LARGE = 18;
    int FONT_SIZE_HUGE = 80;

    Color COLOR_GRAY = Color.GRAY;
    Color COLOR_LIGHT_GRAY = Color.LIGHT_GRAY;
    Color COLOR_BLACK = Color.BLACK;
    Color COLOR_WHITE = Color.WHITE;

    Color DARK_TEXT_PRIMARY = new Color(255, 255, 255);
    Color DARK_TEXT_SECONDARY = new Color(220, 220, 220);
    Color DARK_TEXT_MUTED = new Color(180, 180, 180);

    Color LIGHT_TEXT_PRIMARY = new Color(33, 33, 33);
    Color LIGHT_TEXT_SECONDARY = new Color(66, 66, 66);
    Color LIGHT_TEXT_MUTED = new Color(117, 117, 117);

    String BTN_SUMMARY = "Summary";
    String SUMMARY_TITLE = "Gas Distribution Overview";

    // SummaryView ข้อความ
    String SUMMARY_TOTAL_CELLS = "Total Cells";
    String SUMMARY_TOTAL_VOLUME = "Total Volume";

    String SUMMARY_CLOSE = "Close";
    String SUMMARY_CELLS_UNIT = " cells";
    String SUMMARY_VOLUME_UNIT = " CB.M";

    // Table headers
    String SUMMARY_HEADER_EMPTY = "";
    String SUMMARY_HEADER_TYPE = "Type";
    String SUMMARY_HEADER_CELLS = "Cells";
    String SUMMARY_HEADER_VOLUME = "Volume";
    String SUMMARY_HEADER_PERCENT = "% Cells";

    //Clear ข้อความ
    String TITLE_CLEAR = "Confirm Clear";
    String TEXT_CLEAR = "Will you clear this file?";
    String INFO_CHECK_BOX = "Do not show this dialog in the future";

    //detailCell
    String INFO =
            "Cell: [%d, %d]\n" +
                    "Top Horizon: %.1f \n" +
                    "Base Horizon: %.1f \n" +
                    "Volume: %s \n" +
                    "Gas: %.1f%%\n" ;
}
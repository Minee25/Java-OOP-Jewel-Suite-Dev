import javax.sound.sampled.*;
import java.io.File;

// เสียง
public class SoundPlayer {
    private static SoundPlayer soundInstance;
    private Clip clickSound;

    // ไม่ให้สร้างซ้ำ
    public static SoundPlayer getInstance() {
        if (soundInstance == null) {
            soundInstance = new SoundPlayer();
        }
        return soundInstance;
    }

    private SoundPlayer() {
        try {
            File soundFile = new File("src/res/sounds/click-buttons.wav");
            if (soundFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                clickSound = AudioSystem.getClip();
                clickSound.open(audioStream);
            }
        } catch (Exception e) {
        }
    }

    // เล่นเสียง
    public void playClickSound() {
        if (clickSound != null) {
            clickSound.setFramePosition(0);
            clickSound.start();
        }
    }
}
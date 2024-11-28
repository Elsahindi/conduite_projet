package ui;

import ui.authentification.WelcomeFrame;

import javax.swing.*;

public class WindowManager {

    private static WindowManager instance;

    private JFrame currentFrame = null;

    public JFrame getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(JFrame currentFrame) {
        if (this.currentFrame != null) {
            this.currentFrame.dispose();
        }

        this.currentFrame = currentFrame;
        this.currentFrame.setVisible(true);
    }

    public static WindowManager getInstance() {
        if (instance == null) {
            instance = new WindowManager();
        }

        return instance;
    }

    public static void main(String[] args) {
        WindowManager.getInstance().setCurrentFrame(new WelcomeFrame());
    }
}

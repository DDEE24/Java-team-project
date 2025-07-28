import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf()); // หรือ FlatDarkLaf
        } catch (Exception ex) {
            System.err.println("Failed to set Look and Feel");
        }

        SwingUtilities.invokeLater(() -> {
            ChatUI ui = new ChatUI();
            JFrame frame = new JFrame("Chat Bot");
            frame.setContentPane(ui.getBody());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
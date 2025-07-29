import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GeminiAPI.GeminiAPI;

public class ChatUI {
    private JTextField inputText;
    private JPanel Body;
    private JTextPane textPane;
    private JButton sendButton;

    public ChatUI() {
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = inputText.getText();
                if(text.isEmpty()) return;
                appendTitle("User : \n", Color.CYAN);
                appendWithColor("  " + text + "\n", Color.WHITE);
                inputText.setText("");

                // เรียก Gemini API ใน Thread แยก
                new Thread(() -> {
                    try {
                        String reply = GeminiAPI.ask(text);
                        SwingUtilities.invokeLater(() -> {
                            appendTitle("Gemini : \n", Color.MAGENTA);
                            appendWithColor("  " + reply + "\n\n", Color.LIGHT_GRAY);
                        });
                    } catch (Exception ex) {
                        SwingUtilities.invokeLater(() -> {
                            appendTitle("ERROR :\n", Color.RED);
                            appendWithColor("  " + ex.getMessage() + "\n", Color.RED);
                        });
                    }
                }).start();
            }
        });
    }
    // ส่ง JPanel หลักออกไปให้ Frame แสดง
    public JPanel getBody() {
        return Body;
    }

    // เพิ่มข้อความแบบหัวเรื่อง (ชื่อผู้พูด) + สี + ตัวหนา
    private void appendTitle(String text, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("Title Style", null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setBold(style, true);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    // เพิ่มข้อความธรรมดา + สี
    private void appendWithColor(String text, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("Text Style", null);
        StyleConstants.setForeground(style, color);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}

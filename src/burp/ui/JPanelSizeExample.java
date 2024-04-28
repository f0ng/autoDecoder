package burp.ui;

import javax.swing.*;
import java.awt.*;

public class JPanelSizeExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("JPanel Size Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(300, 200)); // 设置首选大小

            // 添加到 JFrame
            frame.add(panel);

            frame.pack(); // 根据首选大小自动调整 JFrame 的大小

            frame.setVisible(true);
        });
    }
}
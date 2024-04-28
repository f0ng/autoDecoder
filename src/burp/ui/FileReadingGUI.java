package burp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReadingGUI extends JFrame {
    private JButton selectButton;
    private JFileChooser fileChooser;

    public FileReadingGUI() {
        // 设置窗口标题
        setTitle("文件读取");

        // 创建选择按钮
        selectButton = new JButton("选择文件");

        // 创建文件选择对话框
        fileChooser = new JFileChooser();

        // 添加按钮点击事件监听器
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 显示文件选择对话框
                int result = fileChooser.showOpenDialog(FileReadingGUI.this);

                // 判断是否选择了文件
                if (result == JFileChooser.APPROVE_OPTION) {
                    // 获取选择的文件
                    File selectedFile = fileChooser.getSelectedFile();

                    // 读取文件内容并打印
                    try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // 设置布局管理器
        setLayout(new FlowLayout());

        // 添加选择按钮到窗口
        add(selectButton);

        // 设置窗口大小和关闭操作
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 显示窗口
        setVisible(true);
    }

    public static void main(String[] args) {
        // 创建并显示GUI界面
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FileReadingGUI();
            }
        });
    }
}
package burp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSavingGUI extends JFrame {
    private JButton saveButton;
    private JFileChooser fileChooser;

    public FileSavingGUI() {
        // 设置窗口标题
        setTitle("文件保存");

        // 创建保存按钮
        saveButton = new JButton("保存文件");

        // 创建文件选择对话框
        fileChooser = new JFileChooser();

        // 添加按钮点击事件监听器
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 设置文件选择对话框尺寸
                fileChooser.setPreferredSize(new Dimension(1000, 600));

                // 显示文件选择对话框
                int result = fileChooser.showSaveDialog(FileSavingGUI.this);

                // 判断是否选择了文件
                if (result == JFileChooser.APPROVE_OPTION) {
                    // 获取选择的文件
                    File selectedFile = fileChooser.getSelectedFile();

                    // 写入文件内容
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile))) {
                        writer.write("这是要保存的文件内容");
                        writer.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // 设置布局管理器
        setLayout(new FlowLayout());

        // 添加保存按钮到窗口
        add(saveButton);

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
                new FileSavingGUI();
            }
        });
    }
}
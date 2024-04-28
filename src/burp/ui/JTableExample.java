package burp.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Item 可选 Request header、Request body、Response header、Response body
 *
 * Type 可选 Regex、Literal(非正则)
 *
 */
public class JTableExample {
//    public static JTable table;
    public static JFrame frame;
//    public static DefaultTableModel tableModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Table Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // 列名
            String[] columnNames = {"Enabled","Host" ,"Item", "Match", "Replace", "Type", "Comment"};

            // 表格数据
            Object[][] data = {
                    {true,"127.0.0.1", "Item 1" ,"Match 1", "Replace 1", "Type 1", "Comment 1"},
                    {false,"127.0.0.1", "Item 2", "Match 2", "Replace 2", "Type 2", "Comment 2"},
                    {true,"127.0.0.1", "Item 3", "Match 3", "Replace 3", "Type 3", "Comment 3"}
            };

            // 创建默认的表格模型
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
            // 创建表格
            JTable table = createTable(tableModel);

            // 创建按钮面板
            JPanel buttonPanel = createButtonPanel(tableModel,table);

            // 创建主面板
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(buttonPanel, BorderLayout.WEST);
            mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

            // 将主面板添加到 JFrame
            frame.add(mainPanel);
            frame.pack();
            frame.setVisible(true);
        });
    }

    // 创建表格
    public static JTable createTable(DefaultTableModel tableModel) {


        // 创建 JTable
        JTable table = new JTable(tableModel);

        // 设置 Enabled 列的渲染器和编辑器
        table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(0).setCellEditor(table.getDefaultEditor(Boolean.class));

        return table;
    }

    // 创建按钮面板
    public static JPanel createButtonPanel(DefaultTableModel tableModel,JTable table) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // 创建 Add 按钮
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRow(null,tableModel);
            }
        });
        buttonPanel.add(addButton);

        // 创建 Edit 按钮
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editRow(table, null,tableModel);
            }
        });
        buttonPanel.add(editButton);

        // 创建 Remove 按钮
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeSelectedRows(table,tableModel);
            }
        });
        buttonPanel.add(removeButton);


        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 创建文件选择对话框
                JFileChooser fileChooser = new JFileChooser();

                // 显示文件选择对话框
                int result = fileChooser.showOpenDialog(null);

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
        buttonPanel.add(loadButton);

        return buttonPanel;
    }


    // 添加行
    private static void addRow(JFrame parentFrame,DefaultTableModel tableModel) {
        JComboBox JComboBoxitem = new JComboBox(); // 请求、响应部分选择
        DefaultComboBoxModel defaultComboBoxModelitem = new DefaultComboBoxModel();
        defaultComboBoxModelitem.addElement("Request header");
        defaultComboBoxModelitem.addElement("Request body");
        defaultComboBoxModelitem.addElement("Response header");
        defaultComboBoxModelitem.addElement("Response body");
        JComboBoxitem.setModel(defaultComboBoxModelitem);

        JComboBox JComboBoxtype = new JComboBox(); // 请求、响应部分选择
        DefaultComboBoxModel defaultComboBoxModeltype = new DefaultComboBoxModel();
        defaultComboBoxModeltype.addElement("Literal");
        defaultComboBoxModeltype.addElement("Regex");
        defaultComboBoxModeltype.addElement("Extract");
        JComboBoxtype.setModel(defaultComboBoxModeltype);

//        JTextField itemField = new JTextField();
        JTextField hostField = new JTextField(30);
        JTextField matchField = new JTextField(30);
        JTextField replaceField = new JTextField(30);
//        JTextField typeField = new JTextField();
        JTextField commentField = new JTextField(30);


        JPanel panel = new JPanel(new GridLayout(6, 2));
        JPanel panel1 = new JPanel(new BorderLayout());
        panel.add(new JLabel("Host:"));
        panel.add(hostField);
        panel1.add(new JLabel("Item:"), BorderLayout.CENTER);
        panel.add(panel1);
        panel.add(JComboBoxitem);


        panel.add(new JLabel("Match:"));
        panel.add(matchField);

        panel.add(new JLabel("Replace:"));
        panel.add(replaceField);

        panel.add(new JLabel("Type:"));
        panel.add(JComboBoxtype);

        panel.add(new JLabel("Comment:"));
        panel.add(commentField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Add Row",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String host = hostField.getText().toString();
            String item = JComboBoxitem.getSelectedItem().toString();
            String match = matchField.getText();
            String replace =replaceField.getText();
            String type = JComboBoxtype.getSelectedItem().toString();
            String comment =commentField.getText();

            Object[] rowData = {false ,host ,item ,match ,replace ,type , comment };
            tableModel.addRow(rowData);
        }
    }

    // 编辑行
    private static void editRow(JTable table, JFrame parentFrame,DefaultTableModel tableModel) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            System.out.println(tableModel.getValueAt(selectedRow, 0));
            // 从1下标开始，因为0的下标是checkBox类型，在编辑页面不需要读取
            String hostText = (String)tableModel.getValueAt(selectedRow, 1);
            String itemText = (String) tableModel.getValueAt(selectedRow, 2);
            String matchText = (String) tableModel.getValueAt(selectedRow, 3);
            String replaceText = (String) tableModel.getValueAt(selectedRow, 4);
            String typeText = (String) tableModel.getValueAt(selectedRow, 5);
            String commentText = (String) tableModel.getValueAt(selectedRow, 6);

            JComboBox JComboBoxitem = new JComboBox(); // 请求、响应部分选择
            DefaultComboBoxModel defaultComboBoxModelitem = new DefaultComboBoxModel();
            defaultComboBoxModelitem.addElement("Request header");
            defaultComboBoxModelitem.addElement("Request body");
            defaultComboBoxModelitem.addElement("Response header");
            defaultComboBoxModelitem.addElement("Response body");
            JComboBoxitem.setModel(defaultComboBoxModelitem);

            JComboBox JComboBoxtype = new JComboBox(); // 请求、响应部分选择
            DefaultComboBoxModel defaultComboBoxModeltype = new DefaultComboBoxModel();
            defaultComboBoxModeltype.addElement("Regex");
            defaultComboBoxModeltype.addElement("Literal");
            defaultComboBoxModeltype.addElement("Extract");
            JComboBoxtype.setModel(defaultComboBoxModeltype);

            JTextField hostField = new JTextField(30);
            JTextField matchField = new JTextField(30);
            JTextField replaceField = new JTextField(30);
//            JTextField typeField = new JTextField();
            JTextField commentField = new JTextField(30);

            JPanel panel = new JPanel(new GridLayout(6, 2));
            panel.add(new JLabel("Host:"));
            panel.add(hostField);
            panel.add(new JLabel("Item:"));
            panel.add(JComboBoxitem);
            panel.add(new JLabel("Match:"));
            panel.add(matchField);
            panel.add(new JLabel("Replace:"));
            panel.add(replaceField);
            panel.add(new JLabel("Type:"));
            panel.add(JComboBoxtype);
            panel.add(new JLabel("Comment:"));
            panel.add(commentField);

            hostField.setText(hostText);
            JComboBoxitem.setSelectedItem(itemText);
            matchField.setText(matchText);
            replaceField.setText(replaceText);
            JComboBoxtype.setSelectedItem(typeText);
            commentField.setText(commentText);

            int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Edit Row",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String hostFieldtext = hostField.getText().toString();
                String itemFieldtext = JComboBoxitem.getSelectedItem().toString();
                String matchFieldtext = matchField.getText();
                String replaceFieldtext = replaceField.getText();
                String typeFieldtext = JComboBoxtype.getSelectedItem().toString();
                String commentFieldtext = commentField.getText();

                // 从1下标开始，因为0的下标是checkBox类型，在编辑页面不需要读取
                tableModel.setValueAt(hostFieldtext, selectedRow, 1);
                tableModel.setValueAt(itemFieldtext, selectedRow, 2);
                tableModel.setValueAt(matchFieldtext, selectedRow, 3);
                tableModel.setValueAt(replaceFieldtext, selectedRow, 4);
                tableModel.setValueAt(typeFieldtext, selectedRow, 5);
                tableModel.setValueAt(commentFieldtext, selectedRow, 6);
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Please select a row to edit.",
                    "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 移除选中的行
    private static void removeSelectedRows(JTable table,DefaultTableModel tableModel) {
        int[] selectedRows = table.getSelectedRows();
        if (selectedRows.length > 0) {
            int result = JOptionPane.showConfirmDialog(table, "Are you sure you want to remove the selected rows?",
                    "Confirm Remove", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.YES_OPTION) {
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    tableModel.removeRow(selectedRows[i]);
                }
            }
        } else {
            JOptionPane.showMessageDialog(table, "Please select at least one row to remove.",
                    "No Rows Selected", JOptionPane.WARNING_MESSAGE);
        }
    }


}
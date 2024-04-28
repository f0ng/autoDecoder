package burp.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JTableWithCustomTextColumnExample {
    private static DefaultTableModel tableModel;
    private static JTable table;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 创建数据模型
            Object[][] data = {
                    { false,"Option 1", ""},
                    { true, "Option 2",""},
                    { false, "Option 3",""}
            };
            String[] columnNames = {"Option", "Selection", "Custom Text"};

            // 创建 JTable
            tableModel = new DefaultTableModel(data, columnNames) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class;
                    }
                    return super.getColumnClass(columnIndex);
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 1 || column == 2;
                }
            };

            JTable table = new JTable(tableModel);

            // 设置 Enabled 列的渲染器和编辑器
            table.getColumnModel().getColumn(0).setCellRenderer(table.getDefaultRenderer(Boolean.class));
            table.getColumnModel().getColumn(0).setCellEditor(table.getDefaultEditor(Boolean.class));

            // 创建 JFrame 并添加 JTable
            JFrame frame = new JFrame("JTable with Custom Text Column Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

            JButton addButton = new JButton("Add");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addRow(frame);
                }
            });
            buttonPanel.add(addButton);

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editRow(table, frame);
                }
            });
            buttonPanel.add(editButton);

            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeSelectedRows(table);
                }
            });
            buttonPanel.add(removeButton);

            frame.add(buttonPanel, BorderLayout.WEST);

            frame.pack();
            frame.setVisible(true);
        });
    }


    // 添加行
    private static void addRow(JFrame parentFrame) {
        JTextField optionField = new JTextField();
        JCheckBox selectionCheckBox = new JCheckBox("Selected");
        JTextField customTextField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Selection:"));
        panel.add(selectionCheckBox);
        panel.add(new JLabel("Option:"));
        panel.add(optionField);
        panel.add(new JLabel("Custom Text:"));
        panel.add(customTextField);

        int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Add Row",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String option = optionField.getText();
            boolean selection = selectionCheckBox.isSelected();
            String customText =customTextField.getText();

            Object[] rowData = {selection,option , customText};
            tableModel.addRow(rowData);
        }
    }

    // 编辑行
    private static void editRow(JTable table, JFrame parentFrame) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            boolean selection = (boolean) tableModel.getValueAt(selectedRow, 0);
            String option = (String) tableModel.getValueAt(selectedRow, 1);
            String customText = (String) tableModel.getValueAt(selectedRow, 2);

            JTextField optionField = new JTextField(option);
            JCheckBox selectionCheckBox = new JCheckBox("Selected", selection);
            JTextField customTextField = new JTextField(customText);

            JPanel panel = new JPanel(new GridLayout(3, 2));
            panel.add(new JLabel("Selection:"));
            panel.add(selectionCheckBox);
            panel.add(new JLabel("Option:"));
            panel.add(optionField);
            panel.add(new JLabel("Custom Text:"));
            panel.add(customTextField);

            int result = JOptionPane.showConfirmDialog(parentFrame, panel, "Edit Row",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String editedOption = optionField.getText();
                boolean editedSelection = selectionCheckBox.isSelected();
                String editedCustomText = customTextField.getText();

                tableModel.setValueAt(editedOption, selectedRow, 0);
                tableModel.setValueAt(editedSelection, selectedRow, 1);
                tableModel.setValueAt(editedCustomText, selectedRow, 2);
            }
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Please select a row to edit.",
                    "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    // 移除选中的行
    private static void removeSelectedRows(JTable table) {
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
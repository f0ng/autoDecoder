package burp;

import  com.autoDecoder.util.codeDecode.*;
//import  com.autoDecoder.util.codeEncode.encryptKeyivmode;
import static com.autoDecoder.util.codeDecode.decryptKeyivmode;
import static com.autoDecoder.util.codeEncode.encryptKeyivmode;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class IndexautoDecoder {
    private JButton button1;
    private JPanel rootJPanel;
    private JComboBox mChoiceBox5;
    private JComboBox mChoiceBox4;
    private JComboBox mChoiceBox3;
    private JComboBox mChoiceBox2;
    private JComboBox mChoiceBox1;
    private JTextField keytextField;
    private JTextField ivtextField;
    private JButton Button2;
    private JTextArea EncodedtextArea; // 密文
    private JTextArea DecodetextArea; // 明文
    public static JRadioButton RadioButton1;
    public static JRadioButton RadioButton2;
    private JTable table2;
    private JButton Button3;
    private JButton Button4;
    private JTable table1;
    private static JTextField textField3;
    private static JTextField textField4;
    private static JTextArea textArea1;
    private static JTextArea textArea2;

    private JButton Button11;
    private JButton Button22;
    private JRadioButton RadioButton;
    private static JRadioButton RadioButton3; // 是否对请求头加解密
    private JTextField headertextField; // 请求头加解密拼接




    //public File f = new File(BurpExtender.getPath() + "/autoDecoder.properties");

    public File f = new File( BurpExtender.getPath() );
    //File f;
    //if ( oss.toLowerCase().startsWith("win") ) {
    //
    //    f = new File("autoDecoder.properties");
    //
    //}else{

        //f = new File(BurpExtender.getPath() + "/autoDecoder.properties");

    //}

    public String[] EncodeParams = new String[100]; // 保存加密的一些参数

    public static String[] DecodeParams = new String[100]; // 保存加密的一些参数


    public IndexautoDecoder() {
        button1.addActionListener(new ActionListener() { // 解密按钮事件
            @Override
            public void actionPerformed(ActionEvent e) {

                String encodemode = (String) mChoiceBox1.getSelectedItem(); // AES / DES / DESede
                String ivmode = (String) mChoiceBox2.getSelectedItem(); // iv模式 ECB / CBC
                String paddingmode = (String) mChoiceBox3.getSelectedItem(); // 填充模式
                String sSrcmode = (String) mChoiceBox4.getSelectedItem(); // 密文编码
                String keyivmode = (String) mChoiceBox5.getSelectedItem(); // key iv编码

                String skey = keytextField.getText().trim(); // key 密钥
                String iv = ivtextField.getText().trim(); // iv
                String sSrc = EncodedtextArea.getText().trim(); // 密文

                try {
                    String dDes = decryptKeyivmode(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
                    DecodetextArea.setText(dDes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });

        Button2.addActionListener(new ActionListener() { // 加密按钮事件
            @Override
            public void actionPerformed(ActionEvent e) {
                String encodemode = (String) mChoiceBox1.getSelectedItem(); // AES / DES / DESede
                String ivmode = (String) mChoiceBox2.getSelectedItem(); // iv模式 ECB / CBC
                String paddingmode = (String) mChoiceBox3.getSelectedItem(); // 填充模式
                String sSrcmode = (String) mChoiceBox4.getSelectedItem(); // 密文编码
                String keyivmode = (String) mChoiceBox5.getSelectedItem(); // key iv编码

                String skey = keytextField.getText().trim(); // key 密钥
                String iv = ivtextField.getText().trim(); // iv
                String sSrc = DecodetextArea.getText().trim(); // 明文


                try {
                    String dDes = encryptKeyivmode(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
                    EncodedtextArea.setText(dDes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button3.addActionListener(new ActionListener() { // 加密编码 return EncodeParams
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames =
                        {"加密类型", "密文编码", "key/iv编码", "sessionkey", "iv"};
                Object[][] data = new Object[1][5];
                String encodemode = (String) mChoiceBox1.getSelectedItem(); // AES / DES / DESede
                String ivmode = (String) mChoiceBox2.getSelectedItem(); // iv模式 ECB / CBC
                String paddingmode = (String) mChoiceBox3.getSelectedItem(); // 填充模式
                String sSrcmode = (String) mChoiceBox4.getSelectedItem(); // 密文编码
                String keyivmode = (String) mChoiceBox5.getSelectedItem(); // key iv编码
                String skey = keytextField.getText().trim(); // key 密钥
                String iv = ivtextField.getText().trim(); // iv
                String sSrc = EncodedtextArea.getText().trim(); // 密文

                data[0][0] = encodemode + "/" + ivmode + "/" + paddingmode;
                data[0][1] = sSrcmode;
                data[0][2] = keyivmode;
                data[0][3] = skey;
                data[0][4] = iv;

                EncodeParams[0] = encodemode;
                EncodeParams[1] = ivmode;
                EncodeParams[2] = paddingmode;
                EncodeParams[3] = sSrcmode;
                EncodeParams[4] = keyivmode;
                EncodeParams[5] = skey;
                EncodeParams[6] = iv;
                EncodeParams[7] = sSrc;

                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                table1.setModel(model);
            }
        });

        Button4.addActionListener(new ActionListener() { // 解密编码  return DecodeParams
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames =
                        {"加密类型", "密文编码", "key/iv编码", "sessionkey", "iv"};
                Object[][] data = new Object[1][5];
                String encodemode = (String) mChoiceBox1.getSelectedItem(); // AES / DES / DESede
                String ivmode = (String) mChoiceBox2.getSelectedItem(); // iv模式 ECB / CBC
                String paddingmode = (String) mChoiceBox3.getSelectedItem(); // 填充模式
                String sSrcmode = (String) mChoiceBox4.getSelectedItem(); // 密文编码
                String keyivmode = (String) mChoiceBox5.getSelectedItem(); // key iv编码
                String skey = keytextField.getText().trim(); // key 密钥
                String iv = ivtextField.getText().trim(); // iv
                String sSrc = EncodedtextArea.getText().trim(); // 密文

                data[0][0] = encodemode + "/" + ivmode + "/" + paddingmode;
                data[0][1] = sSrcmode;
                data[0][2] = keyivmode;
                data[0][3] = skey;
                data[0][4] = iv;

                DecodeParams[0] = encodemode;
                DecodeParams[1] = ivmode;
                DecodeParams[2] = paddingmode;
                DecodeParams[3] = sSrcmode;
                DecodeParams[4] = keyivmode;
                DecodeParams[5] = skey;
                DecodeParams[6] = iv;
                DecodeParams[7] = sSrc;

                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                table2.setModel(model);
            }
        });
        Button11.addActionListener(new ActionListener() { // 保存配置按钮
            @Override
            public void actionPerformed(ActionEvent e) {
                String total = "";
                Boolean encodeApi = getRadioButton2State(); // 读取是否通过接口加密

                Boolean encodeCode = getRadioButton1State(); // 读取是否通过加解密算法加密

                Boolean encodeIf = getRadioButtonState(); // 读取是否启用插件

                String encodeMethod;

                if (encodeApi)
                    encodeMethod = "1";
                else if (encodeCode)
                    encodeMethod = "2";
                else
                    encodeMethod = "0";

                total = total + "encodemethod=" + encodeMethod + "\n";

                total = total + "encodeapi=" + textField3.getText().trim() + "\n";
                total = total + "decodeapi=" + textField4.getText().trim() + "\n";
                Boolean encodeHeaders = getRadioButton3State();
                String encodeHeaders_str;
                if (encodeHeaders)
                    encodeHeaders_str = "1";
                else
                    encodeHeaders_str = "0";
                total = total + "encodeheaders=" + encodeHeaders_str + "\n";

                total = total + "encodemode=" + (String) mChoiceBox1.getSelectedItem() + "\n";
                total = total + "ivmode=" + (String) mChoiceBox2.getSelectedItem() + "\n";
                total = total + "paddingmode=" + (String) mChoiceBox3.getSelectedItem() + "\n";
                total = total + "sSrcmode=" + (String) mChoiceBox4.getSelectedItem() + "\n";
                total = total + "keyivmode=" + (String) mChoiceBox5.getSelectedItem() + "\n";
                total = total + "skey=" + keytextField.getText().trim() + "\n";
                total = total + "iv=" + ivtextField.getText().trim() + "\n";
                String[] hosts_lists = textArea1.getText().split("\n");
                StringBuilder hosts_total = new StringBuilder();
                for (String host: hosts_lists)
                    hosts_total.append(host).append(",");
                hosts_total = new StringBuilder(hosts_total.substring(0, hosts_total.length() - 1));
                total = total + "hosts=" + hosts_total + "\n";

                String[] words_lists = textArea2.getText().split("\n");
                StringBuilder words_total = new StringBuilder();
                for (String word: words_lists)
                    words_total.append(word).append(",");
                words_total = new StringBuilder(words_total.substring(0, words_total.length() - 1));
                total = total + "words=" + words_total + "\n";

                try (FileWriter fileWriter = new FileWriter(f.getAbsolutePath())) {
                    fileWriter.append(total);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }

            }
        });
        Button22.addActionListener(new ActionListener() { // 恢复默认配置按钮
            @Override
            public void actionPerformed(ActionEvent e) {
                mChoiceBox1.setSelectedItem("DES"); // AES / DES / DESede
                mChoiceBox2.setSelectedItem("CBC"); // iv模式 ECB / CBC
                mChoiceBox3.setSelectedItem("PKCS5Padding"); // 填充模式
                mChoiceBox4.setSelectedItem("Base64"); // 密文编码
                mChoiceBox5.setSelectedItem("null");
                keytextField.setText("f0ngtest"); // key 密钥
                ivtextField.setText("f0ngf0ng"); // iv

                Object[][] data3 = new Object[1][5];

                data3[0][0] = mChoiceBox1.getSelectedItem() + "/" + mChoiceBox2.getSelectedItem() + "/" + mChoiceBox3.getSelectedItem();
                data3[0][1] = mChoiceBox4.getSelectedItem();
                data3[0][2] = mChoiceBox5.getSelectedItem();
                data3[0][3] = keytextField.getText().trim();
                data3[0][4] = ivtextField.getText().trim();
                String[] columnNames2 =
                        {"加密类型", "密文编码", "key/iv编码","sessionkey","iv"};
                DefaultTableModel model3 = new DefaultTableModel(data3, columnNames2);
                table1.setModel(model3);
                table2.setModel(model3);

            }
        });
    }


    public static String[] getEncryptHosts() { // 获取使用加解密的域名
        String[] host_lists;
        if (getRadioButton1State() || getRadioButton2State()) { // 通过加解密算法进行加解密才会去查看需要加解密的域名
            host_lists = textArea1.getText().split("\n");
            return host_lists;
        } else {
            return null;
        }
    }

    public String FileGetValue(File f, String key) { // 读取properties文件，根据key取出value
        BufferedReader reader = null;
        String value = "";
        StringBuffer sbf = new StringBuffer();
        String output = "";
        try {
            reader = new BufferedReader(new FileReader(f));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr + '\n');
            }
            reader.close();
            output = sbf.toString();
        } catch (IOException e) {
        }
        String[] properties_lists = output.split("\n");
        for (String str : properties_lists) {
            String[] str_lists = str.split("=", 2);
            if (str_lists[0].equals(key))
                value = str_lists[1];
        }
        return value.trim();
    }

    public static boolean getRadioButton1State() { // 获取是否通过加解密算法进行加解密
        return RadioButton1.isSelected();

    }

    public static boolean getRadioButton2State() { // 获取是否通过接口进行加解密
        return RadioButton2.isSelected();
    }

    public static boolean getRadioButton3State() { // 获取是否对header进行处理
        return RadioButton3.isSelected();
    }

    public boolean getRadioButtonState() { // 获取是否通过接口进行加解密
        return RadioButton.isSelected();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootJPanel = new JPanel();
        rootJPanel.setLayout(new GridLayoutManager(4, 8, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(18, 15, new Insets(0, 0, 0, 0), -1, -1));
        rootJPanel.add(panel1, new GridConstraints(0, 0, 4, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("加密方式");
        panel1.add(label1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("向量模式");
        panel1.add(label2, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(164, 16), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("填充模式");
        panel1.add(label3, new GridConstraints(4, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mChoiceBox3 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("PKCS5Padding");
        defaultComboBoxModel1.addElement("NoPadding");
        mChoiceBox3.setModel(defaultComboBoxModel1);
        panel1.add(mChoiceBox3, new GridConstraints(4, 8, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mChoiceBox2 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("CBC");
        defaultComboBoxModel2.addElement("ECB");
        defaultComboBoxModel2.addElement("CFB");
        mChoiceBox2.setModel(defaultComboBoxModel2);
        panel1.add(mChoiceBox2, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mChoiceBox1 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("AES");
        defaultComboBoxModel3.addElement("DES");
        defaultComboBoxModel3.addElement("DESede");
        mChoiceBox1.setModel(defaultComboBoxModel3);
        panel1.add(mChoiceBox1, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Ivparamter");
        panel1.add(label4, new GridConstraints(5, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("密文");
        panel1.add(label5, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(164, 16), null, 0, false));
        button1 = new JButton();
        button1.setText("解密>");
        panel1.add(button1, new GridConstraints(6, 4, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Button2 = new JButton();
        Button2.setText("<加密");
        panel1.add(Button2, new GridConstraints(6, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        EncodedtextArea = new JTextArea();
        EncodedtextArea.setLineWrap(true);
                JScrollPane js=new JScrollPane(EncodedtextArea);
                js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel1.add(js, new GridConstraints(7, 0, 5, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 50), null, 0, false));
        DecodetextArea = new JTextArea();
        DecodetextArea.setLineWrap(true);
        DecodetextArea.setText("");
                JScrollPane js2=new JScrollPane(DecodetextArea);
                js2.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                js2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel1.add(js2, new GridConstraints(7, 8, 5, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("密文编码");
        panel1.add(label6, new GridConstraints(4, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keytextField = new JTextField();
        panel1.add(keytextField, new GridConstraints(5, 1, 1, 7, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ivtextField = new JTextField();
        panel1.add(ivtextField, new GridConstraints(5, 9, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("密钥sessionkey");
        panel1.add(label7, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RadioButton2 = new JRadioButton();
        RadioButton2.setText("使用接口进行加解密");
        panel1.add(RadioButton2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("解密接口");
        panel1.add(label8, new GridConstraints(2, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField4 = new JTextField();
        panel1.add(textField4, new GridConstraints(2, 9, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("加密接口");
        panel1.add(label9, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField3 = new JTextField();
        textField3.setText("");
        panel1.add(textField3, new GridConstraints(2, 2, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        Button3 = new JButton();
        Button3.setText("添加为请求包加/解密方式");
        panel1.add(Button3, new GridConstraints(12, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Button4 = new JButton();
        Button4.setText("添加为响应包解密方式");
        panel1.add(Button4, new GridConstraints(12, 9, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("明文");
        panel1.add(label10, new GridConstraints(6, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        RadioButton1 = new JRadioButton();
        RadioButton1.setHideActionText(false);
        RadioButton1.setSelected(true);
        RadioButton1.setText("使用加解密算法进行加解密");
        panel1.add(RadioButton1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        String[] columnNames =
        { "加密类型", "密文编码", "key/iv编码","sessionkey","iv"};
        Object[][] data = new Object[0][5];
        DefaultTableModel model=new DefaultTableModel(data, columnNames);
        table2 = new JTable(model);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        panel1.add(scrollPane2, new GridConstraints(14, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("请求包加/解密方式");
        panel1.add(label11, new GridConstraints(13, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("响应包解密方式");
        panel1.add(label12, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        textArea2 = new JTextArea();
        JScrollPane js4=new JScrollPane(textArea2);
        js4.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js4.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textArea2.setLineWrap(true);
        textArea2.setText("\"\n:");
        panel1.add(js4, new GridConstraints(14, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(100, 20), null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("设置明文关键字(出现则不进行加密)");
        panel1.add(label22, new GridConstraints(14, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));


        Object[][] data2 = new Object[0][5];
        DefaultTableModel model2=new DefaultTableModel(data2, columnNames);
        table1 = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table1);
        panel1.add(scrollPane, new GridConstraints(13, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(17, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        Button11 = new JButton();
        Button11.setText("保存配置");
        panel1.add(Button11, new GridConstraints(15, 4, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Button22 = new JButton();
        Button22.setText("恢复默认配置");
        panel1.add(Button22, new GridConstraints(16, 4, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea1 = new JTextArea();
        JScrollPane js3=new JScrollPane(textArea1);
                js2.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                js2.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textArea1.setLineWrap(true);
        textArea1.setText("");
        panel1.add(js3, new GridConstraints(13, 9, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(100, 20), null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("设置需要加解密的域名");
        panel1.add(label13, new GridConstraints(13, 8, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mChoiceBox4 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("null");
        defaultComboBoxModel4.addElement("Base64");
        defaultComboBoxModel4.addElement("Hex");
        mChoiceBox4.setModel(defaultComboBoxModel4);
        panel1.add(mChoiceBox4, new GridConstraints(4, 10, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("key/iv编码");
        panel1.add(label14, new GridConstraints(4, 11, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mChoiceBox5 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("null");
        defaultComboBoxModel5.addElement("Base64");
        defaultComboBoxModel5.addElement("Hex");
        mChoiceBox5.setModel(defaultComboBoxModel5);
        panel1.add(mChoiceBox5, new GridConstraints(4, 12, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(5, 14, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        RadioButton = new JRadioButton();
        RadioButton.setText("不对请求进行加解密");
        panel1.add(RadioButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
                ButtonGroup bp = new ButtonGroup();
                bp.add(RadioButton2);
                bp.add(RadioButton1);
                bp.add(RadioButton);

        RadioButton3 = new JRadioButton();
        RadioButton3.setText("对请求头进行加密");
        panel1.add(RadioButton3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));


                if (!f.exists())
                {
                try {
                f.createNewFile();
                try (FileWriter fileWriter = new FileWriter(f)) {
                fileWriter.append("encodemethod=0");
                fileWriter.append("\n");
                fileWriter.append("encodeapi=http://127.0.0.1:8888/encode");
                fileWriter.append("\n");
                fileWriter.append("decodeapi=http://127.0.0.1:8888/decode");
                fileWriter.append("\n");
                fileWriter.append("encodeheaders=2");
                fileWriter.append("\n");
                fileWriter.append("encodemode=DES");
                fileWriter.append("\n");
                fileWriter.append("ivmode=CBC");
                fileWriter.append("\n");
                fileWriter.append("paddingmode=PKCS5Padding");
                fileWriter.append("\n");
                fileWriter.append("sSrcmode=Base64");
                fileWriter.append("\n");
                fileWriter.append("keyivmode=null");
                fileWriter.append("\n");
                fileWriter.append("skey=f0ngtest");
                fileWriter.append("\n");
                fileWriter.append("iv=f0ngf0ng");
                fileWriter.append("\n");
                fileWriter.append("hosts=10.211.55.4");
                fileWriter.append("\n");
                fileWriter.append("words=\",:");
                fileWriter.flush();
                } catch (IOException e) { e.printStackTrace(); }
                } catch (IOException e) { e.printStackTrace(); }
                }

                String[] columnNames2 =
                {"加密类型", "密文编码", "key/iv编码","sessionkey","iv"};
                Object[][] data3 = new Object[1][5];
                String encodemode = FileGetValue(f,"encodemode"); // AES / DES / DESede
                String ivmode = FileGetValue(f,"ivmode"); // iv模式 ECB / CBC
                String paddingmode = FileGetValue(f,"paddingmode"); // 填充模式
                String sSrcmode = FileGetValue(f,"sSrcmode"); // 密文编码
                String keyivmode = FileGetValue(f,"keyivmode"); // key iv编码
                String skey = FileGetValue(f,"skey");; // key 密钥
                String iv = FileGetValue(f,"iv");; // iv

                String encodemethod = FileGetValue(f,"encodemethod");; // encodemethod
                if (encodemethod == "0"){ // 不启用插件
                    RadioButton.setSelected(true);
                }else if(encodemethod == "1"){ // 接口加解密
                    RadioButton2.setSelected(true);
                }else{  // 加解密算法进行加解密
                    RadioButton1.setSelected(true);
                }


                String[] hosts = FileGetValue(f,"hosts").split(",");
                String t = "";
                for (String host : hosts)
                    t = t + host +'\n';
                textArea1.setText(t);

                String[] words = FileGetValue(f,"words").split(",");
                String tt = "";
                for (String word : words)
                    tt = tt + word +'\n';
                textArea2.setText(tt);

                data3[0][0] = encodemode + "/" + ivmode + "/" + paddingmode;
                data3[0][1] = sSrcmode;
                data3[0][2] = keyivmode;
                data3[0][3] = skey;
                data3[0][4] = iv;

                DecodeParams[0] = encodemode;
                DecodeParams[1] = ivmode;
                DecodeParams[2] = paddingmode;
                DecodeParams[3] = sSrcmode;
                DecodeParams[4] = keyivmode;
                DecodeParams[5] = skey;
                DecodeParams[6] = iv;

                EncodeParams[0] = encodemode;
                EncodeParams[1] = ivmode;
                EncodeParams[2] = paddingmode;
                EncodeParams[3] = sSrcmode;
                EncodeParams[4] = keyivmode;
                EncodeParams[5] = skey;
                EncodeParams[6] = iv;

                DefaultTableModel model3 = new DefaultTableModel(data3, columnNames2);
                table1.setModel(model3);
                table2.setModel(model3);

                mChoiceBox1.setSelectedItem(encodemode); // AES / DES / DESede
                mChoiceBox2.setSelectedItem(ivmode); // iv模式 ECB / CBC
                mChoiceBox3.setSelectedItem(paddingmode); // 填充模式
                mChoiceBox4.setSelectedItem(sSrcmode); // 密文编码
                mChoiceBox5.setSelectedItem(keyivmode);
                keytextField.setText(skey); // key 密钥
                ivtextField.setText(iv); // iv

                String encodeapi = FileGetValue(f,"encodeapi");; // encodeapi
                String decodeapi = FileGetValue(f,"decodeapi");; // decodeapi

                textField3.setText(encodeapi);
                textField4.setText(decodeapi);

    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootJPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    // 删除数组里为null的值
    public static String[] deleteArrayNull(String[] string) {
        String[] array = string;

        List<String> list = new ArrayList<>(array.length);

        try {
            for (String str : array) {
                list.add(str.trim());
            }
        } catch (NullPointerException e) {
            ;
        }
        // 删除空的值
        while (list.remove(null)) ;
        while (list.remove("")) ;

        // 将list 转换成数组
        String[] list2 = list.toArray(new String[list.size()]);
        // 返回删除空值后的数组
        return list2;
    }
    public static String gettextArea2() {
        return textArea2.getText();
    }

    public static String getEncodeApi() {
        return textField3.getText().trim();
    }

    public static String getDecodeApi() {
        return textField4.getText().trim();
    }

    public String[] getEncodeParams() {
        return deleteArrayNull(EncodeParams);
    }

    public static String[] getDecodeParams() {
        return deleteArrayNull(DecodeParams);
    }
}

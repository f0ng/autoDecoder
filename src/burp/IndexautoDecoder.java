package burp;

import Utils.TestDecode;
import Utils.TestEncode;
import static Utils.Match.MatchReg;
import static burp.BurpExtender.stdout;
import static com.autoDecoder.util.codeDecode.decryptKeyivmode;
import static com.autoDecoder.util.codeEncode.encryptKeyivmode;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class IndexautoDecoder {


    public JTabbedPane mainPanel; // 修改为可选面板，主面板
    private JPanel rootJPanel; // 面板一，设置，模块与加解密域名、关键字
    private JPanel rootJPanel2; // 面板二，自带算法加解密
    private JPanel rootJPanel3; // 面板三，接口加解密

    public JLabel label7;
    public JLabel label4;

    private JComboBox mChoiceBox5;
    private JComboBox mChoiceBox4;
    private JComboBox mChoiceBox3;
    private JComboBox mChoiceBox2;
    private JComboBox mChoiceBox1;
    private JTextArea keytextField;
    private JTextArea ivtextField;

    public static JTextField regtextField;
    public static JTextField regtextField_resp;

    private JButton button1;
    private JButton Button2;
    private JButton buttontiqu;
    private JButton buttontiqu_resp;

    private JTextArea EncodedtextArea; // 密文
    private JTextArea DecodetextArea; // 明文

    private JTextArea InputtextArea; // 密文
    private JTextArea OutputtextArea; // 明文

    private JTextArea InputtextArea_resp; // 密文
    private JTextArea OutputtextArea_resp; // 明文


    public static JRadioButton RadioButton1;
    public static JRadioButton RadioButton11;
    public static JRadioButton RadioButton12;
    public static JRadioButton RadioButton2;
    private JTable table2; // 请求包加解密方式
    private JButton Button3;
    private JButton Button4;
    private JTable table1; // 响应包加解密方式
    private static JTextField textField3;
    private static JTextField textField4;
    private static JTextArea textArea1;
    private static JTextArea textArea2;
    private static JTextArea textArea3;

    private static JTextArea textDatatestArea;
    private static JTextArea textDatatestAreaoutput;

    private JButton Button11;
    private JButton Button111;
    private JButton Button22;
    private JRadioButton RadioButton;
    private static JRadioButton RadioButton1111;
    private static JRadioButton RadioButton2222;
    private static JRadioButton RadioButton3333;
    private static JRadioButton RadioButton4444;
    private static JRadioButton RadioButton3; // 是否对请求头加解密
    private static JRadioButton RadioButton4; // 请求包、响应包是否不同加解密

    private static JRadioButton RadioButton5; // 请求包使用base64编码
    private static JRadioButton RadioButton6; // 响应包使用base64编码


    private static JButton testEncodejb;
    private static JButton testDecodejb;

    public static JRadioButton RadioButtontestEncode; // 接口调试加密按钮
    public static JRadioButton RadioButtontestDecode; // 接口调试解密按钮
    public static JRadioButton RadioButtontestHeader; // 接口调试请求头加密按钮
    public static JRadioButton RadioButtontestdifferent; // 接口调试请求、相应不同按钮




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

//    public String[] EncodeParams = new String[100]; // 保存加密的一些参数

    public static String[] DecodeParams = new String[100]; // 保存解密的一些参数

//    public String[] RespEncodeParams = new String[100]; // 保存加密的一些参数

    public static String[] RespDecodeParams = new String[100]; // 保存加密的一些参数




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
                String sSrc = EncodedtextArea.getText(); // 密文

//                stdout.println(encodemode);
//                stdout.println(ivmode);
//                stdout.println(paddingmode);
//                stdout.println(sSrcmode);
//                stdout.println(keyivmode);
//                stdout.println(skey);
//                stdout.println(iv);
//                stdout.println(sSrc);

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
                String sSrc = DecodetextArea.getText(); // 明文


                try {
                    String dDes = encryptKeyivmode(sSrc, skey, iv, encodemode, ivmode, paddingmode, sSrcmode, keyivmode);
                    EncodedtextArea.setText(dDes);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button4.addActionListener(new ActionListener() { // 添加为响应包解密方式
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

                RespDecodeParams[0] = encodemode;
                RespDecodeParams[1] = ivmode;
                RespDecodeParams[2] = paddingmode;
                RespDecodeParams[3] = sSrcmode;
                RespDecodeParams[4] = keyivmode;
                RespDecodeParams[5] = skey;
                RespDecodeParams[6] = iv;
                RespDecodeParams[7] = sSrc;

                DefaultTableModel model = new DefaultTableModel(data, columnNames);
                table1.setModel(model);
            }
        });

        Button3.addActionListener(new ActionListener() { // 添加为请求包加解密方式
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

                Boolean encodeHeaders = getRadioButton3State(); // 读取是否启用

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

                String encodeHeaders_str;
                if (encodeHeaders)
                    encodeHeaders_str = "1";
                else
                    encodeHeaders_str = "0";
                total = total + "encodeheaders=" + encodeHeaders_str + "\n";


                total = total + "reqencodemode=" + table2.getValueAt(0,0).toString().split("/")[0] + "\n";
                total = total + "reqivmode=" + table2.getValueAt(0,0).toString().split("/")[1] + "\n";
                total = total + "reqpaddingmode=" + table2.getValueAt(0,0).toString().split("/")[2] + "\n";
                total = total + "reqsSrcmode=" + table2.getValueAt(0,1).toString() + "\n";
                total = total + "reqkeyivmode=" + table2.getValueAt(0,2).toString() + "\n";
                total = total + "reqskey=" + table2.getValueAt(0,3).toString() + "\n";
                total = total + "reqiv=" + table2.getValueAt(0,4).toString() + "\n";



                total = total + "respencodemode=" + table1.getValueAt(0,0).toString().split("/")[0] + "\n";
                total = total + "respivmode=" + table1.getValueAt(0,0).toString().split("/")[1] + "\n";
                total = total + "resppaddingmode=" + table1.getValueAt(0,0).toString().split("/")[2] + "\n";
                total = total + "respsSrcmode=" + table1.getValueAt(0,1).toString() + "\n";
                total = total + "respkeyivmode=" + table1.getValueAt(0,2).toString() + "\n";
                total = total + "respskey=" + table1.getValueAt(0,3).toString() + "\n";
                total = total + "respiv=" + table1.getValueAt(0,4).toString() + "\n";




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

                String[] not_words_lists = textArea3.getText().split("\n");
                StringBuilder not_words_total = new StringBuilder();
                for (String not_word: not_words_lists)
                    not_words_total.append(not_word).append(",");
                not_words_total = new StringBuilder(not_words_total.substring(0, not_words_total.length() - 1));
                total = total + "notwords=" + not_words_total ;

                try (FileWriter fileWriter = new FileWriter(f.getAbsolutePath())) {
                    fileWriter.append(total);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Save success！" , "Save", JOptionPane.INFORMATION_MESSAGE);
            }
        });



        Button111.addActionListener(new ActionListener() { // 保存配置按钮
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

                total = total + "reqencodemode=" + table2.getValueAt(0,0).toString().split("/")[0] + "\n";
                total = total + "reqivmode=" + table2.getValueAt(0,0).toString().split("/")[1] + "\n";
                total = total + "reqpaddingmode=" + table2.getValueAt(0,0).toString().split("/")[2] + "\n";
                total = total + "reqsSrcmode=" + table2.getValueAt(0,1).toString() + "\n";
                total = total + "reqkeyivmode=" + table2.getValueAt(0,2).toString() + "\n";
                total = total + "reqskey=" + table2.getValueAt(0,3).toString() + "\n";
                total = total + "reqiv=" + table2.getValueAt(0,4).toString() + "\n";



                total = total + "respencodemode=" + table1.getValueAt(0,0).toString().split("/")[0] + "\n";
                total = total + "respivmode=" + table1.getValueAt(0,0).toString().split("/")[1] + "\n";
                total = total + "resppaddingmode=" + table1.getValueAt(0,0).toString().split("/")[2] + "\n";
                total = total + "respsSrcmode=" + table1.getValueAt(0,1).toString() + "\n";
                total = total + "respkeyivmode=" + table1.getValueAt(0,2).toString() + "\n";
                total = total + "respskey=" + table1.getValueAt(0,3).toString() + "\n";
                total = total + "respiv=" + table1.getValueAt(0,4).toString() + "\n";


                DecodeParams[0] = table2.getValueAt(0,0).toString().split("/")[0];
                DecodeParams[1] = table2.getValueAt(0,0).toString().split("/")[1];
                DecodeParams[2] = table2.getValueAt(0,0).toString().split("/")[2];
                DecodeParams[3] = table2.getValueAt(0,1).toString();
                DecodeParams[4] = table2.getValueAt(0,2).toString();
                DecodeParams[5] = table2.getValueAt(0,3).toString();
                DecodeParams[6] = table2.getValueAt(0,4).toString();

                RespDecodeParams[0] = table1.getValueAt(0,0).toString().split("/")[0];
                RespDecodeParams[1] = table1.getValueAt(0,0).toString().split("/")[1];
                RespDecodeParams[2] = table1.getValueAt(0,0).toString().split("/")[2];
                RespDecodeParams[3] = table1.getValueAt(0,1).toString();
                RespDecodeParams[4] = table1.getValueAt(0,2).toString();
                RespDecodeParams[5] = table1.getValueAt(0,3).toString();
                RespDecodeParams[6] = table1.getValueAt(0,4).toString();

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

                String[] not_words_lists = textArea3.getText().split("\n");
                StringBuilder not_words_total = new StringBuilder();
                for (String not_word: not_words_lists)
                    not_words_total.append(not_word).append(",");
                not_words_total = new StringBuilder(not_words_total.substring(0, not_words_total.length() - 1));
                total = total + "notwords=" + not_words_total ;

                try (FileWriter fileWriter = new FileWriter(f.getAbsolutePath())) {
                    fileWriter.append(total);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Save success！" , "Save", JOptionPane.INFORMATION_MESSAGE);
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

        buttontiqu.addActionListener(new ActionListener() { // 解密按钮事件
            @Override
            public void actionPerformed(ActionEvent e) {
                String InputtextAreatext = InputtextArea.getText();
                String reg = regtextField.getText();
                OutputtextArea.setText(MatchReg(InputtextAreatext, reg));
            }
        });

        buttontiqu_resp.addActionListener(new ActionListener() { // 解密按钮事件
            @Override
            public void actionPerformed(ActionEvent e) {
                String InputtextAreatext_resp = InputtextArea_resp.getText();
                String reg_resp = regtextField_resp.getText();
                OutputtextArea_resp.setText(MatchReg(InputtextAreatext_resp, reg_resp));
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
    } // 使用接口加解密

    public static boolean getRadioButton3State() { // 获取是否对header进行处理
        return RadioButton3.isSelected();
    }  // 是否对请求头加解密

    public static boolean getRadioButtontestHeaderState() { // 获取接口是否调试请求头加密按钮
        return RadioButtontestHeader.isSelected();
    }// 接口调试请求头加密按钮


    public static boolean getRadioButton4State() { // 请求包、响应包的解密方式是否分开
        return RadioButton4.isSelected();
    }

    public static boolean getRadioButton5State() { // 请求包、响应包的解密方式是否分开
        return RadioButton5.isSelected();
    } // 请求包base64编码

    public static boolean getRadioButton6State() { // 请求包、响应包的解密方式是否分开
        return RadioButton6.isSelected();
    } // 响应包base64编码

    // 请求包、响应包的解密方式是否分开
    public static boolean getRadioButtontestdifferentState() { // 获取是否对header进行处理
        return RadioButtontestdifferent.isSelected();
    }

    public boolean getRadioButtonState() { // 获取是否不进行加解密
        return RadioButton.isSelected();
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        try {
            $$$setupUI$$$();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // todo 修改ui为拖拉的
    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() throws IOException {
        mainPanel = new JTabbedPane();

        rootJPanel = new JPanel();
        rootJPanel2 = new JPanel();
        rootJPanel3 = new JPanel();
        rootJPanel.setLayout(new GridLayoutManager(4, 8, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        final JPanel panel2 = new JPanel();
        final JPanel panel3 = new JPanel();

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setAlignmentX(0.0f);
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));

        rootJPanel.add(panel1, new GridConstraints(0, 0, 4, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setAlignmentX(0.0f);
        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));

        rootJPanel2.add(panel2, new GridConstraints(0, 0, 4, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        panel3.setAlignmentX(0.0f);
        panel3.setBorder(new EmptyBorder(10, 10, 10, 10));

        rootJPanel3.add(panel3, new GridConstraints(0, 0, 4, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        JPanel panel1_1 = new JPanel(); // 模块控制
        panel1_1.setBorder(BorderFactory.createTitledBorder("模块控制")); // 模块控制
        panel1_1.setLayout(new BoxLayout(panel1_1, BoxLayout.X_AXIS));

        JPanel panel1_2 = new JPanel(); // 加解密选项开关
        panel1_2.setBorder(BorderFactory.createTitledBorder("加解密选项")); // 模块控制
        panel1_2.setLayout(new BoxLayout(panel1_2, BoxLayout.X_AXIS));

        JPanel panel1_3 = new JPanel(); // 加解密选项开关
        panel1_3.setBorder(BorderFactory.createTitledBorder("加解密设置")); // 模块控制
        panel1_3.setLayout(new BoxLayout(panel1_3, BoxLayout.X_AXIS));

        RadioButton1111 = new JRadioButton();
        RadioButton1111.setText("Proxy");
        RadioButton1111.setSelected(true);


        RadioButton2222 = new JRadioButton();
        RadioButton2222.setText("Repeater");
        RadioButton2222.setSelected(true);


        RadioButton3333 = new JRadioButton();
        RadioButton3333.setText("Intruder");
        RadioButton3333.setSelected(true);

        RadioButton4444 = new JRadioButton();
        RadioButton4444.setText("Extender");

        GroupLayout layout1_1 = new GroupLayout(panel1_1);
        panel1_1.setLayout(layout1_1);
        layout1_1.setAutoCreateGaps(true);
        layout1_1.setAutoCreateContainerGaps(true);

        layout1_1.setHorizontalGroup(layout1_1.createSequentialGroup()
                .addGroup(layout1_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton1111))

                .addGroup(layout1_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton2222))

                .addGroup(layout1_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton3333))

                .addGroup(layout1_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton4444))
        );

        layout1_1.setVerticalGroup(layout1_1.createSequentialGroup()
                .addGroup(layout1_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(RadioButton1111)
                        .addComponent(RadioButton2222)
                        .addComponent(RadioButton3333)
                        .addComponent(RadioButton4444))

        );




        RadioButton = new JRadioButton();
        RadioButton.setText("不对请求进行加解密");


        RadioButton1 = new JRadioButton();
        RadioButton1.setHideActionText(false);
        RadioButton1.setSelected(true);
        RadioButton1.setText("自带算法");

        RadioButton11 = new JRadioButton();
        RadioButton11.setText("密文URL解码读取");

        RadioButton12 = new JRadioButton();
        RadioButton12.setText("加密后的密文URL编码");

        RadioButton2 = new JRadioButton();
        RadioButton2.setText("接口加解密");

        RadioButton3 = new JRadioButton();
        RadioButton3.setText("对数据头进行处理");

        RadioButton4 = new JRadioButton();
        RadioButton4.setText("请求响应不同加解密");

        RadioButton5 = new JRadioButton();
        RadioButton5.setText("请求base64编码");

        RadioButton6 = new JRadioButton();
        RadioButton6.setText("响应base64编码");

        GroupLayout layout1_2 = new GroupLayout(panel1_2);
        panel1_2.setLayout(layout1_2);
        layout1_2.setAutoCreateGaps(true);
        layout1_2.setAutoCreateContainerGaps(true);

        layout1_2.setHorizontalGroup(layout1_2.createSequentialGroup()
                .addGroup(layout1_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton)
                        .addComponent(RadioButton1)
                        .addComponent(RadioButton2))

                .addGroup(layout1_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton11)
                        .addComponent(RadioButton3))

                .addGroup(layout1_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton12)
                        .addComponent(RadioButton4))

                .addGroup(layout1_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton5))

                .addGroup(layout1_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton6))

        );

        layout1_2.setVerticalGroup(layout1_2.createSequentialGroup()
                .addGroup(layout1_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton))

                .addGroup(layout1_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton1)
                        .addComponent(RadioButton11)
                        .addComponent(RadioButton12))

                .addGroup(layout1_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(RadioButton2)
                        .addComponent(RadioButton3)
                        .addComponent(RadioButton4)
                        .addComponent(RadioButton5)
                        .addComponent(RadioButton6)
                )
        );




        final JLabel label13 = new JLabel();
        label13.setText("加解密域名");

        textArea1 = new JTextArea(6,30); // 域名输入栏
        JPanel panelOutput1 = new JPanel();
        panelOutput1.add(new JScrollPane(textArea1));
        textArea1.setLineWrap(true);
        textArea1.setText("");



        JLabel label22 = new JLabel();
        label22.setText("明文关键字(出现关键字则为明文，置空则全部加密；当请求包有明文关键字，响应包也会将密文自动解密返回明文)");

        textArea2 = new JTextArea(6,30); // 明文关键字输入栏
        JPanel panelOutput2 = new JPanel();
        panelOutput2.add(new JScrollPane(textArea2));
        textArea2.setLineWrap(true);
        textArea2.setText("\"\n:");

        JLabel label23 = new JLabel();
        label23.setText("密文关键字(出现关键字则为密文，置空则不处理)");

        textArea3 = new JTextArea(6,20); // 密文关键字输入栏
        JPanel panelOutput3 = new JPanel();
        panelOutput3.add(new JScrollPane(textArea3));
        textArea3.setLineWrap(true);
        textArea3.setText("");

        Button111 = new JButton();
        Button111.setText("保存配置");

        GroupLayout layout1_3 = new GroupLayout(panel1_3);
        panel1_3.setLayout(layout1_3);
        layout1_3.setAutoCreateGaps(true);
        layout1_3.setAutoCreateContainerGaps(true);

        layout1_3.setHorizontalGroup(layout1_3.createSequentialGroup()
                .addGroup(layout1_3.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(label13)
                        .addComponent(label22)
                        .addComponent(label23)
                        .addComponent(panelOutput1)
                        .addComponent(panelOutput2)
                        .addComponent(panelOutput3)
                        .addComponent(Button111))

        );

        layout1_3.setVerticalGroup(layout1_3.createSequentialGroup()
                .addGroup(layout1_3.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(label13))

                .addGroup(layout1_3.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(panelOutput1))

                .addGroup(layout1_3.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(label22))

                .addGroup(layout1_3.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panelOutput2))

                .addGroup(layout1_3.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(label23))

                .addGroup(layout1_3.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panelOutput3))

                .addGroup(layout1_3.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(Button111))

        );







        JPanel panel2_1 = new JPanel(); // 模块控制
        panel2_1.setBorder(BorderFactory.createTitledBorder("加解密主体")); // 模块控制
        panel2_1.setLayout(new BoxLayout(panel2_1, BoxLayout.X_AXIS));

        GroupLayout layout2_1 = new GroupLayout(panel2_1);
        panel2_1.setLayout(layout2_1);
        layout2_1.setAutoCreateGaps(true);
        layout2_1.setAutoCreateContainerGaps(true);



        final JLabel label1 = new JLabel();
        label1.setText("Encrypt");

        final JLabel label2 = new JLabel();
        label2.setText("Vector");

        final JLabel label3 = new JLabel();
        label3.setText("Padding");

        final JLabel label6 = new JLabel();
        label6.setText("Ciphertext");

        final JLabel label14 = new JLabel();
        label14.setText("key/iv");



        mChoiceBox1 = new JComboBox(); // AES / DES / DESede
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("AES");
        defaultComboBoxModel3.addElement("DES");
        defaultComboBoxModel3.addElement("DESede");
        defaultComboBoxModel3.addElement("RSA");
        defaultComboBoxModel3.addElement("null");
        mChoiceBox1.setModel(defaultComboBoxModel3);

        mChoiceBox1.addItemListener(new ItemListener()
        {
            public void itemStateChanged(final ItemEvent e) {
                if (e.SELECTED == e.getStateChange()) {
                    if (mChoiceBox1.getSelectedItem().toString() == "RSA") {
                        label7.setText("Public Key");
                        label4.setText("Private Key");
                    }else{
                        label7.setText("Sessionkey");
                        label4.setText("Ivparamter");
                    }
                }
            }
        });

        mChoiceBox2 = new JComboBox(); // iv模式 ECB / CBC
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("CBC");
        defaultComboBoxModel2.addElement("ECB");
        defaultComboBoxModel2.addElement("CFB");
        mChoiceBox2.setModel(defaultComboBoxModel2);

        mChoiceBox3 = new JComboBox(); // 填充模式
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("PKCS5Padding");
        defaultComboBoxModel1.addElement("NoPadding");
        defaultComboBoxModel1.addElement("ZeroPadding");
        mChoiceBox3.setModel(defaultComboBoxModel1);

        mChoiceBox4 = new JComboBox(); // 密文编码
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("null");
        defaultComboBoxModel4.addElement("Base64");
        defaultComboBoxModel4.addElement("Hex");
        mChoiceBox4.setModel(defaultComboBoxModel4);



        mChoiceBox5 = new JComboBox(); // key iv编码
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        defaultComboBoxModel5.addElement("null");
        defaultComboBoxModel5.addElement("Base64");
        defaultComboBoxModel5.addElement("Hex");
        mChoiceBox5.setModel(defaultComboBoxModel5);


        label7 = new JLabel();
        label7.setText("Sessionkey");

        label4 = new JLabel();
        label4.setText("Ivparamter");

        keytextField = new JTextArea(3,25);
        JPanel panelDecodetext_keytext = new JPanel();
        panelDecodetext_keytext.add(new JScrollPane(keytextField));
        keytextField.setLineWrap(true);

        ivtextField = new JTextArea(3,25);
        JPanel panelDecodetext_iv = new JPanel();
        panelDecodetext_iv.add(new JScrollPane(ivtextField));
        ivtextField.setLineWrap(true);


        final JLabel label5 = new JLabel();
        label5.setText("Ciphertext(密文)");

        final JLabel label10 = new JLabel();
        label10.setText("Plaintext(明文)");

        button1 = new JButton();
        button1.setText("decrypt>");

        Button2 = new JButton();
        Button2.setText("<encrypt");

//        textArea1 = new JTextArea(6,30); // 域名输入栏
//        JPanel panelOutput1 = new JPanel();
//        panelOutput1.add(new JScrollPane(textArea1));
//        textArea1.setLineWrap(true);
//        textArea1.setText("");

        EncodedtextArea = new JTextArea(6,25);
        JPanel panelEncodedtext = new JPanel();
        panelEncodedtext.add(new JScrollPane(EncodedtextArea));
        EncodedtextArea.setLineWrap(true);
//        EncodedtextArea.setText("");


        DecodetextArea = new JTextArea(6,25);
        JPanel panelDecodetext = new JPanel();
        panelDecodetext.add(new JScrollPane(DecodetextArea));
        DecodetextArea.setLineWrap(true);
//        DecodetextArea.setText("");

        Button3 = new JButton();
        Button3.setText("添加为请求包加/解密方式");

        Button4 = new JButton();
        Button4.setText("添加为响应包解密方式");


        layout2_1.setHorizontalGroup(layout2_1.createSequentialGroup()
                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label1)
                        .addComponent(label7))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mChoiceBox1)
                        .addComponent(panelDecodetext_keytext)
                        .addComponent(label5)
                        .addComponent(panelEncodedtext)
                        .addComponent(Button3))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label2)
                        .addComponent(button1)
                )

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mChoiceBox2)

                )

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label3)
                        .addComponent(label4)
                        .addComponent(Button2)
                )

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mChoiceBox3)
                        .addComponent(panelDecodetext_iv)
                        .addComponent(label10)
                        .addComponent(panelDecodetext)
                        .addComponent(Button4))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label6))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mChoiceBox4))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label14))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mChoiceBox5))
        );

        layout2_1.setVerticalGroup(layout2_1.createSequentialGroup()
                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label1)
                        .addComponent(mChoiceBox1)
                        .addComponent(label2)
                        .addComponent(mChoiceBox2)
                        .addComponent(label3)
                        .addComponent(mChoiceBox3)
                        .addComponent(label6)
                        .addComponent(mChoiceBox4)
                        .addComponent(label14)
                        .addComponent(mChoiceBox5))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label7)
                        .addComponent(panelDecodetext_keytext)
                        .addComponent(label4)
                        .addComponent(panelDecodetext_iv))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label5)
                        .addComponent(button1)
                        .addComponent(Button2)
                        .addComponent(label10))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(panelEncodedtext)
                        .addComponent(panelDecodetext))

                .addGroup(layout2_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(Button3)
                        .addComponent(Button4))
        );


        JPanel panel2_2 = new JPanel(); // 模块控制
        panel2_2.setBorder(BorderFactory.createTitledBorder("加解密方式")); // 模块控制
        panel2_2.setLayout(new BoxLayout(panel2_2, BoxLayout.X_AXIS));

        GroupLayout layout2_2 = new GroupLayout(panel2_2);
        panel2_2.setLayout(layout2_2);
        layout2_2.setAutoCreateGaps(true);
        layout2_2.setAutoCreateContainerGaps(true);


        final JLabel label11 = new JLabel();
        label11.setText("响应包解密方式");

        String[] columnNames =
                { "加密类型", "密文编码", "key/iv编码","sessionkey","iv"};
        Object[][] data2 = new Object[1][5];
        DefaultTableModel model2 = new DefaultTableModel(data2, columnNames);
        table2 = new JTable(2,5);
        JScrollPane scrollPane = new JScrollPane(table2);



        final JLabel label12 = new JLabel();
        label12.setText("请求包加/解密方式");

        Object[][] data = new Object[1][5];
        DefaultTableModel model=new DefaultTableModel(data, columnNames);
        table1 = new JTable(model);
        JScrollPane scrollPane2 = new JScrollPane(table1);

        Button11 = new JButton();
        Button11.setText("保存配置");
        Button22 = new JButton();
        Button22.setText("恢复默认配置");



        layout2_2.setHorizontalGroup(layout2_2.createSequentialGroup()
                .addGroup(layout2_2.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label11)
                        .addComponent(label12)
                        .addComponent(Button11)
                        .addComponent(Button22))

                .addGroup(layout2_2.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(table2)
                        .addComponent(table1))


        );

        layout2_2.setVerticalGroup(layout2_2.createSequentialGroup()
                .addGroup(layout2_2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label12)
                        .addComponent(table2))

                .addGroup(layout2_2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label11)
                        .addComponent(table1))

                .addGroup(layout2_2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(Button11))

                .addGroup(layout2_2.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(Button22))


        );

































        JPanel panel2_3 = new JPanel(); // 模块控制
        panel2_3.setBorder(BorderFactory.createTitledBorder("正则模式")); // 模块控制
        panel2_3.setLayout(new BoxLayout(panel2_3, BoxLayout.X_AXIS));

        GroupLayout layout2_3 = new GroupLayout(panel2_3);
        panel2_3.setLayout(layout2_3);
        layout2_3.setAutoCreateGaps(true);
        layout2_3.setAutoCreateContainerGaps(true);

        final JLabel labelreg = new JLabel();
        labelreg.setText("正则提取文本(针对请求包)");

        final JLabel labelreg_resp = new JLabel();
        labelreg_resp.setText("正则提取文本(针对响应包)");

        InputtextArea = new JTextArea(4,30); // 输入需要提取的文本
        JPanel js11 = new JPanel();
        js11.add(new JScrollPane(InputtextArea));
        InputtextArea.setLineWrap(true);
        InputtextArea.setText("");

        InputtextArea_resp = new JTextArea(4,30); // 输入需要提取的文本
        JPanel js11_resp = new JPanel();
        js11_resp.add(new JScrollPane(InputtextArea_resp));
        InputtextArea_resp.setLineWrap(true);
        InputtextArea_resp.setText("");

        final JLabel labelregr = new JLabel();
        labelregr.setText("正则表达式");

        final JLabel labelregr_resp = new JLabel();
        labelregr_resp.setText("正则表达式");

        regtextField = new JTextField(25); // 正则表达式

        regtextField_resp = new JTextField(25); // 正则表达式

        buttontiqu = new JButton();
        buttontiqu.setText("提取");

        buttontiqu_resp = new JButton();
        buttontiqu_resp.setText("提取");

        OutputtextArea = new JTextArea(); // 提取后的文本
        OutputtextArea.setLineWrap(true);
        OutputtextArea.setText("");
        JScrollPane js22=new JScrollPane(OutputtextArea);

        OutputtextArea_resp = new JTextArea(); // 提取后的文本
        OutputtextArea_resp.setLineWrap(true);
        OutputtextArea_resp.setText("");
        JScrollPane js22_resp=new JScrollPane(OutputtextArea_resp);

        layout2_3.setHorizontalGroup(layout2_3.createSequentialGroup()
                .addGroup(layout2_3.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(labelreg)
                        .addComponent(labelreg_resp)
                )

                .addGroup(layout2_3.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(js11)
                        .addComponent(js11_resp)
                )

                .addGroup(layout2_3.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(labelregr)
                        .addComponent(labelregr_resp)
                )

                .addGroup(layout2_3.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(regtextField)
                        .addComponent(regtextField_resp)
                )

                .addGroup(layout2_3.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(buttontiqu)
                        .addComponent(buttontiqu_resp)
                )

                .addGroup(layout2_3.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(js22)
                        .addComponent(js22_resp)
                )

        );

        layout2_3.setVerticalGroup(layout2_3.createSequentialGroup()
                .addGroup(layout2_3.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelreg)
                        .addComponent(js11)
                        .addComponent(labelregr)
                        .addComponent(regtextField)
                        .addComponent(buttontiqu)
                        .addComponent(js22))

                .addGroup(layout2_3.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(labelreg_resp)
                        .addComponent(js11_resp)
                        .addComponent(labelregr_resp)
                        .addComponent(regtextField_resp)
                        .addComponent(buttontiqu_resp)
                        .addComponent(js22_resp)
                )
        );















        JPanel panel3_1 = new JPanel(); // 加解密选项开关
        panel3_1.setBorder(BorderFactory.createTitledBorder("接口设置")); // 模块控制
        panel3_1.setLayout(new BoxLayout(panel3_1, BoxLayout.X_AXIS));

        GroupLayout layout3_1 = new GroupLayout(panel3_1);
        panel3_1.setLayout(layout3_1);
        layout3_1.setAutoCreateGaps(true);
        layout3_1.setAutoCreateContainerGaps(true);


        final JLabel label8 = new JLabel();
        label8.setText("解密接口");

        textField4 = new JTextField(25);
        textField4.setText("");

        final JLabel label9 = new JLabel();
        label9.setText("加密接口");

        textField3 = new JTextField(25);
        textField3.setText("");


        layout3_1.setHorizontalGroup(layout3_1.createSequentialGroup()
                .addGroup(layout3_1.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label8))

                .addGroup(layout3_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(textField4))

                .addGroup(layout3_1.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(label9))

                .addGroup(layout3_1.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(textField3))
        );

        layout3_1.setVerticalGroup(layout3_1.createSequentialGroup()
                .addGroup(layout3_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label8)
                        .addComponent(textField4)
                        .addComponent(label9)
                        .addComponent(textField3))
        );


        JPanel panel3_2 = new JPanel(); // 加解密选项开关
        panel3_2.setBorder(BorderFactory.createTitledBorder("接口调试")); // 模块控制
        panel3_2.setLayout(new BoxLayout(panel3_2, BoxLayout.X_AXIS));



        RadioButtontestEncode = new JRadioButton("请求数据包");
        RadioButtontestEncode.setSelected(true);

        RadioButtontestDecode = new JRadioButton("响应数据包");

        RadioButtontestHeader = new JRadioButton("对数据头进行处理");

        RadioButtontestdifferent = new JRadioButton("请求响应不同加解密");

        Label labelDatatest = new Label("原始数据包");

        textDatatestArea = new JTextArea(20,30); // 数据包
        JPanel Datatestpanel = new JPanel();
        Datatestpanel.add(new JScrollPane(textDatatestArea));
        textDatatestArea.setLineWrap(true);

//        InputtextArea = new JTextArea(6,30); // 输入需要提取的文本
//        JPanel js11 = new JPanel();
//        js11.add(new JScrollPane(InputtextArea));
//        InputtextArea.setLineWrap(true);
//        InputtextArea.setText("");


        Label labelDatatestOutput = new Label("处理后的数据包");

        textDatatestAreaoutput = new JTextArea(20,30); // 数据包输出
        JPanel DatatestpanelOutput = new JPanel();
        DatatestpanelOutput.add(new JScrollPane(textDatatestAreaoutput));
        textDatatestAreaoutput.setLineWrap(true);

        testEncodejb = new JButton("<<<加密");
        testDecodejb = new JButton("解密>>>");

        testDecodejb.addActionListener(new ActionListener() { // 调试解密
            @Override
            public void actionPerformed(ActionEvent e) {
                if(RadioButtontestEncode.isSelected()){
                    try {
                        textDatatestAreaoutput.setText(TestDecode.TestDecode(textDatatestArea.getText(),"request"));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        textDatatestAreaoutput.setText(TestDecode.TestDecode(textDatatestArea.getText(),"response"));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        testEncodejb.addActionListener(new ActionListener() { // 调试加密
            @Override
            public void actionPerformed(ActionEvent e) {
                if(RadioButtontestEncode.isSelected()){
                    try {
                        textDatatestArea.setText(TestEncode.TestEncode(textDatatestAreaoutput.getText(),"request"));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    try {
                        textDatatestArea.setText(TestEncode.TestEncode(textDatatestAreaoutput.getText(),"response"));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });





        GroupLayout layout3_2 = new GroupLayout(panel3_2);
        panel3_2.setLayout(layout3_2);
        layout3_2.setAutoCreateGaps(true);
        layout3_2.setAutoCreateContainerGaps(true);

        layout3_2.setHorizontalGroup(layout3_2.createSequentialGroup()
                .addGroup(layout3_2.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(RadioButtontestEncode)
                        .addComponent(RadioButtontestHeader)
                        .addComponent(labelDatatest)
                        .addComponent(Datatestpanel))

                .addGroup(layout3_2.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(testDecodejb))


                .addGroup(layout3_2.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(testEncodejb))


                .addGroup(layout3_2.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(RadioButtontestDecode)
                        .addComponent(RadioButtontestdifferent)
                        .addComponent(labelDatatestOutput)
                        .addComponent(DatatestpanelOutput))

        );

        layout3_2.setVerticalGroup(layout3_2.createSequentialGroup()
                .addGroup(layout3_2.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(RadioButtontestEncode)
                        .addComponent(RadioButtontestDecode))

                .addGroup(layout3_2.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(RadioButtontestHeader)
                        .addComponent(RadioButtontestdifferent))

                .addGroup(layout3_2.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(labelDatatest)
                        .addComponent(labelDatatestOutput))

                .addGroup(layout3_2.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(Datatestpanel)
                        .addComponent(testDecodejb)
                        .addComponent(testEncodejb)
                        .addComponent(DatatestpanelOutput))


        );




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
                    fileWriter.append("reqencodemode=DES");
                    fileWriter.append("\n");
                    fileWriter.append("reqivmode=CBC");
                    fileWriter.append("\n");
                    fileWriter.append("reqpaddingmode=PKCS5Padding");
                    fileWriter.append("\n");
                    fileWriter.append("reqsSrcmode=Base64");
                    fileWriter.append("\n");
                    fileWriter.append("reqkeyivmode=null");
                    fileWriter.append("\n");
                    fileWriter.append("reqskey=f0ngtest");
                    fileWriter.append("\n");
                    fileWriter.append("reqiv=f0ngf0ng");
                    fileWriter.append("\n");

                    fileWriter.append("respencodemode=DES");
                    fileWriter.append("\n");
                    fileWriter.append("respivmode=CBC");
                    fileWriter.append("\n");
                    fileWriter.append("resppaddingmode=PKCS5Padding");
                    fileWriter.append("\n");
                    fileWriter.append("respsSrcmode=Base64");
                    fileWriter.append("\n");
                    fileWriter.append("respkeyivmode=null");
                    fileWriter.append("\n");
                    fileWriter.append("respskey=f0ngtest");
                    fileWriter.append("\n");
                    fileWriter.append("respiv=f0ngf0ng");
                    fileWriter.append("\n");

                    fileWriter.append("hosts=10.211.55.4");
                    fileWriter.append("\n");
                    fileWriter.append("words=\",:");
                    fileWriter.append("\n");
                    fileWriter.append("notwords=");
                    fileWriter.flush();
                } catch (IOException e) { e.printStackTrace(); }
            } catch (IOException e) { e.printStackTrace(); }
        }

        String[] columnNames2 =
                {"加密类型", "密文编码", "key/iv编码","sessionkey","iv"};
        Object[][] data3 = new Object[1][5];
        Object[][] data3resp = new Object[1][5];

        String encodemode = FileGetValue(f,"reqencodemode"); // AES / DES / DESede
        String ivmode = FileGetValue(f,"reqivmode"); // iv模式 ECB / CBC
        String paddingmode = FileGetValue(f,"reqpaddingmode"); // 填充模式
        String sSrcmode = FileGetValue(f,"reqsSrcmode"); // 密文编码
        String keyivmode = FileGetValue(f,"reqkeyivmode"); // key iv编码
        String skey = FileGetValue(f,"reqskey");; // key 密钥
        String iv = FileGetValue(f,"reqiv");; // iv

        String respencodemode = FileGetValue(f,"respencodemode"); // AES / DES / DESede
        String respivmode = FileGetValue(f,"respivmode"); // iv模式 ECB / CBC
        String resppaddingmode = FileGetValue(f,"resppaddingmode"); // 填充模式
        String respsSrcmode = FileGetValue(f,"respsSrcmode"); // 密文编码
        String respkeyivmode = FileGetValue(f,"respkeyivmode"); // key iv编码
        String respskey = FileGetValue(f,"respskey");; // key 密钥
        String respiv = FileGetValue(f,"respiv");; // iv

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
        textArea1.setText(t.trim());

        String[] words = FileGetValue(f,"words").split(",");
        String tt = "";
        for (String word : words)
            tt = tt + word +'\n';
        textArea2.setText(tt.trim());

        String[] not_words = FileGetValue(f,"notwords").split(",");
        String not_tt = "";
        for (String not_word : not_words)
            not_tt = not_tt + not_word +'\n';
        textArea3.setText(not_tt.trim());

        data3[0][0] = encodemode + "/" + ivmode + "/" + paddingmode;
        data3[0][1] = sSrcmode;
        data3[0][2] = keyivmode;
        data3[0][3] = skey;
        data3[0][4] = iv;

        data3resp[0][0] = respencodemode + "/" + respivmode + "/" + resppaddingmode;
        data3resp[0][1] = respsSrcmode;
        data3resp[0][2] = respkeyivmode;
        data3resp[0][3] = respskey;
        data3resp[0][4] = respiv;

        DecodeParams[0] = encodemode;
        DecodeParams[1] = ivmode;
        DecodeParams[2] = paddingmode;
        DecodeParams[3] = sSrcmode;
        DecodeParams[4] = keyivmode;
        DecodeParams[5] = skey;
        DecodeParams[6] = iv;


        RespDecodeParams[0] = respencodemode;
        RespDecodeParams[1] = respivmode;
        RespDecodeParams[2] = resppaddingmode;
        RespDecodeParams[3] = respsSrcmode;
        RespDecodeParams[4] = respkeyivmode;
        RespDecodeParams[5] = respskey;
        RespDecodeParams[6] = respiv;


        DefaultTableModel model3 = new DefaultTableModel(data3, columnNames2);
        DefaultTableModel respmodel3 = new DefaultTableModel(data3resp, columnNames2);
        table1.setModel(respmodel3); // 响应包

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

        // 按钮组
        ButtonGroup bp = new ButtonGroup();
        bp.add(RadioButton2);
        bp.add(RadioButton1);
        bp.add(RadioButton);

        // 按钮组2
        ButtonGroup bp2 = new ButtonGroup();
        bp2.add(RadioButtontestEncode);
        bp2.add(RadioButtontestDecode);

        panel1.add(panel1_1);
        panel1.add(panel1_2);
        panel1.add(panel1_3);

        panel2.add(panel2_1);
        panel2.add(panel2_2);
        panel2.add(panel2_3);

        panel3.add(panel3_1);
        panel3.add(panel3_2);

        mainPanel.addTab("Options",rootJPanel);
        mainPanel.addTab("自带算法加解密",rootJPanel2);
        mainPanel.addTab("接口加解密",rootJPanel3);

    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
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

    public static String gettextArea3() {
        return textArea3.getText();
    }
    public static Boolean getRadioButton1111State() {
        return RadioButton1111.isSelected();
    }
    public static Boolean getRadioButton2222State() {
        return RadioButton2222.isSelected();
    }
    public static Boolean getRadioButton3333State() {
        return RadioButton3333.isSelected();
    }
    public static Boolean getRadioButton4444State() {
        return RadioButton4444.isSelected();
    }


    public static Boolean getRadioButton11State() {
        return RadioButton11.isSelected();
    } // 密文URL解码读取

    public static Boolean getRadioButton12State() {
        return RadioButton12.isSelected();
    } // 加密后的密文URL编码

    public static String getEncodeApi() {
        return textField3.getText().trim();
    }

    public static String getDecodeApi() {
        return textField4.getText().trim();
    }


    public static String[] getDecodeParams() {
        return deleteArrayNull(DecodeParams);
    }

    public static String[] getRespDecodeParams() {
        return deleteArrayNull(RespDecodeParams);
    }
}

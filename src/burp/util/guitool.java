package burp.util;

import javax.swing.*;

import java.awt.*;

import static burp.IndexautoDecoder.*;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/10/23 下午2:16
 */
public class guitool {
    public static void layout321(GroupLayout layout3_2, Label labelDatatest, JPanel Datatestpanel ,JButton testDecodejb,JButton testEncodejb,Label labelDatatestOutput , JPanel DatatestpanelOutput){
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
    }

    public static void layout322(GroupLayout layout3_2, Label labelDatatest, JPanel Datatestpanel ,JButton testDecodejb,JButton testEncodejb,Label labelDatatestOutput , JPanel DatatestpanelOutput){
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
    }

    public static void layout311(GroupLayout layout3_1,JLabel label8 , JTextField textField4, JLabel label9, JTextField textField3){
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
    }

    public static void layout312(GroupLayout layout3_1,JLabel label8 , JTextField textField4, JLabel label9, JTextField textField3){
        layout3_1.setVerticalGroup(layout3_1.createSequentialGroup()
                .addGroup(layout3_1.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(label8)
                        .addComponent(textField4)
                        .addComponent(label9)
                        .addComponent(textField3))
        );
    }

    public static void layout231(GroupLayout layout2_3,JLabel labelreg , JLabel labelreg_resp, JPanel js11, JPanel js11_resp,JLabel labelregr, JLabel labelregr_resp,JButton buttontiqu , JButton buttontiqu_resp , JScrollPane js22, JScrollPane js22_resp){
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
    }

    public static void layout232(GroupLayout layout2_3,JLabel labelreg , JLabel labelreg_resp, JPanel js11, JPanel js11_resp,JLabel labelregr, JLabel labelregr_resp,JButton buttontiqu , JButton buttontiqu_resp , JScrollPane js22, JScrollPane js22_resp){
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
    }
}

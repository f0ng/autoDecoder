package burp.ui;

import burp.BurpExtender;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import java.awt.*;

/**
 * @DESCRIPTION:
 * @USER: f0ng
 * @DATE: 2023/5/21 下午1:52
 */
public class HackvertorInput extends RSyntaxTextArea {
    public HackvertorInput() {
        super();
        BurpExtender.callbacks.customizeUiComponent(this);
        this.updateFont();
    }
    public void updateUI() {
        super.updateUI();
//        BurpExtender.stdout.println(BurpExtender.DARK_THEMES);
//        BurpExtender.stdout.println(UIManager.getLookAndFeel().getID());
        BurpExtender.isDarkTheme = UIManager.getLookAndFeel().getID().contains("Dark");
        SwingUtilities.invokeLater(() -> {
            if(BurpExtender.isDarkTheme) {
                Utils.Utils.applyThemeToRSyntaxTextArea(this, "dark");
            } else {
                Utils.Utils.applyThemeToRSyntaxTextArea(this, "default");
            }
        });
        BurpExtender.callbacks.customizeUiComponent(this);
        this.updateFont();
    }
    public void updateFont() {
        this.setFont(new Font("Courier New", Font.PLAIN, this.getFont().getSize()));
    }
}

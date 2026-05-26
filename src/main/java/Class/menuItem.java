/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author HP
 */
public class menuItem { 
    
    private final Color activeBackground = Color.decode("#E2E8F0");

    private final Color defaultForeground = Color.decode("#475569");
    private final Color activeForeground = Color.WHITE;
    
    private JPanel panel;
    private JLabel label;
    private JLabel iconLabel;
    private javax.swing.ImageIcon blackIcon;
    private javax.swing.ImageIcon whiteIcon;

    public menuItem(JPanel panel, JLabel label) {
        this.panel = panel;
        this.label = label;
    }

    public menuItem(JPanel panel, JLabel label, JLabel iconLabel, String blackIconPath, String whiteIconPath) {
        this.panel = panel;
        this.label = label;
        this.iconLabel = iconLabel;
        
        java.net.URL blackUrl = getClass().getResource(blackIconPath);
        if (blackUrl != null) {
            this.blackIcon = new javax.swing.ImageIcon(blackUrl);
        }
        
        java.net.URL whiteUrl = getClass().getResource(whiteIconPath);
        if (whiteUrl != null) {
            this.whiteIcon = new javax.swing.ImageIcon(whiteUrl);
        }
    }

    public void resetIcon() {
        if (iconLabel != null && blackIcon != null) {
            iconLabel.setIcon(blackIcon);
        }
    }

    public void iconPressed() {
        if (iconLabel != null && whiteIcon != null) {
            iconLabel.setIcon(whiteIcon);
        }
    }

    public void setDefault() {
        panel.setOpaque(false);
        label.setForeground(defaultForeground);
        resetIcon();
        panel.repaint();
    }

    public void setActive() {
        panel.setOpaque(true);
        label.setForeground(activeForeground);
        iconPressed();
        panel.repaint();
    }

    public static void resetMenu(menuItem... panels) {
        for (menuItem panel : panels) {
            panel.setDefault();
        }
    }
}

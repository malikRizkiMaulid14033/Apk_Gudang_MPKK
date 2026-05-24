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

    public menuItem(JPanel panel, JLabel label) {
        this.panel = panel;
        this.label = label;
    }

   

    public void setDefault() {
        panel.setOpaque(false);
        label.setForeground(defaultForeground);

        panel.repaint();
    }

    public void setActive() {
        panel.setOpaque(true);
        label.setForeground(activeForeground);

        panel.repaint();
    }

    public static void resetMenu(menuItem... panels) {
        for (menuItem panel : panels) {
            panel.setDefault();
        }
    }

}

package test;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class test {

    public static void main(String[] args) {
        JFrame frame = new JFrame("证书");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(
                new CARead("E:/lhx/license.der"),
                BorderLayout.CENTER);
        frame.setSize(700, 425);
        frame.setVisible(true);
    }

}

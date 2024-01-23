package test.internal_frame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EmbeddedAppDemo extends JFrame {
    private JDesktopPane desktopPane;

    public EmbeddedAppDemo() {
        super("Embedded Application Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        desktopPane = new JDesktopPane();
        createInternalFrame("C:\\WINDOWS\\cmd.exe", "My Embedded App");

        setContentPane(desktopPane);
    }

    private void createInternalFrame(String applicationPath, String title) {
        JInternalFrame internalFrame = new JInternalFrame(title, true, true, true, true);
        internalFrame.setSize(400, 300);

        JButton openButton = new JButton("Open External App");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder(applicationPath);
                    processBuilder.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        internalFrame.add(openButton);
        internalFrame.setVisible(true);
        desktopPane.add(internalFrame);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmbeddedAppDemo().setVisible(true);
            }
        });
    }
}

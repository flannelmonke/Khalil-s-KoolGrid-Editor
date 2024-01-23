package terminalIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

public class terminal extends JPanel {
    private JTextArea outputTextArea;
    private JTextField commandInputField;
    private Font terminalFont = null;

    public terminal() {
        setLayout(new BorderLayout());

        outputTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        add(scrollPane, BorderLayout.CENTER);

        commandInputField = new JTextField();
        JButton executeButton = new JButton("Execute Command");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand(commandInputField.getText());
                commandInputField.setText("");
            }
        });

        Path default_font_path = Path.of("lib/assets/fonts/JetBrainsMono-Regular.ttf");

        setFont(null, new File(default_font_path.toAbsolutePath().toString()));
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(commandInputField, BorderLayout.CENTER);
        inputPanel.add(executeButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
        executeCommand("");
    }

    public void executeCommand(String command) {
        switch (command) {
            case "clear":
            case "cls":
                outputTextArea.setText("");
                break;
            default:
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/k");
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();

                    // Redirect the input stream to write commands to the process
                    OutputStream outputStream = process.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                    writer.write(command + "\n");
                    writer.flush();

                    // Read the output of the command prompt and display it in the JTextArea
                    readProcessOutput(process);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    private void readProcessOutput(Process process) {
        new Thread(() -> {
            try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    final String finalLine = line;
                    SwingUtilities.invokeLater(() -> outputTextArea.append(finalLine + "\n"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public JTextField getCommandInputField() {
        return commandInputField;
    }

    public void setFont(Font font, File file) {
        if (font != null) {
            terminalFont = font;
            outputTextArea.setFont(terminalFont);
            commandInputField.setFont(terminalFont);
        } else if (file != null) {
            try {
                terminalFont = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(12f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(terminalFont);

                outputTextArea.setFont(terminalFont);
                commandInputField.setFont(terminalFont);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package terminalIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class terminal extends JPanel {
    private JTextArea outputTextArea;
    private JTextField commandInputField;

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

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(commandInputField, BorderLayout.CENTER);
        inputPanel.add(executeButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void executeCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe");
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
}

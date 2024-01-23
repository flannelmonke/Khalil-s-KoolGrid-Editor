package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JTextArea;

public class terminal_manager {

    private File workingDirectory = null;
    private String enviornment = System.getProperty("os.name").toLowerCase();
    private String[] command = new String[4];

    public terminal_manager(String opened_file) {
        if (opened_file == null) {
            setWorkingDirectory(new File(System.getProperty("user.home")));
        } else {
            setWorkingDirectory(new File(opened_file).getParentFile());
        }
    }

    public void run_command(String command, JTextArea terminal) {
        setCommandPrefix();
        setCommand(command);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(this.command);
            processBuilder.directory(this.workingDirectory);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            try (PrintWriter writer = new PrintWriter(process.getOutputStream())) {
                writer.println(terminal.getText());
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                int count = 1;
                while ((line = reader.readLine()) != null) {
                    System.out.println(count);
                    System.out.println(reader.readLine());
                    terminal.append(line + "\n");
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(File workingDirectory) {
        if (workingDirectory.getParentFile() == null) {
            this.workingDirectory = new File(System.getProperty("user.home"));
        } else {
            this.workingDirectory = workingDirectory;
        }
    }

    public String getEnviornment() {
        return enviornment;
    }

    public String[] getCommand() {
        return command;
    }

    public void setCommandPrefix() {
        if (this.enviornment.contains("win")) {
            this.command[0] = "cmd";
            this.command[1] = "/k";
            this.command[2] = "";
        } else {
            this.command[0] = "bash";
            this.command[1] = "-c";
            this.command[2] = "-i";
        }
    }

    public void setCommand(String command) {
        this.command[3] = command;
    }
}

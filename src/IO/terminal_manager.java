package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class terminal_manager {

    private File workingDirectory = null;
    private String enviornment = System.getProperty("os.name").toLowerCase();
    private ArrayList<String> command = new ArrayList<>();

    public terminal_manager() {
        setWorkingDirectory(null);

    }

    public terminal_manager(File opened_file) {
        setWorkingDirectory(opened_file.getParentFile());

    }

    public ArrayList<String> run_command(String command, File working_directory) {
        ArrayList<String> output = new ArrayList<>();
        setCommandPrefix();
        setCommand(command);
        setWorkingDirectory(working_directory);
        try{
            
            ProcessBuilder processBuilder = new ProcessBuilder(this.command);
            processBuilder.directory(this.workingDirectory);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))){
                String line;
                while((line = reader.readLine())!=null){
                    output.add(line);
                }
            }

        }catch(Exception e){
            output.add("Boy what is you doing?");
            return output;
        }

        return output;
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

    public ArrayList<String> getCommand() {
        return command;
    }

    public void setCommandPrefix() {
        if (this.enviornment.contains("win")) {
            this.command.set(0, "cmd");
            this.command.set(1, "k");
        } else {
            this.command.set(0, "bash");
            command.set(1, "-i");
            command.set(2, "-c");
        }
    }

    public void setCommand(String command) {
        this.command.set(3, command);
    }
}

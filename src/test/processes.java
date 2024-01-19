package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class processes {
    public static void main(String[] args) throws IOException {
        try {
            // Specify the working directory
            File workingDirectory = new File("D:/Productivity/");

            String[] command = new String[4];
            command[0] = "cmd";
            command[1] = "/k";
            command[3] = "cd";
            command[2] = "";
            // Command and arguments to run on the command prompt
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Set the working directory
            processBuilder.directory(workingDirectory);

            // Redirect the standard output
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line+">");
                }
            }
            
            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Command exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

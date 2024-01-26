package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class file_loader {
    public ArrayList<ArrayList<String>> text = new ArrayList<>();
    public int rows;
    public int cols = 0;
    private File selected_file;

    public file_loader() {
    }

    public file_loader(String file_path) {
        selected_file = new File(file_path);
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(selected_file));
            while ((line = br.readLine()) != null) { // returns a Boolean value
                String[] data = line.split(","); // use comma as separator
                if (cols < data.length)
                    cols = data.length;

                ArrayList<String> temp = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    temp.add(data[i]);
                }
                text.add(temp);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Some sort of IOException");
            e.printStackTrace();
        }
        rows = text.size();
    }

}

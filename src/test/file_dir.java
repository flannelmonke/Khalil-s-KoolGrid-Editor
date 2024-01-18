package test;

import java.io.File;

public class file_dir {
    public static void main(String[] args) {
        File directory = new File("D:/Productivity/Stat Machomp");
        System.out.println(directory.isDirectory());
        File[] fileArray = directory.listFiles();

        for (File file : fileArray) {
            System.out.println(file.getName());
        }
    }
}

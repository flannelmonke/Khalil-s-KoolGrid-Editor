import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import csvIO.file_loader;

public class App{
    public static void main(String[] args) {
        // JFileChooser chooser = new JFileChooser("C:/Users/khali/Desktop");
        file_loader reader = new file_loader("D:/Productivity/Stat Machomp/push yourself/lib/testing files/income_data.csv");
        frame_maker(reader);
    }
    
    public static void frame_maker(file_loader reader) {
        // Frame Creation
        JFrame frame = new JFrame("Khalil's KoolGrid Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image icon = Toolkit.getDefaultToolkit().getImage("D:/Productivity/Stat Machomp/push yourself/lib/assets/monkey-98455_1920.png");
        frame.setIconImage(icon);
        
        // Cell Grid Creation 
        int rows = reader.rows;
        int cols = reader.cols;
        if(cols<10){
            cols = 15;
        }

        JPanel CellGrid = new JPanel(new GridLayout(rows, cols, 1, 1));
    
        for (int i = 0; i < reader.rows; i++) {
            for (int j = 0; j < cols; j++) {
                JTextField text = new JTextField();
                
                try{
                    text.setText(reader.text.get(i).get(j));
                }catch(IndexOutOfBoundsException e){
                    text.setText("");
                }finally{
                text.setPreferredSize(new Dimension(50, 25));
                text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                
                CellGrid.add(text);
            }
            }
        }
        //add to scroll pane in case of large number of rows.
        JScrollPane scrollPane = new JScrollPane(CellGrid);


        // Header Creation
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));

        Button open_file = new Button("Open File...");
        open_file.setPreferredSize(new Dimension(100,40));
        
        Button save_file = new Button("Save File");
        save_file.setPreferredSize(new Dimension(100,40));
        
        Button new_window = new Button("New Window");
        new_window.setPreferredSize(new Dimension(100,40));
        
        header.add(open_file);
        header.add(save_file);
        header.add(new_window);

        // Add all content to frame
        frame.getContentPane().add(header, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane,BorderLayout.CENTER);
        frame.setVisible(true);
    }   
}
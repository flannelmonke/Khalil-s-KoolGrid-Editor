package frameManipulation;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import csvIO.file_loader;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import java.io.File;

public class frame_maker {
    JFrame frame = new JFrame("Khalil's KoolGrid Editor");

    public Button open_file = new Button("Open File");
    public Button load = new Button("Load File");
    public Button save_file = new Button("Save File");
    public Button new_window = new Button("New Window");
    public Button new_tab = new Button("New Tab");


    public frame_maker(){
        frame_init();
    }

    public frame_maker(File selected_file){
       frame_init();
       load(selected_file);
    }

    public void frame_init(){
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800,800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image icon = Toolkit.getDefaultToolkit().getImage("D:/Productivity/Stat Machomp/push yourself/lib/assets/monkey-98455_1920.png");
        frame.setIconImage(icon);

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));

        open_file.setPreferredSize(new Dimension(100,40));
        load.setPreferredSize(new Dimension(100,40));
        save_file.setPreferredSize(new Dimension(100,40));
        new_window.setPreferredSize(new Dimension(100,40));
        new_tab.setPreferredSize(new Dimension(100,40));
        
        header.add(open_file);
        header.add(save_file);
        header.add(load);
        header.add(new_window);
        header.add(new_tab);


        frame.getContentPane().add(header, BorderLayout.NORTH);
    }

    public void init(){
        frame.setVisible(true);
    }

    public void load(File selected_file){
        file_loader reader = new file_loader(selected_file.getAbsolutePath());

        int rows = reader.rows;
        int cols = reader.cols;

        JPanel CellGrid = new JPanel(new GridLayout(rows, cols,0,0));

        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++){
                JTextField text = new JTextField();

                try{
                    text.setText(reader.text.get(i).get(j));
                }catch(IndexOutOfBoundsException e){
                    text.setText("");
                }finally{
                    text.setPreferredSize(new Dimension(50,25));
                    text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                    CellGrid.add(text);
                }
            }
        }
        JScrollPane scrollPane = new JScrollPane(CellGrid);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

}
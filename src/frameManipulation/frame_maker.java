package frameManipulation;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import IO.file_loader;

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
    //Application frame
    JFrame frame = new JFrame("Khalil's KoolGrid Editor");

    //Content pane (Split pane for controlling size of content areas)
    JSplitPane terminal_grid = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    //Terminal TextArea
    JTextArea terminalIO = new JTextArea();
    
    //Cell Grid area
    JPanel CellGrid = new JPanel(new GridLayout(501, 31, 0, 0));


    //Header buttons
    public Button open_file = new Button("Open File");
    public Button load = new Button("Load File");
    public Button save_file = new Button("Save File");
    public Button new_window = new Button("New Window");
    public Button new_tab = new Button("New Tab");

    public frame_maker() {
        frame_init();
    }

    public frame_maker(String path) {
        frame_init();
        load(path);
    }

    public void frame_init() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Image icon = Toolkit.getDefaultToolkit()
                .getImage("D:/Productivity/Stat Machomp/push yourself/lib/assets/monkey-98455_1920.png");
        frame.setIconImage(icon);

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));

        open_file.setPreferredSize(new Dimension(100, 40));
        load.setPreferredSize(new Dimension(100, 40));
        save_file.setPreferredSize(new Dimension(100, 40));
        new_window.setPreferredSize(new Dimension(100, 40));
        new_tab.setPreferredSize(new Dimension(100, 40));

        header.add(open_file);
        header.add(save_file);
        header.add(load);
        header.add(new_window);
        header.add(new_tab);

        frame.getContentPane().add(header, BorderLayout.NORTH);
        terminal_init();
    }

    public void init() {
        frame.setVisible(true);
    }

    public void load(String path) {
        
        CellGrid.removeAll();

        
        file_loader reader = new file_loader();
        int rows=500;
        int cols=30;
        if(path != null){
            reader = new file_loader(path);
            rows = reader.rows;
            cols = reader.cols;
        }
        create_header(cols);
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {
                JTextField text = new JTextField();

                try {
                    text.setText(reader.text.get(i).get(j));
                } catch (IndexOutOfBoundsException e) {
                    text.setText(" ");
                } finally {
                    text.setPreferredSize(new Dimension(50, 25));
                    text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                    CellGrid.add(text);
                }
            }
        }

        JScrollPane GridParent = new JScrollPane(CellGrid);
        JScrollPane terminalParent = new JScrollPane(terminalIO);

        terminal_grid.setTopComponent(GridParent);
        terminal_grid.setBottomComponent(terminalParent);
        terminal_grid.setDividerLocation(0.7);


        frame.getContentPane().add(terminal_grid, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }


    public void terminal_init(){
        terminalIO.setLineWrap(true);
        terminalIO.setPreferredSize(new Dimension(1920, 200));
        terminalIO.setBorder(BorderFactory.createLineBorder(Color.BLUE,3));

        terminal_grid.add(terminalIO);
        terminal_grid.setBottomComponent(terminalIO);

        frame.getContentPane().add(terminal_grid, BorderLayout.CENTER);
    }

    public void create_header(int cols){
        for(int i =0; i<cols;i++){
            String text = "";
            int index = 'A';
            if(i>25){
                index = index+i-26;
                char car = ((char)index);
                text = "A"+car;
            }else{
                index = index+i;
                char car = ((char)index);
                text = ""+car;
            }

            
            JTextField header = new JTextField(text);

            CellGrid.add(header);

        }

    }
}
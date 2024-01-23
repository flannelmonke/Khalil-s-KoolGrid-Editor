package frameManipulation;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import IO.file_loader;
import terminalIO.terminal;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.nio.file.Path;

public class frame_maker {
    // Application frame
    JFrame frame = new JFrame("Khalil's KoolGrid Editor");

    JSplitPane cell_terminal = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    // Cell Grid area
    JPanel CellGrid = new JPanel(new GridLayout(501, 31, 0, 0));

    terminal term = new terminal();

    // Header buttons
    public Button open_file = new Button("Open File");
    public Button load = new Button("Load File");
    public Button save_file = new Button("Save File");
    public Button save_as = new Button("Save As");
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
        Path icon_path = Path.of("lib/assets/monkey-98455_1920.png");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Image icon = Toolkit.getDefaultToolkit()
                .getImage(icon_path.toAbsolutePath().toString());
        frame.setIconImage(icon);

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));

        open_file.setPreferredSize(new Dimension(100, 40));
        load.setPreferredSize(new Dimension(100, 40));
        save_file.setPreferredSize(new Dimension(100, 40));
        new_window.setPreferredSize(new Dimension(100, 40));
        new_tab.setPreferredSize(new Dimension(100, 40));
        save_as.setPreferredSize(new Dimension(100, 40));

        header.add(open_file);
        header.add(save_file);
        header.add(save_as);
        header.add(load);
        header.add(new_window);
        header.add(new_tab);

        frame.getContentPane().add(header, BorderLayout.NORTH);
        terminal_init();
        cell_terminal.setDividerLocation(0.7);
    }

    public void init() {
        frame.setVisible(true);
    }

    public void load(String path) {

        CellGrid.removeAll();

        file_loader reader = new file_loader();
        int rows = 500;
        int cols = 30;
        if (path != null) {
            reader = new file_loader(path);
            rows = reader.rows;
            cols = reader.cols;
        }
        create_cell_index(cols);
        for (int i = 0; i < rows; i++) {
            // create thing that goes on the side
            JTextField side = new JTextField();
            side.setText("" + (i + 1));
            side.setPreferredSize(new Dimension(50, 25));
            side.setEditable(false);

            CellGrid.add(side);
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
        GridParent.setPreferredSize(new Dimension(800, 800));
        cell_terminal.setTopComponent(GridParent);
        terminal_init();

        frame.getContentPane().add(cell_terminal, BorderLayout.CENTER);
        frame.pack();
        cell_terminal.setDividerLocation(0.7);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    public void terminal_init() {
        term.setPreferredSize(new Dimension(800, 100));
        cell_terminal.setBottomComponent(term);
    }

    public void create_cell_index(int cols) {
        JTextField corner = new JTextField();
        corner.setPreferredSize(new Dimension(50, 25));
        corner.setEditable(false);

        CellGrid.add(corner);

        for (int i = 0; i < cols; i++) {
            String text = "";
            int index = 'A';
            if (i > 25) {
                index = index + i - 26;
                char car = ((char) index);
                text = "A" + car;
            } else {
                index = index + i;
                char car = ((char) index);
                text = "" + car;
            }

            JTextField header = new JTextField(text);
            header.setPreferredSize(new Dimension(50, 25));
            header.setEditable(false);

            CellGrid.add(header);

        }

    }

}
package frameManipulation;

import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class frame_manager {
    public ArrayList<frame_maker> frames = new ArrayList<>();
    public static int number_of_frames = 0;

    public frame_manager(frame_maker frame) {
        create_new_frame(frame);
    }

    public void create_new_frame(frame_maker frame) {
        frames.add(frame);
        number_of_frames++;

        open_file_script(frames.get(number_of_frames - 1).open_file);
        new_window_script(frames.get(number_of_frames - 1).new_window);
        load_file_script(frames.get(number_of_frames - 1).load, frames, (number_of_frames - 1));
        terminal_return(frames.get(number_of_frames - 1).term.getCommandInputField());

        frames.get(number_of_frames - 1).init();
    }

    public void open_file_script(Button open_file) {
        open_file.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == open_file) {

                    JFileChooser fileChooser = new JFileChooser();

                    int response = fileChooser.showOpenDialog(null);
                    if (response == JFileChooser.APPROVE_OPTION) {
                        create_new_frame(new frame_maker(fileChooser.getSelectedFile().getAbsolutePath()));
                    }
                }
            }
        });
    }

    public void new_window_script(Button new_window) {
        new_window.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                create_new_frame(new frame_maker(null));
            }
        });
    }

    public void load_file_script(Button load, ArrayList<frame_maker> frames, int index) {
        load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == load) {
                    JFileChooser fileChooser = new JFileChooser();
                    int response = fileChooser.showOpenDialog(null);

                    if (response == JFileChooser.APPROVE_OPTION) {
                        frames.set(index, new frame_maker(fileChooser.getSelectedFile().getAbsolutePath()));
                    }
                }
            }

        });
    }

    public void terminal_return(JTextField terminal) {
        terminal.addKeyListener(new KeyListener() {
            // Key code for return key is 10
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (terminal.getText().length() > 0 && e.getKeyCode() == 10) {
                    String command = terminal.getText();
                    terminal.setText("");
                    frames.get(number_of_frames - 1).term.executeCommand(command);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

        });
    }
}

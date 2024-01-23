package test;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;

public class textbox {
            public static void main(String[] args) {
            JTextPane textPane = new JTextPane();
            StyledDocument doc = textPane.getStyledDocument();
    
            SimpleAttributeSet nonEditable = new SimpleAttributeSet();
            nonEditable.addAttribute(NonEditableAttribute, true);
    
            try {
                doc.insertString(doc.getLength(), "Non-editable text\n", nonEditable);
                doc.insertString(doc.getLength(), "Editable text\n", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
    
            textPane.setCaretPosition(doc.getLength());

            textPane.addKeyListener(new KeyAdapter(){
                @Override
                public void keyTyped(KeyEvent e) {
                    AttributeSet attrs = doc.getCharacterElement(textPane.getCaretPosition() - 1).getAttributes();
                    if (attrs.containsAttribute(NonEditableAttribute, true)) {
                        e.consume();
                    }
                }
            });

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new JScrollPane(textPane));
            frame.setSize(400, 300);
            frame.setVisible(true);
        }
    
        private static final Object NonEditableAttribute = new Object();
    } 

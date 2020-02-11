package adv.panels;

import adv.main.Window;
import adv.views.HashView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HashPanel extends Panel {

    protected final int MAXITERATOR = 2;


    protected HashView hashView;
    protected JButton insertButton;
    protected JTextField insertField;
    protected JButton deleteButton;
    protected JTextField deleteField;
    protected JTextField findField;
    protected JButton findButton;

    JLabel hashtype;

    protected boolean hashingIntegers = true;


    public HashPanel(Window window) {
        super(window);


        Box box = Box.createHorizontalBox();


        insertField = new JTextField("");
        insertField.setMaximumSize(new Dimension(100, 30));
        insertField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(HashView.INSERT, insertField);
            }
        });

        box.add(insertField);


        insertButton = new JButton("insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(HashView.INSERT, insertField);

            }
        });

        box.add(insertButton);


        findField = new JTextField("");
        findField.setMaximumSize(new Dimension(100, 30));
        findField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(HashView.FIND, findField);

            }
        });

        box.add(findField);


        findButton = new JButton("find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(HashView.FIND, findField);

            }
        });

        box.add(findButton);

        deleteField = new JTextField("");
        deleteField.setMaximumSize(new Dimension(100, 30));
        deleteField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(HashView.DELETE, deleteField);
            }
        });

        box.add(deleteField);


        deleteButton = new JButton("delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(HashView.DELETE, deleteField);
            }
        });

        box.add(deleteButton);


        hashtype = new JLabel(" Hashing Integers ");
        hashtype.setForeground(Color.BLUE);

        box.add(hashtype);



        this.add(box, BorderLayout.NORTH);
        this.add(view = hashView = new HashView(), BorderLayout.CENTER);
        setUpAnimationPanel(hashView);

    }


    protected void ListenerBody(int action, JTextField field) {
        if (field.getText().length() != 0) {
            if (hashingIntegers) {
                int elem = extractInt(field.getText());
                if (elem < Integer.MAX_VALUE) {
                    beginAnimation(action, new Integer(elem));
                }
            } else {
                if (field.getText().length() > 0) {
                    beginAnimation(action, extractString(field.getText(),20));
                }
            }
            field.setText("");
            changeDone();
        }


    }


    public void DisableSpecific() {

        insertButton.setEnabled(false);
        insertField.setEnabled(false);
        findButton.setEnabled(false);
        findField.setEnabled(false);
        deleteButton.setEnabled(false);
        deleteField.setEnabled(false);
        //swapinputButton.setEnabled(false);

    }


    public void EnableSpecific() {

        insertButton.setEnabled(true);
        insertField.setEnabled(true);
        findButton.setEnabled(true);
        findField.setEnabled(true);
        deleteButton.setEnabled(true);
        deleteField.setEnabled(true);
        //swapinputButton.setEnabled(true);


    }

    // Persistence

    public void setData(Object data) {
        hashView.setData(data);
    }

    public Object getData() {
        return hashView.getData();
    }

}

package adv.panels;

import adv.main.Window;
import adv.utility.InputConstraints;
import adv.views.HeapView;
import edu.usfca.xj.appkit.utils.XJAlert;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeapPanel extends Panel implements DocumentListener {

    protected HeapView heapView;
    protected JTextField insertfield;
    private Document insertFieldDocument;
   // protected JButton insertbutton;
   private JLabel insertLabel;
    protected JButton removemaxbutton;
    protected JButton buildheapbutton;
    protected JButton deletebutton;

    private int nonEmptyField;
    private String nonEmptyFieldInput;

    public HeapPanel(Window window) {

        super(window);

       //paused = false;
        //running = false;
        Box box = Box.createHorizontalBox();

        insertLabel = new JLabel("Insert: ");
        box.add(insertLabel);
        insertfield = new JTextField("");
        insertFieldDocument = insertfield.getDocument();
        insertFieldDocument.addDocumentListener(this);
        insertfield.setMaximumSize(new Dimension(50, 30));
        //      field.setMaximumSize(new Dimension(50, 30));
        insertfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (insertfield.getText().length() != 0) {
                        DisableSpecific();
                        goButton.requestFocusInWindow();
                        goButton.doClick();
                        int insertelement = extractInt(insertfield.getText(), 3);
                        if (insertelement < 1000) {
                        beginAnimation(heapView.INSERT, new Integer(insertelement));
                }
                insertfield.setText("");
                changeDone();

                }
            else {
                    showInvalidInputDialog();
                }
                EnableSpecific();
            }
                            });
        box.add(insertfield);


        removemaxbutton = new JButton("Remove MAX");
        removemaxbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                beginAnimation(heapView.REMOVEMAX);
                changeDone();
            }
        });
        box.add(removemaxbutton);

        deletebutton = new JButton("Delete ");
        deletebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                beginAnimation(heapView.DELETEHEAP);
                changeDone();
            }
        });
        box.add(deletebutton);

        buildheapbutton = new JButton("Build Heap");
        buildheapbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                beginAnimation(heapView.BUILDHEAP);
                changeDone();
            }
        });
        box.add(buildheapbutton);

        this.add(box, BorderLayout.NORTH);
        this.add(view = heapView = new HeapView(), BorderLayout.CENTER);
        setUpAnimationPanel(heapView);


    }


    public void DisableSpecific() {
        buildheapbutton.setEnabled(false);
        deletebutton.setEnabled(false);
        //insertbutton.setEnabled(false);
        removemaxbutton.setEnabled(false);
        insertfield.setEnabled(false);
    }



    public void EnableSpecific() {
        buildheapbutton.setEnabled(true);
        deletebutton.setEnabled(true);
       // insertbutton.setEnabled(true);
        removemaxbutton.setEnabled(true);
        insertfield.setEnabled(true);
        insertfield.setRequestFocusEnabled(true);
    }



    // Persistence

    public void setData(Object data) {
        heapView.setData(data);
    }

    public Object getData() {
        return heapView.getData();
    }
    @Override
    protected void setUpGoButton(Box buttonsContainer) {
        goButton = new JButton("Go");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (!userInputValid(nonEmptyFieldInput) || nonEmptyField < 0) {
                    showInvalidInputDialog();
                    return;
                }

                if (!running) {
                    beginAnimation(nonEmptyField, nonEmptyFieldInput);
                    goButton.setText("Pause");
                } else {

                    if (!paused) {
                        paused = true;
                        view.pause();
                        goButton.setText("Go");
                        setUpAnimationPaused();
                    } else {
                        paused = false;
                        view.go();
                        goButton.setText("Pause");
                        setUpAnimationRunning();
                    }
                }
            }


        });

    } @Override
        protected void setUpSkipButton (Box buttonsContainer){
            skipButton = new JButton("Skip");
            skipButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    if (!running) {
                        beginAnimation(nonEmptyField, nonEmptyFieldInput);
                        view.skip();
                    } else {
                        if (!paused) {
                            view.skip();
                        } else {
                            view.skip();
                            paused = false;
                            view.go();
                        }

                    }
                    changeDone();
                }
            });
            buttonsContainer.add(skipButton);
        }

    @Override
    protected void setUpRestartButton(Box buttonsContainer) {
        super.setUpRestartButton(buttonsContainer);
        buttonsContainer.remove(restartButton);
    }

    private void showInvalidInputDialog() {
        XJAlert.display(window.getJavaContainer(), "Invalid Input", "Please enter a number or a list of comma separated numbers\n\n" + "Note: Number(s) can't be be greater than " + InputConstraints.MAX_INPUT_VALUE);
    }

    private boolean userInputValid(String nonEmptyFieldInput) {
        return InputConstraints.isValidNumber(nonEmptyFieldInput) || InputConstraints.isValidNumberList(nonEmptyFieldInput);
    }

    private void clearinput() {
        insertfield.setText("");
    }
    protected void endAnimation(){
        super.endAnimation();
        clearinput();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        Document doc = event.getDocument();

        try {
            String currentFieldInput = doc.getText(0, doc.getLength());
            nonEmptyFieldInput = currentFieldInput;
            if (currentFieldInput.length() == 0) {
                enableSpecificButtons();
                nonEmptyField = -1;
                disableGoAndSkip();
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}

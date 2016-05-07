package MVC_Package.View;

import MVC_Package.model.MP3NameChangerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Morgan on 5/1/2016.
 */
public class PracticeView extends JFrame {

    private MP3NameChangerModel model;
    private JButton helloButton;
    private JLabel messageLbl;


    public PracticeView(MP3NameChangerModel model){
        super("MVC Demo");
        this.model = model;

        helloButton = new JButton("Click Me!");
        messageLbl = new JLabel(" ");

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 1;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        add(helloButton, gc);

        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 1;
        gc.gridy = 2;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        add(messageLbl, gc);

        helloButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                messageLbl.setText(messageLbl.getText() + " Hello!");
            }
        });

        setSize(600,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

}

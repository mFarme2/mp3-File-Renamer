package MVC_Package.Controller;

import InterfAndObjs.updateObj;
import MVC_Package.View.MP3NameChangerView;
import MVC_Package.model.MP3NameChangerModel;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;

/**
 * Created by Morgan on 5/1/2016.
 */
public class MP3NameChangerController {
    private MP3NameChangerModel model;
    private MP3NameChangerView view;

    public MP3NameChangerController(){
    }

    public void setModelAndView(MP3NameChangerView view, MP3NameChangerModel model){
        this.view = view;
        this.model = model;
    }

    /**
     * Opens a file chooser dialog. If a valid directory is chosen, then the working path will be updated in the model.
     */
    public void selectRootDir(){
        while (true) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select Root Directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(view.getContentPane()) == JFileChooser.APPROVE_OPTION) {
                model.setWorkingDir(chooser.getSelectedFile());
                break;
            } else {
                JOptionPane.showMessageDialog(null,"Need a root!\n please select a root!");
            }
        }
    }

    /**
     * Updates the view that the working directory has changed.
     */
    public void updateView(updateObj obj){
        view.update(obj);
    }
}

package MVC_Package.Controller;

import InterfAndObjs.updateObj;
import MVC_Package.View.MP3NameChangerView;
import MVC_Package.Model.MP3NameChangerModel;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Created by Morgan Farmer on 5/1/2016.
 */
public class MP3NameChangerController {
    private MP3NameChangerModel model;
    private MP3NameChangerView view;

    public MP3NameChangerController(){
    }

    public void setModelAndView(MP3NameChangerView view, MP3NameChangerModel model){
        this.view = view;
        this.model = model;

        updateMusicLoc();
    }

    /**
     * Opens a file chooser dialog. If a valid directory is chosen, then the working path will be updated in the Model.
     */
    public void selectRootDir(){
        File f;
        if (model.getWorkingDir() == null){
            f = new File(".");
        } else {
            f = model.getWorkingDir().toFile();
        }
        while (true) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(f);
            chooser.setDialogTitle("Select Root Directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(view.getContentPane()) == JFileChooser.APPROVE_OPTION) {
                ChangeWorkingDir(chooser.getSelectedFile().toPath());
                break;
            } else {
                JOptionPane.showMessageDialog(null,"Need a root!\n please select a root!");
            }
        }
    }

    /**
     * looks for musicRoot.txt file that contains the last user defined music root file. if no file exists then
     * it creates one.
     */
    public void updateMusicLoc(){
        File file = new File(model.getDirectoryFileTxt());
        if (file.isFile()){
            try {
                Scanner read = new Scanner(file);
                if (read.hasNext()) {
                    //musicRoot file has something in it
                    File f = new File(read.next());

                    if (f.isDirectory()) {
                        //line of file is real directory
                        ChangeWorkingDir(f.toPath());
                    } else {
                        //line is not a proper directory
                        System.out.println("Not a real directory");
                        selectRootDir();
                    }
                } else {
                    //no musicRoot file so prompt user to select a Root.
                    selectRootDir();
                }
                read.close();
            } catch (FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }
        } else {
            //this file is not made yet. Prompt user to select music root
            System.out.println("File not found");
            selectRootDir();
        }
    }

    /**
     * Takes in a file f and updates the models working directory variable.
     * Then it updates the text file with the working music directory
     * Next updates the list of files that are of the mp3 type
     * Finally tells the view(s) to update.
     */
    public void ChangeWorkingDir(Path f){
        model.setWorkingDir(f);
        try{
            //Delete old file...
            File file = new File(model.getDirectoryFileTxt());
            file.delete();

            //Make new file with new music file directory
            File newFile = new File(model.getDirectoryFileTxt());
            PrintWriter writer = new PrintWriter(newFile);
            writer.println(model.getWorkingDir().toAbsolutePath());
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        //get all mp3 files and update views
        model.getMp3FilesList().clear();
        updateMp3Paths(model.getWorkingDir().toString());

        updateView(new updateObj(model.getWorkingDir(), model.getMp3FilesList()));
    }


    private void updateMp3Paths(String path) {
        File[] files = new File(path).listFiles();

        if (files == null) return;

        for (File f: files){
            if (f.isDirectory()){
                updateMp3Paths(f.getAbsolutePath());
            } else if (f.toString().endsWith(".mp3")){
                model.addFileToList(f.toPath());
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

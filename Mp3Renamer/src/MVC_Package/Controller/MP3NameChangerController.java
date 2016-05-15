package MVC_Package.Controller;

import InterfAndObjs.updateObj;
import MVC_Package.View.MP3NameChangerView;
import MVC_Package.Model.MP3NameChangerModel;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

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

    private boolean modelAndViewSetYet = false; //controller must set model and view before any methods can be used.

    public MP3NameChangerController(){
    }

    public void setModelAndView(MP3NameChangerView view, MP3NameChangerModel model){
        this.view = view;
        this.model = model;
        modelAndViewSetYet = true;
    }

    public void updateModel(){
        updateMusicLoc();
    }

    /**
     * Checks for variables needed to run the controller. If any of the nessesary dependencies are not met,
     * the program will shut down and tell the user why.
     */
    private void checkForNeededVars(){
        if (!this.modelAndViewSetYet){
            System.out.println("Must set View and Controller");
            System.exit(-1);
        }
    }

    /**
     * Opens a file chooser dialog. If a valid directory is chosen, then the working path will be updated in the Model.
     */
    public void selectRootDir(){
        checkForNeededVars();

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
        checkForNeededVars();

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
        checkForNeededVars();

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

    /**
     * recursively traverses through the music root currently selected and adds files that are of mp3 type
     */
    private void updateMp3Paths(String path) {
        checkForNeededVars();

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
        checkForNeededVars();

        view.update(obj);
    }

    /**
     * ConvertMp3FileName takes in a string that holds a pattern a cycles through the current
     * arrayList of mp3Files and changes them according to the pattern. This will make use of the
     * jid3lib library to grab metadata from the audio files.
     */
    public void convertMp3FileName(String pattern){
        checkForNeededVars();

        for (Path p: model.getMp3FilesList()) {
            try {
                MP3File file = new MP3File(p.toFile());
                String pathToFolder = file.getMp3file().getParent() + "\\";

                if (file.hasID3v1Tag() && (!file.getID3v1Tag().getSongTitle().equals("")) && (!file.getID3v1Tag().getTrackNumberOnAlbum().equals("0"))){
                    String newName = "";
                    int trackNum = Integer.parseInt(file.getID3v1Tag().getTrackNumberOnAlbum());
                    switch(pattern){
                        case "## - Song Title.mp3":
                            newName = String.format("%02d",trackNum) + " - " + file.getID3v1Tag().getSongTitle() + ".mp3";
                            break;
                        case "### - Song Title.mp3":
                            newName = String.format("%03d",trackNum) + " - " + file.getID3v1Tag().getSongTitle() + ".mp3";
                            break;
                        case "SongTitle.mp3":
                            newName = file.getID3v1Tag().getSongTitle() + ".mp3";
                            break;
                        default:
                            //should never happen as the view I made uses a comboBox to select
                            //format, but if someone adds more options, it should will show error
                            JOptionPane.showMessageDialog(null,"Error!\n Unrecognized rename format!");
                            break;
                    }
                    //rename file if a pattern matches a predefined pattern and the newName is generated.
                    if (!newName.equals("")){
                        File newFile = new File(pathToFolder + newName);
                        try {
                            file.getMp3file().renameTo(newFile);
                        } catch (NullPointerException e){
                            System.out.println(e.getMessage());
                        } catch (SecurityException e){
                            System.out.println(e.getMessage());
                        }
                    }

                } else {
                    System.out.println("File " + file.getMp3file().getName() + " is missing required ID3v1 Tags");
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (TagException e) {
                System.out.println(e.getMessage());
            }

            model.getMp3FilesList().clear();
            updateMp3Paths(model.getWorkingDir().toString());
            updateView(new updateObj(model.getMp3FilesList()));
        }
    }
}

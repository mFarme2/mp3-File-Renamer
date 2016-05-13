package MVC_Package.Model;

import MVC_Package.Controller.MP3NameChangerController;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Morgan Farmer on 5/1/2016.
 */

/**
 * Model should retrieve and store all the paths of files with .mp3 file extension. It should also keep
 * track of a text file that holds a reference to the most recent working music root file.
 */
public class MP3NameChangerModel {
    private MP3NameChangerController controller;

    private static final String DIRECTORY_FILE_TXT = "musicRoot.txt";
    private Path workingDir;
    private ArrayList<Path> mp3FilesList = new ArrayList<>();

    public MP3NameChangerModel(MP3NameChangerController controller){
        this.controller = controller;
    }

    public ArrayList<Path> getMp3FilesList() {
        return mp3FilesList;
    }

    public void addFileToList(Path p){
        mp3FilesList.add(p);
        System.out.println("Added: " + p.getFileName());
    }

    public static String getDirectoryFileTxt() {
        return DIRECTORY_FILE_TXT;
    }

    public Path getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(Path newWorkingDir){
        workingDir = newWorkingDir;
    }

}

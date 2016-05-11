package MVC_Package.Model;

import InterfAndObjs.updateObj;
import MVC_Package.Controller.MP3NameChangerController;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Morgan Farmer on 5/1/2016.
 */

/**
 * Model should retrieve and store all the paths of files with .mp3 file extension. It should also keep
 * track of a text file that holds a reference to the most recent working music root file.
 */
public class MP3NameChangerModel {
    private MP3NameChangerController controller;
    private static final String DIRTXT = "musicRoot.txt";

    private Path workingDir;
    private ArrayList<Path> mp3FilesList = new ArrayList<>();

    public MP3NameChangerModel(MP3NameChangerController controller){
        this.controller = controller;
    }

    public ArrayList<Path> getAllMp3Paths(){
        return mp3FilesList;
    }

    public void update(){
        //Things to do upon start up. Need to check for location file, if none make one then prompt user for root
        //once we know music file location, we need to walk the file and take all .mp3 files. update the view.
        updateMusicLoc();
        mp3FilesList.clear();
        updateMp3Paths(workingDir.toString());
    }

    private void updateMp3Paths(String path) {
        File[] files = new File(path).listFiles();

        if (files == null) return;

        for (File f: files){
            if (f.isDirectory()){
                updateMp3Paths(f.getAbsolutePath());
            } else if (f.toString().endsWith(".mp3")){
                mp3FilesList.add(f.toPath());
            }
        }
    }

    /**
     * looks for musicRoot.txt file that contains the last user defined music root file. if no file exists then
     * it creates one.
     */
    private void updateMusicLoc(){
        File file = new File(DIRTXT);
        if (file.isFile()){
            try {
                Scanner read = new Scanner(file);
                if (read.hasNext()) {
                    //musicRoot file has something in it
                    File f = new File(read.next());

                    if (f.isDirectory()) {
                        //line of file is real directory
                        workingDir = f.toPath();
                        controller.updateView(new updateObj(workingDir));
                    } else {
                        //line is not a proper directory
                        System.out.println("Not a real directory");
                        controller.selectRootDir();
                    }
                } else {
                    //no musicRoot file so prompt user to select a Root.
                    controller.selectRootDir();
                }
                read.close();
            } catch (FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }
        } else {
            //this file is not made yet. Prompt user to select music root
            System.out.println("File not found");
            controller.selectRootDir();
        }
    }

    /**
     * Takes in a file f and updates models working directory variable.
     * Then it updates the text file with the working music directory
     * Then tells the controller to update view(s).
     */
    public void setWorkingDir(Path f){
        this.workingDir = f;
        try{
            //Delete old file...
            File file = new File(DIRTXT);
            file.delete();

            //Make new file with new music file directory
            File newFile = new File(DIRTXT);
            PrintWriter writer = new PrintWriter(newFile);
            writer.println(workingDir.toAbsolutePath());
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        //get all mp3 files and update views
        mp3FilesList.clear();
        updateMp3Paths(workingDir.toString());
        controller.updateView(new updateObj(workingDir, mp3FilesList));
    }
}

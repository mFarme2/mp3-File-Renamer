package MVC_Package.model;

import InterfAndObjs.updateObj;
import MVC_Package.Controller.MP3NameChangerController;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Morgan on 5/1/2016.
 */
public class MP3NameChangerModel {
    private static final String DIRTXT = "musicRoot.txt";
    private File workingDir;
    private ArrayList<Path> mp3FilesList;
    private MP3NameChangerController controller;

    public MP3NameChangerModel (){
        //Default constructor (never used)
    };

    public MP3NameChangerModel(MP3NameChangerController controller){
        this.controller = controller;
    }

    public void addPath(Path path){
        mp3FilesList.add(path);
    }

    public ArrayList<Path> getAllMp3Paths(){
        return mp3FilesList;
    }

    public void update(){
        //Things to do upon start up. Need to check for location file, if none make one then prompt user for root
        //once we know music file location, we need to walk the file and take all .mp3 files. update the view.
        checkForMusicLoc();
    }

    /**
     * looks for musicRoot.txt file that contains the last user defined music root file. if no file exists then
     * it creates one and
     */
    private void checkForMusicLoc(){
        File file = new File("musicRoot.txt");
        if (file.isFile()){
            try {
                Scanner read = new Scanner(file);
                if (read.hasNext()) {
                    workingDir = new File(read.next());
                } else {
                    controller.selectRootDir();
                }
                controller.updateView(new updateObj(workingDir));
                read.close();
            } catch (FileNotFoundException ex){
                System.out.println(ex.getMessage());
            }
        } else {
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println("Blah");
                writer.close();
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Takes in a file f and updates models working directory variable.
     * Then it updates the text file with the working music directory
     * Then tells the controller to update view(s).
     */
    public void setWorkingDir(File f){
        this.workingDir = f;
        try{
            //Delete old file...
            File file = new File(DIRTXT);
            file.delete();

            //Make new file with new music file directory
            File newFile = new File(DIRTXT);
            PrintWriter writer = new PrintWriter(newFile);
            writer.println(workingDir.getAbsolutePath());
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        controller.updateView(new updateObj(workingDir));
    }
}

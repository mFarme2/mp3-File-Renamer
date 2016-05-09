package MVC_Package.model;

import MVC_Package.Controller.MP3NameChangerController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Morgan on 5/1/2016.
 */
public class MP3NameChangerModel {

    private File workingDir;
    private String absolutePath = new File("").getAbsolutePath(); //used to work relatively with directory
    private ArrayList<Path> mp3FilesList;
    private MP3NameChangerController controller;

    public MP3NameChangerModel (){

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
                controller.updateWorkingPath(workingDir);
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
     * Takes in a file f and updates models working directory variable. Then tells the controller to update view.
     */
    public void setWorkingDir(File f){
        this.workingDir = f;
        controller.updateWorkingPath(workingDir);
    }
}

package MVC_Package.model;

import MVC_Package.Controller.MP3NameChangerController;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Morgan on 5/1/2016.
 */
public class MP3NameChangerModel {

    private File workingDir;
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
        rootTest();
    }

    private void rootTest(){
        File f = new File("C://Users//Morgan//IdeaProjects//Mp3Renamer//src//MVC_Package//rootLocation");
        System.out.println(f.isFile());
        if (f.isFile()){
            controller.updateWorkingPath(f);
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

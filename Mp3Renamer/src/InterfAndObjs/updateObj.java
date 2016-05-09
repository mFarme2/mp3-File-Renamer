package InterfAndObjs;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Morgan on 5/9/2016.
 */
public class updateObj {
    private boolean hasNewSongDir = false;
    private boolean hasNewMp3Paths = false;
    private File newSongDir = null;
    private ArrayList<Path> newMp3Paths = new ArrayList<>();

    public updateObj(File f){
        newSongDir = f;
        hasNewSongDir = true;
    }

    public updateObj(ArrayList<Path> paths){
        newMp3Paths = paths;
        hasNewMp3Paths = true;
    }

    public updateObj(File f, ArrayList<Path> paths){
        newSongDir = f;
        hasNewSongDir = true;
        newMp3Paths = paths;
        hasNewMp3Paths = true;
    }

    public boolean hasNewDir(){
        return hasNewSongDir;
    }

    public boolean hasNewPaths(){
        return hasNewMp3Paths;
    }

    public void passNewDir(File f){
        newSongDir = f;
        hasNewSongDir = true;
    }

    public void passNewMp3Paths(ArrayList<Path> paths){
        newMp3Paths = paths;
        hasNewMp3Paths = true;
    }

    public File getNewPath(){
        return newSongDir;
    }

    public ArrayList<Path> getNewMp3Paths(){
        return newMp3Paths;
    }
}

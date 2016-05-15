package InterfAndObjs;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Morgan Farmer on 5/9/2016.
 */
public class updateObj {
    private boolean hasNewSongDir = false;
    private boolean hasNewMp3Paths = false;
    private Path newSongDir = null;
    private ArrayList<Path> newMp3Paths = new ArrayList<>();

    public updateObj(Path f){
        newSongDir = f;
        hasNewSongDir = true;
    }

    public updateObj(ArrayList<Path> paths){
        newMp3Paths = paths;
        hasNewMp3Paths = true;
    }

    public updateObj(Path f, ArrayList<Path> paths){
        newSongDir = f;
        hasNewSongDir = true;
        newMp3Paths = paths;
        hasNewMp3Paths = true;
    }

    public void clear(){
        newSongDir = null;
        hasNewSongDir = false;
        newMp3Paths = null;
        hasNewMp3Paths = false;

    }

    public boolean hasNewDir(){
        return hasNewSongDir;
    }

    public boolean hasNewPaths(){
        return hasNewMp3Paths;
    }

    public void passNewDir(Path f){
        newSongDir = f;
        hasNewSongDir = true;
    }

    public void passNewMp3Paths(ArrayList<Path> paths){
        newMp3Paths = paths;
        hasNewMp3Paths = true;
    }

    public Path getNewPath(){
        return newSongDir;
    }

    public ArrayList<Path> getNewMp3Paths(){
        return newMp3Paths;
    }
}

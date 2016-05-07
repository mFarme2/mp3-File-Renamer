package EverythingElse;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;
import org.farng.mp3.id3.AbstractID3v1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

import static java.nio.file.StandardCopyOption.*;

/**
 * Created by Morgan on 4/18/2016.
 */

public class Trizle {
    public static void main(String args[]){
        String newName;
        String number;
        File rootDirectory = new File("C:\\Users\\Morgan\\Desktop\\test");
        String mp3Location = "C:\\Users\\Morgan\\Desktop\\test\\abcdef.mp3";

        try {
            MP3File song = new MP3File(mp3Location);
            if (song.hasID3v1Tag()){
                AbstractID3v1 id3v2tag = song.getID3v1Tag();
                System.out.println("Original File name :" + song.getMp3file().getName());

                if (Double.parseDouble(id3v2tag.getTrackNumberOnAlbum()) < 10){
                    number = "0" + id3v2tag.getTrackNumberOnAlbum();
                } else {
                    number = id3v2tag.getTrackNumberOnAlbum();
                }

                newName =  number + " - " + id3v2tag.getSongTitle() + ".mp3";

                System.out.println("New File Name: " + newName);
                Path newPath = Paths.get(rootDirectory.getAbsolutePath() + "\\" + newName);

                //make a copy of old file with new file name
                Files.copy(song.getMp3file().toPath(), newPath, REPLACE_EXISTING);
                //delete old file
                Files.delete(song.getMp3file().toPath());
                System.out.println("File Renamed!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (TagException e) {
            e.printStackTrace();
        }

    }
}

package org.mm3.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Simulates connection to MM3 while retreiving recorded MM3 bytes from a file
 * <p/>
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/12/15
 * Time: 11:25 AM
 */
public class FileBasedEventGenerator extends AbstractEventGenerator {

    protected String fileName;

    public int getPlaybackFrequency() {
        return playbackFrequency;
    }

    public void setPlaybackFrequency(int playbackFrequency) {
        this.playbackFrequency = playbackFrequency;
    }

    protected int playbackFrequency = 500;

    public FileBasedEventGenerator(String fileName) {
        this.fileName = fileName;
    }

    public void play() throws IOException {

        Runnable playbackTask;


        playbackTask = () -> {

        };

    }

    protected Runnable filePlaybackExecutor() throws IOException{

        File file = new File(fileName);
        byte bFile[] = new byte[(int) file.length()];
        FileInputStream fin = new FileInputStream(file);
        fin.read(bFile);


        return null;
    }


}

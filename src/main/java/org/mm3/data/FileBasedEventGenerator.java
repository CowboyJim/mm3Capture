package org.mm3.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Simulates connection to MM3 while retreiving recorded MM3 bytes from a file
 * <p>
 * Created with IntelliJ IDEA.
 * User: CowboyJim
 * Date: 7/12/15
 * Time: 11:25 AM
 */
public class FileBasedEventGenerator extends BaseEventGenerator {

    public static final int PACKET_SIZE = 39;
    protected Logger LOG = LoggerFactory.getLogger(FileBasedEventGenerator.class);
    protected String fileName;

    protected File file;
    protected int playbackFrequency = 500;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getPlaybackFrequency() {
        return playbackFrequency;
    }

    public void setPlaybackFrequency(int playbackFrequency) {
        this.playbackFrequency = playbackFrequency;
    }

    public void play() throws IOException {

        if (file == null) {
            file = new File(fileName);
        }
        byte bFile[] = new byte[(int) file.length()];
        FileInputStream fin = new FileInputStream(file);
        fin.read(bFile);

        Runnable filePlaybackExecutor = () -> {

            byte[] bufPacket = new byte[PACKET_SIZE];
            int bFilePosition = 0;
            int bFileLength = bFile.length;
            boolean bFileEmpty = false;
            int size = PACKET_SIZE;

            while (!bFileEmpty) {

                if ((bFileLength - bFilePosition) < PACKET_SIZE) {
                    size = bFileLength - bFilePosition;
                    bFileEmpty = true;
                }
                System.arraycopy(bFile, bFilePosition, bufPacket, 0, size);
                bFilePosition += PACKET_SIZE;

                this.notifyObservers(bufPacket);

                try {
                    Thread.sleep(playbackFrequency);
                } catch (InterruptedException e) {
                    LOG.error("Thread Interrupted", e);
                }
            }
        };

        new Thread(filePlaybackExecutor).start();

    }


}

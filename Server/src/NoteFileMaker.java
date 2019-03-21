import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;

/**
 * The NoteFileMaker class writes the notes given to it, to a file containing the note, on or off
 * and its start or stop time, in milliseconds.
 *
 * @author Luke Sykes
 * @version 1.0
 * @since 2019-02-22
 */
public class NoteFileMaker {

  String FILE_NAME;
  File file;
  FileWriter fw;
  BufferedWriter bw;

  /**
   * This method creates a new note file and writes the track number to the first line
   *
   * @param FILE_NAME what the new file should be called
   * @param trackNumber the track number the notes are recorded from
   */
  public NoteFileMaker(String FILE_NAME, int trackNumber) {
    try {
      this.FILE_NAME = FILE_NAME;
      this.file = new File(FILE_NAME);
      this.file.createNewFile();
      this.fw = new FileWriter(file);
      this.bw = new BufferedWriter(fw);
      this.bw.write(Integer.toString(trackNumber));
      this.bw.newLine();
      // write track Guitar is played on at top of the file
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Writes each note and its metadata on a new line in the file
   *
   * @param notes A list of notes to be written
   */
  public void writeSong(ArrayList<String> notes) {
    try {
      for (String line : notes) {
        this.bw.write(line);
        this.bw.newLine();
      }
      this.bw.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}

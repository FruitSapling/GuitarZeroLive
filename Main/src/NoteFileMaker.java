import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;

public class NoteFileMaker {

  String FILE_NAME;
  File file;
  FileWriter fw;
  BufferedWriter bw;

  public NoteFileMaker(String FILE_NAME, int trackNumber) {
    try {
      this.FILE_NAME = FILE_NAME;
      this.file = new File(FILE_NAME);
      this.file.createNewFile();
      this.fw = new FileWriter(file);
      this.bw = new BufferedWriter(fw);
      this.bw.write(Integer.toString(trackNumber));
      this.bw.newLine();
      // write track guitar is played on at top of the file
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void writeSong(ArrayList<String> notes) {
    try {
      for (String line : notes) {
        this.bw.write(line);
        this.bw.newLine();
      }
      this.bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

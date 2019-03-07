/*
* Class to read in a midnotes file and store the file in an array of NoteInfo objects.
* Includes methods to access these NoteInfo objects by reading them.
* @author Mark Newell
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class NoteInputStream {
  private File notesFile;
  private NoteInfo[] trackNotes;
  private int trackNumber;
  private int index;

  public NoteInputStream(File notesFile) throws IOException {
    FileFilter filter = (pathname) -> pathname.getPath().endsWith(".midnotes");
    if (filter.accept(notesFile)) {
      this.notesFile = notesFile;
      this.trackNotes = extractNotes();
    }
    else {
      /*TODO: when using this class, if you get this exception, then prompt user for a new file
        or system exit.
       */
      throw new IOException("NoteInputStream only takes .midnotes files");
    }
  }



  /*
  * A method to extract the notes from the .midnotes file and
  * return an array of NoteInfo objects to store the notes for viewing.
  */
  public NoteInfo[] extractNotes() {
    ArrayList<NoteInfo> notes = new ArrayList<>();
    try {
      Reader in = new FileReader(notesFile);
      BufferedReader reader = new BufferedReader(in);

      this.trackNumber = Integer.valueOf(reader.readLine());
      String startLine;

      while((startLine = reader.readLine()) != null) {
        String endLine = reader.readLine();

        String[] splitStartLine = startLine.split(",");
        String[] splitEndLine = endLine.split(",");
        String note = splitStartLine[1];

        int startTime = Integer.valueOf(splitStartLine[2]);
        int endTime = Integer.valueOf(splitEndLine[2]);

        note = note.substring(0,(note.length())-1);
        int string = classifyNotes(note);
        notes.add(new NoteInfo(note,startTime,endTime,string));
      }
    }
    catch (IOException e) {
      System.out.println("Error reading file");
      System.exit(1);
    }

    return notes.toArray(new NoteInfo[notes.size()]);
  }


  /*
  * Method to output the next note in the NoteInfo array.
  * Aiming to make the class similar to other InputStream classes for ease of use.
  */
  public NoteInfo readNote() {
    NoteInfo nextNote = null;
    if (index < this.trackNotes.length) {
      nextNote = this.trackNotes[index];
      index++;
    }
    return nextNote;
  }


  /*
  * A method to bind a note to the appropriate string on the guitar.
  * Takes in a note string and returns the integer index of the appropriate string.
  */
  private int classifyNotes(String note) {

    String stringLeft = "C|C#|Cb|D|D#|Eb";
    String stringCenter = "E|F|F#|Gb|G";
    String stringRight = "G#|Ab|A|A#|Bb|B";

    if (note.matches(stringLeft)) {
      return 0;
    }
    else if (note.matches(stringCenter)) {
      return 1;
    }
    else if (note.matches(stringRight)) {
      return 2;
    }
    return 3;
  }

}

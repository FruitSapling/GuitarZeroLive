/*
* Class to extract the notes from a notes file.
* @author Mark Newell
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class NotesInput {
  private File notesFile;
  private Note[] trackNotes;
  private int trackNumber;
  private int index;

  NotesInput(File notesFile) throws IOException {
    FileFilter filter = (extention) -> extention.getPath().endsWith(".midnotes");
    if (filter.accept(notesFile)) {
      this.notesFile = notesFile;
    }
    else {
      throw new IOException("NotesInput can only take a .midinotes file");
    }

    this.trackNotes = extractNotes();
  }


  public Note[] extractNotes() {
    ArrayList<Note> notes = new ArrayList<>();
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

        notes.add(new Note(note,startTime,endTime));
      }
    }
    catch (IOException e) {
      System.out.println("Error reading file");
    }

    return notes.toArray(new Note[notes.size()]);
  }

  public Note readNote() {
    Note nextNote = null;
    if (index < this.trackNotes.length) {
      nextNote = this.trackNotes[index];
      index++;
    }
    return nextNote;
  }

  public int getTrackNumber() {
    return this.trackNumber;
  }


  /*
  public static void main(String[] args) {
    NotesInput input = new NotesInput(new File("Main/src/Wonderwall.midnotes"));

    Note note;

    while ((note = input.readNote()) != null) {
      long counter = System.currentTimeMillis();
      long duration = note.getDuration();
      System.out.println(note.toString());

      while((System.currentTimeMillis()-counter) < duration) {

      }


    }
  }
  */


}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class NotesInput {
  private File notesFile;
  private NoteInfo[] trackNotes;
  private int trackNumber;
  private int index;

  NotesInput(File notesFile) throws IOException {
    FileFilter filter = (pathname) -> pathname.getPath().endsWith(".midnotes");
    if (filter.accept(notesFile)) {
      this.notesFile = notesFile;
      this.trackNotes = extractNotes();
    }
    else {
      throw new IOException("NotesInput only takes .midnotes files");
    }
  }


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

        int string = classifyNotes(note);
        notes.add(new NoteInfo(note,startTime,endTime,string));
      }
    }
    catch (IOException e) {
      System.out.println("Error reading file");
    }

    return notes.toArray(new NoteInfo[notes.size()]);
  }

  public NoteInfo readNote() {
    NoteInfo nextNote = null;
    if (index < this.trackNotes.length) {
      nextNote = this.trackNotes[index];
      index++;
    }
    return nextNote;
  }


  private int classifyNotes(String note) {
    switch (note) {

    }
    return 1;
  }

}

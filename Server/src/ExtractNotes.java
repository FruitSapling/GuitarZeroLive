import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * This class extracts the notes played by the lead Guitar of a song along with their corresponding
 * time, in milliseconds
 *
 * @author Luke Sykes
 * @version 1.1
 * @since 2019-03-05
 */
public class ExtractNotes {

  private static Sequence seq;
  private static final ArrayList<Integer> nextMessageOf = new ArrayList<>();
  private static int currentTrack = 0;

  public static void main(String[] args) {
    // code below is all for showcase
    makeNotesFile("Wonderwall.mid");
  }

  /**
   * Finds a lead Guitar from the file and then uses this to extract the appropiate notes
   *
   * @param filename the name of the midifile to be played
   */
  public static void makeNotesFile(String filename) {
    try {
      seq = MidiSystem.getSequence(new File("Server/src/resources/" + filename));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    Guitar leadGuitar = findLeadGuitar(findAllGuitars(seq));
    convertMidiToNotes(seq, leadGuitar, filename);
  }


  /**
   * Finds a lead Guitar from the file and then uses this to extract the appropiate notes
   *
   * @param file the name of the midifile to be played
   */
  public static void makeNotesFile(File file) {
    try {
      seq = MidiSystem.getSequence(file);
    } catch (Exception e) {
      System.exit(1);
    }
    Guitar leadGuitar = findLeadGuitar(findAllGuitars(seq));
    convertMidiToNotes(seq, leadGuitar, file.getName());
  }

  /**
   * Iterates over every track, calling the method findGuitarInTrack on each
   *
   * @param seq the midi sequence to iterate through tracks
   * @return a list of Guitar instrument numbers for their respective track
   */
  private static HashMap<Integer, Guitar> findAllGuitars(Sequence seq) {
    Track[] tracks = seq.getTracks();
    HashMap<Integer, Guitar> guitarsInTracks = new HashMap<>();

    for (int track = 0; track < tracks.length; track++) {

      int currentInstrument = -2;
      int currentGuitar = -1;

      for (int j = 0; j < tracks[track].size(); j++) {
        MidiEvent event = tracks[track].get(j);
        MidiMessage msg = event.getMessage();

        if (msg instanceof ShortMessage) {
          final ShortMessage smsg = (ShortMessage) msg;
          final int cmd = smsg.getCommand();
          final int dat1 = smsg.getData1();
          final int chan = smsg.getChannel();

          if (cmd == ShortMessage.PROGRAM_CHANGE) {

            currentInstrument = dat1;
            if (dat1 >= Constants.FirstGuitar
                && dat1 <= Constants.LastGuitar
                && !guitarsInTracks.containsKey(dat1)) {
              Guitar newGuitar = new Guitar(track, chan, dat1);
              guitarsInTracks.put(dat1, newGuitar);
              currentGuitar = dat1;
            }
          } else {
            if (currentGuitar == currentInstrument) {
              guitarsInTracks.get(currentGuitar).incrementNoteCount();
              guitarsInTracks.get(currentGuitar).addNote(noteName(dat1));
            }
          }
        }
      }
    }
    return guitarsInTracks;
  }

  /**
   * Finds the lead Guitar out of the given instrument numbers The most significant Guitar is notes
   * played multipled by range of notes
   *
   * @param instrumentTracks an array of instrument numbers where index corresponds to track number
   * @return the instrument number of the lead Guitar
   */
  private static Guitar findLeadGuitar(HashMap<Integer, Guitar> instrumentTracks) {
    int highestScore = 0;
    int score = 0;
    int key = 0;
    Guitar g;
    for (Map.Entry<Integer, Guitar> entry : instrumentTracks.entrySet()) {
      g = entry.getValue();
      score = (g.getNoteCount() * g.getNotes().size());
      if (score > highestScore) {
        highestScore = score;
        key = entry.getKey();
      }
    }
    return instrumentTracks.get(key);
  }


  /**
   * Returns the name of nth note
   *
   * @param n the note number
   * @return the note name
   */
  private static String noteName(int n) {
    final String[] NAMES =
        {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    final int OCTAVE = (n / 12) - 1;
    final int NOTE = n % 12;
    return NAMES[NOTE] + OCTAVE;
  }

  /**
   * This method recognises changes when playing the midi file and takes appropiate action
   *
   * @param seq the midi file being played
   * @param leadGuitar the track number the lead Guitar is played on
   */
  private static void convertMidiToNotes(Sequence seq, Guitar leadGuitar, String filename) {
    int count = 0;
    int lastTick = 0;
    int tickOfTempoChange = 0;
    double currentTempo = 500000;
    double msb4 = 0;
    double division = seq.getResolution();
    MidiEvent nextEvent;

    // TODO dont do this
    int channelNumber = -2;
    int currentChannel = -1;
    int currentInstrumentNumber = -1;
    NoteFileMaker notes = new NoteFileMaker("./Server/src/resources/" + filename + "notes",
        leadGuitar.getTrackNumber());
    ArrayList<String> noteList = new ArrayList<>();

    for (int track = 0; track < seq.getTracks().length; track++) {
      nextMessageOf.add(0);
    }

    while ((nextEvent = getNextEvent()) != null) {
      int tick = (int) nextEvent.getTick();

      currentChannel = getChannelNumber(nextEvent);

      if (programChange(nextEvent)) {
        currentInstrumentNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
      } else {
        if (noteIsOff(nextEvent)) {
          if (leadGuitar.getChannelNumber() == currentChannel
              && leadGuitar.getTrackNumber() == currentTrack) {
            double time = (msb4 + (((currentTempo / seq.getResolution()) / 1000)
                * (tick - tickOfTempoChange)));
            int noteNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
            noteList.add("OFF," + noteName(noteNumber) + "," + (int) (time + 0.5));
          }
        } else {
          if (noteIsOn(nextEvent)) {
            if (leadGuitar.getChannelNumber() == currentChannel
                && leadGuitar.getTrackNumber() == currentTrack) {
              double time = (msb4 + (((currentTempo / seq.getResolution()) / 1000)
                  * (tick - tickOfTempoChange)));
              int noteNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
              noteList.add("ON," + noteName(noteNumber) + "," + (int) (time + 0.5));
            }
          } else {
            if (changeTemp(nextEvent)) {
              String a = (Integer.toHexString((int) nextEvent.getMessage().getMessage()[3] & 0xFF));
              String b = (Integer.toHexString((int) nextEvent.getMessage().getMessage()[4] & 0xFF));
              String c = (Integer.toHexString((int) nextEvent.getMessage().getMessage()[5] & 0xFF));
              if (a.length() == 1) {
                a = ("0" + a);
              }
              if (b.length() == 1) {
                b = ("0" + b);
              }
              if (c.length() == 1) {
                c = ("0" + c);
              }
              String whole = a + b + c;
              int newTempo = Integer.parseInt(whole, 16);
              double newTime = (currentTempo / seq.getResolution()) * (tick - tickOfTempoChange);
              msb4 += (newTime / 1000);
              tickOfTempoChange = tick;
              currentTempo = newTempo;
            }
          }
        }
      }

    }
    notes.writeSong(findHighPoints(noteList));
  }

  private static ArrayList<String> findHighPoints(ArrayList<String> noteList) {

    int songHighPointLength = 10000;
    int averageNotes = 49;

    LimitedQueue<Integer> zeroPower = new LimitedQueue<Integer>(averageNotes);

    for (int n = 0; n <= averageNotes; n++) {
      zeroPower.add(Integer.MAX_VALUE);
    }

    int size = noteList.size() - 1;
    for (int i = 0; i <= size; i++) {
      String[] line = noteList.get(i).split(",");
      if (line[0].equals("ON")) {
        zeroPower.add(Integer.parseInt(line[2]));
        if (zeroPower.get(averageNotes - 1) - zeroPower.get(0) < songHighPointLength
            && zeroPower.get(averageNotes - 1) - zeroPower.get(0) > 0) {
          noteList.add(i - averageNotes + 1,
              "zero power mode started" + "," + noteList.get(i - averageNotes + 1).split(",")[2]);
          noteList.add(i, "zero power mode finished" + "," + line[2]);
          i += averageNotes;
        }
      }

    }
    return noteList;
  }

  /**
   * Find the next midi event to take place across all tracks
   *
   * @return the next midi event to take place
   */
  private static MidiEvent getNextEvent() {
    ArrayList<MidiEvent> nextEvent = new ArrayList<MidiEvent>();
    ArrayList<Integer> trackOfNextEvent = new ArrayList<Integer>();
    for (int track = 0; track < seq.getTracks().length; track++) {
      if (seq.getTracks()[track].size() - 1 > (nextMessageOf.get(track))) {
        nextEvent.add(seq.getTracks()[track].get(nextMessageOf.get(track)));
        trackOfNextEvent.add(track);
      }
    }
    if (nextEvent.size() == 0) {
      return null;
    }
    int closestMessage = 0;
    int smallestTick = (int) nextEvent.get(0).getTick();
    for (int trialMessage = 1; trialMessage < nextEvent.size(); trialMessage++) {
      if ((int) nextEvent.get(trialMessage).getTick() < smallestTick) {
        smallestTick = (int) nextEvent.get(trialMessage).getTick();
        closestMessage = trialMessage;
      }
    }
    currentTrack = trackOfNextEvent.get(closestMessage);
    nextMessageOf.set(currentTrack, (nextMessageOf.get(currentTrack) + 1));
    return nextEvent.get(closestMessage);
  }

  /**
   * Find the current channel number being played
   *
   * @param event the current event taking place on the midifile
   * @return the channel number being played currently
   */
  private static int getChannelNumber(MidiEvent event) {
    MidiMessage msg = event.getMessage();
    if (msg instanceof ShortMessage) {
      final ShortMessage smsg = (ShortMessage) msg;
      final int chan = smsg.getChannel();
      return chan;
    }
    return -1;
  }

  /**
   * Find if the current event is a program change event
   *
   * @param event the current event taking place on the midifile
   * @return whether the current event is a program change
   */
  private static boolean programChange(MidiEvent event) {
    // TODO make less clumsy
    MidiMessage msg = event.getMessage();
    if (msg instanceof ShortMessage) {
      final long tick = event.getTick();
      final ShortMessage smsg = (ShortMessage) msg;
      final int chan = smsg.getChannel();
      final int cmd = smsg.getCommand();
      final int dat1 = smsg.getData1();

      switch (cmd) {
        case ShortMessage.PROGRAM_CHANGE:
          return true;
        default:
          /* ignore other commands */
          break;
      }
    }
    return false;
  }

  /**
   * Find if the current event is a note off event
   *
   * @param event the current event taking place on the midifile
   * @return whether the current event is a note off
   */
  private static boolean noteIsOff(MidiEvent event) {
    if (Integer.toString((int) event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '8' ||
        (noteIsOn(event) && event.getMessage().getLength() >= 3
            && ((int) event.getMessage().getMessage()[2] & 0xFF) == 0)) {
      return true;
    }
    return false;
  }

  /**
   * Find if the current event is a note off event
   *
   * @param event the current event taking place on the midifile
   * @return whether the current event is a note on
   */
  private static boolean noteIsOn(MidiEvent event) {
    if (Integer.toString(event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '9') {
      return true;
    }
    return false;
  }

  /**
   * Find if there has been a change in tempo during the event
   *
   * @param event the current event taking place on the midifile
   * @return whether the tempo has changed or not
   */
  private static boolean changeTemp(MidiEvent event) {
    if ((int) Integer.valueOf(
        ("" + Integer.toString((int) event.getMessage().getStatus(), 16).toUpperCase().charAt(0)),
        16) == 15
        && (int) Integer.valueOf(
        ("" + ((String) (Integer.toString((int) event.getMessage().getStatus(), 16).toUpperCase()))
            .charAt(1)), 16) == 15
        && Integer.toString((int) event.getMessage().getMessage()[1], 16).toUpperCase().length()
        == 2
        && Integer.toString((int) event.getMessage().getMessage()[1], 16).toUpperCase().equals("51")
        && Integer.toString((int) event.getMessage().getMessage()[2], 16).toUpperCase()
        .equals("3")) {
      return true;
    }
    return false;
  }
}
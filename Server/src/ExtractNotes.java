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
 * @version 2.0
 * @since 2019-03-05
 */
public class ExtractNotes {

  private static Sequence seq;
  private static final ArrayList<Integer> nextMessageOf = new ArrayList<>();
  private static int currentTrack = 0;

    /**
     * Finds a lead Guitar from the file and then uses this to extract the appropiate notes
     * @param file the midifile to be played
     */
    public static void makeNotesFile(File file){
        try{
            seq = MidiSystem.getSequence(file);
        } catch (Exception e){
            e.printStackTrace();
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
            if (dat1 >= Constants.FIRST_GUITAR
                && dat1 <= Constants.LAST_GUITAR
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
    int score;
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
    int tickOfTempoChange = 0;
    double currentTempo = 500000;
    double msb4 = 0;
    MidiEvent nextEvent;

    int currentChannel;
    NoteFileMaker notes = new NoteFileMaker("./Server/src/resources/" + filename + "notes",
        leadGuitar.getTrackNumber());
    ArrayList<String> noteList = new ArrayList<>();

    for (int track = 0; track < seq.getTracks().length; track++) {
      nextMessageOf.add(0);
    }

    while ((nextEvent = getNextEvent()) != null) {
      int tick = (int) nextEvent.getTick();

      currentChannel = getChannelNumber(nextEvent);

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
    notes.writeSong(findHighPoints(noteList));
  }

  /**
   * Find the musical high points in the song which is when zero power will be activated
   * @param noteList All the recorded notes before they are written to the file
   * @return the same arraylist but now with markers to show zero power mode has started and ended
   */
  private static ArrayList<String> findHighPoints(ArrayList<String> noteList) {

    LimitedQueue<Integer> zeroPower = new LimitedQueue<>(Constants.HIGH_POINT_NOTES);

    for (int n = 0; n <= Constants.HIGH_POINT_NOTES; n++) {
      zeroPower.add(Integer.MAX_VALUE);
    }

    int size = noteList.size() - 1;
    for (int i = 0; i <= size; i++) {
      String[] line = noteList.get(i).split(",");
      if (line[0].equals("ON")) {
        zeroPower.add(Integer.parseInt(line[2]));
        if (zeroPower.get(Constants.HIGH_POINT_NOTES - 1) - zeroPower.get(0) < Constants.HIGH_POINT_LENGTH
            && zeroPower.get(Constants.HIGH_POINT_NOTES - 1) - zeroPower.get(0) > 0) {
          noteList.add(i - Constants.HIGH_POINT_NOTES + 1,
              "zero power mode started" + "," + noteList.get(i - Constants.HIGH_POINT_NOTES + 1).split(",")[2]);
          noteList.add(i, "zero power mode finished" + "," + line[2]);
          i += Constants.HIGH_POINT_NOTES;
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
    ArrayList<MidiEvent> nextEvent = new ArrayList<>();
    ArrayList<Integer> trackOfNextEvent = new ArrayList<>();
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
      return smsg.getChannel();
    }
    return -1;
  }

  /**
   * Find if the current event is a note off event
   *
   * @param event the current event taking place on the midifile
   * @return whether the current event is a note off
   */
  private static boolean noteIsOff(MidiEvent event) {
    return Integer.toString(event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '8' ||
        (noteIsOn(event) && event.getMessage().getLength() >= 3
            && ((int) event.getMessage().getMessage()[2] & 0xFF) == 0);
  }

  /**
   * Find if the current event is a note off event
   *
   * @param event the current event taking place on the midifile
   * @return whether the current event is a note on
   */
  private static boolean noteIsOn(MidiEvent event) {
    return Integer.toString(event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '9';
  }

  /**
   * Find if there has been a change in tempo during the event
   *
   * @param event the current event taking place on the midifile
   * @return whether the tempo has changed or not
   */
  private static boolean changeTemp(MidiEvent event) {
    return Integer.valueOf(
        ("" + Integer.toString(event.getMessage().getStatus(), 16).toUpperCase().charAt(0)),
        16) == 15
        && Integer.valueOf(
        ("" + Integer.toString(event.getMessage().getStatus(), 16).toUpperCase()
            .charAt(1)), 16) == 15
        && Integer.toString((int) event.getMessage().getMessage()[1], 16).toUpperCase().length()
        == 2
        && Integer.toString((int) event.getMessage().getMessage()[1], 16).toUpperCase().equals("51")
        && Integer.toString((int) event.getMessage().getMessage()[2], 16).toUpperCase()
        .equals("3");
  }
}

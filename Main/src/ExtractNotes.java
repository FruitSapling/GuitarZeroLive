import java.util.*;
import javax.sound.midi.*;
import java.io.*;
import java.text.*;

public class ExtractNotes {
  static final String FILE = "Main/src/Wonderwall.mid";
  static Sequence seq;
  static int currentTrack = 0;
  static int guitarTrack = -1;
  static ArrayList<Integer> nextMessageOf = new ArrayList<Integer>();

  public static void main(String[] args) {
    try {
      seq = MidiSystem.getSequence(new File(FILE));
    } catch (Exception e) {
      e.printStackTrace();
    }

    int leadGuitar = findLeadGuitar(seq);
    System.out.println("The lead guitar is: " + leadGuitar);
    convertMidiToNotes(seq, leadGuitar);
  }

  public static void convertMidiToNotes(Sequence seq, int leadGuitar) {
    int count = 0;
    int lastTick = 0;
    int tickOfTempoChange = 0;
    double currentTempo = 500000;
    double msb4 = 0;
    double division = seq.getResolution();
    MidiEvent nextEvent;

    NoteFileMaker notes = new NoteFileMaker(FILE + "notes", guitarTrack);
    ArrayList<String> noteList = new ArrayList<String>();

    // set to -1 to show if not changed
    int instrumentNumber = -1;
    int channelNumber = -1;

    for (int track = 0; track < seq.getTracks().length; track++) nextMessageOf.add(0);

    while ((nextEvent = getNextEvent()) != null) {
      int tick = (int) nextEvent.getTick();

      MidiMessage msg = nextEvent.getMessage();
      if (msg instanceof ShortMessage) {
        // final long         tick = evt.getTick();
        ShortMessage smsg = (ShortMessage) msg;
        int currentChannel = smsg.getChannel();
        int cmd = smsg.getCommand();
        int dat1 = smsg.getData1();

        if (cmd == ShortMessage.PROGRAM_CHANGE) {
          instrumentNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
          if (instrumentNumber == leadGuitar) {
            channelNumber = currentChannel;
          }
        }

        if (noteIsOff(nextEvent)) {
          if (channelNumber == currentChannel && guitarTrack == currentTrack) {
            double time =
                (msb4
                    + (((currentTempo / seq.getResolution()) / 1000) * (tick - tickOfTempoChange)));
            int noteNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
            noteList.add("OFF," + noteName(noteNumber) + "," + (int) (time + 0.5));
          }
        }

        if (noteIsOn(nextEvent)) {
          if (channelNumber == currentChannel && guitarTrack == currentTrack) {
            double time =
                (msb4
                    + (((currentTempo / seq.getResolution()) / 1000) * (tick - tickOfTempoChange)));
            int noteNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
            noteList.add("ON," + noteName(noteNumber) + "," + (int) (time + 0.5));
          }
        }
        if (changeTemp(nextEvent)) {
          String a = (Integer.toHexString((int) nextEvent.getMessage().getMessage()[3] & 0xFF));
          String b = (Integer.toHexString((int) nextEvent.getMessage().getMessage()[4] & 0xFF));
          String c = (Integer.toHexString((int) nextEvent.getMessage().getMessage()[5] & 0xFF));
          if (a.length() == 1) a = ("0" + a);
          if (b.length() == 1) b = ("0" + b);
          if (c.length() == 1) c = ("0" + c);
          String whole = a + b + c;
          int newTempo = Integer.parseInt(whole, 16);
          double newTime = (currentTempo / seq.getResolution()) * (tick - tickOfTempoChange);
          msb4 += (newTime / 1000);
          tickOfTempoChange = tick;
          currentTempo = newTempo;
        }
      }
    }
    notes.writeSong(noteList);
  }

  public static boolean noteIsOff(MidiEvent event) {
    if (Integer.toString((int) event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '8'
        || (noteIsOn(event)
            && event.getMessage().getLength() >= 3
            && ((int) event.getMessage().getMessage()[2] & 0xFF) == 0)) return true;
    return false;
  }

  public static boolean noteIsOn(MidiEvent event) {
    if (Integer.toString(event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '9')
      return true;
    return false;
  }

  public static boolean programChange(MidiEvent event) {
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

  public static boolean changeTemp(MidiEvent event) {
    if ((int)
                Integer.valueOf(
                    (""
                        + Integer.toString((int) event.getMessage().getStatus(), 16)
                            .toUpperCase()
                            .charAt(0)),
                    16)
            == 15
        && (int)
                Integer.valueOf(
                    (""
                        + ((String)
                                (Integer.toString((int) event.getMessage().getStatus(), 16)
                                    .toUpperCase()))
                            .charAt(1)),
                    16)
            == 15
        && Integer.toString((int) event.getMessage().getMessage()[1], 16).toUpperCase().length()
            == 2
        && Integer.toString((int) event.getMessage().getMessage()[1], 16).toUpperCase().equals("51")
        && Integer.toString((int) event.getMessage().getMessage()[2], 16).toUpperCase().equals("3"))
      return true;
    return false;
  }

  public static MidiEvent getNextEvent() {
    ArrayList<MidiEvent> nextEvent = new ArrayList<MidiEvent>();
    ArrayList<Integer> trackOfNextEvent = new ArrayList<Integer>();
    for (int track = 0; track < seq.getTracks().length; track++) {
      if (seq.getTracks()[track].size() - 1 > (nextMessageOf.get(track))) {
        nextEvent.add(seq.getTracks()[track].get(nextMessageOf.get(track)));
        trackOfNextEvent.add(track);
      }
    }
    if (nextEvent.size() == 0) return null;
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

  public static String noteName(int n) {
    final String[] NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    final int octave = (n / 12) - 1;
    final int note = n % 12;
    return NAMES[note] + octave;
  }

  public static int findLeadGuitar(Sequence seq) {
    int guitarNumber = -1;
    try {
      guitarNumber = findLeadGuitar(loopTracks(seq));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return guitarNumber;
  }

  public static ArrayList<Integer> loopTracks(Sequence seq) {
    Track[] tracks = seq.getTracks();
    ArrayList<Integer> guitarTracks = new ArrayList<Integer>();
    for (int i = 0; i < tracks.length; i++) {
      guitarTracks.add(findGuitarInTrack(tracks[i]));
    }
    System.out.println("first guitar on each track: " + guitarTracks);
    return guitarTracks;
  }

  public static int findGuitarInTrack(Track track) {
    for (int i = 0; i < track.size(); i++) {
      MidiEvent event = track.get(i);
      MidiMessage msg = event.getMessage();
      if (msg instanceof ShortMessage) {
        final ShortMessage smsg = (ShortMessage) msg;
        final int cmd = smsg.getCommand();
        final int dat1 = smsg.getData1();

        if (cmd == ShortMessage.PROGRAM_CHANGE) {
          if (dat1 >= 25 && dat1 <= 38) {
            return dat1;
          }
        }
      }
    }
    return 0;
  }

  // currently lead guitar is the first track containing a guitar
  public static int findLeadGuitar(ArrayList<Integer> instrumentTracks) {
    for (int i = 0; i < instrumentTracks.size(); i++) {
      if (instrumentTracks.get(i) != 0) {
        guitarTrack = i;
        return instrumentTracks.get(i);
      }
    }
    return -1;
  }
}

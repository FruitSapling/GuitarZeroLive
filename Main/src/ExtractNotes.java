import java.util.*;
import javax.sound.midi.*;
import java.io.*;

/**
 * This class extracts the notes played by the lead guitar of a song along with their corresponding
 * time, in milliseconds
 *
 * @author Luke Sykes
 * @version 1.1
 * @since 2019-03-05
 */
public class ExtractNotes{
  final static String FILE = "Main/src/MrBrightside.mid"; // TODO input required song
  static Sequence seq;
  static int currentTrack = 0;
  static int guitarTrack = -1;
  static ArrayList<Integer> nextMessageOf = new ArrayList<Integer>();

  /**
   * This is the main method which gets the midi sequenece and then makes use of the methods to find
   * the lead guitar and then extract its notes.
   * @param args
   */
  public static void main(String[] args){
    try{
      seq = MidiSystem.getSequence(new File( FILE));
    } catch (Exception e){
      e.printStackTrace();
    }

    System.out.println(findAllGuitars(seq));
    //int leadGuitar = findLeadGuitar(findAllGuitars(seq));
    //System.out.println("The lead guitar is: " + leadGuitar);
    //convertMidiToNotes(seq, leadGuitar);
  }

  /**
   * This method recognises changes when playing the midi file and takes appropiate action
   * @param seq the midi file being played
   * @param leadGuitar the track number the lead guitar is played on
   */
  // TODO break down this method to make it smaller
  public static void convertMidiToNotes(Sequence seq, int leadGuitar){
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
    int currentChannel = -1;

    for (int track=0; track < seq.getTracks().length; track ++)
      nextMessageOf.add(0);

    while((nextEvent = getNextEvent()) != null){
      int tick = (int)nextEvent.getTick();

      currentChannel = getChannelNumber(nextEvent);

      if(programChange(nextEvent)){
        instrumentNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
        if (instrumentNumber == leadGuitar) {
          channelNumber = currentChannel;
        }
      }

      if(noteIsOff(nextEvent)){
        if (channelNumber == currentChannel && guitarTrack == currentTrack) {
          double time = (msb4 + (((currentTempo / seq.getResolution()) / 1000)
              * (tick - tickOfTempoChange)));
          int noteNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
          noteList.add("OFF," + noteName(noteNumber) + "," + (int) (time + 0.5));
        }
      }else

      if(noteIsOn(nextEvent)){
        if (channelNumber == currentChannel && guitarTrack == currentTrack) {
          double time = (msb4 + (((currentTempo / seq.getResolution()) / 1000)
              * (tick - tickOfTempoChange)));
          int noteNumber = ((int) nextEvent.getMessage().getMessage()[1] & 0xFF);
          noteList.add("ON," + noteName(noteNumber) + "," + (int) (time + 0.5));
        }
      }else
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
    notes.writeSong(noteList);
  }

  /**
   * Find the current channel number being played
   * @param event the current event taking place on the midifile
   * @return the channel number being played currently
   */
  public static int getChannelNumber(MidiEvent event){
    MidiMessage msg = event.getMessage();
    if(msg instanceof ShortMessage){
      final ShortMessage smsg = (ShortMessage) msg;
      final int          chan = smsg.getChannel();
      return chan;
    }
    // return -1 if no channel number is found
    return -1;
  }

  /**
   * Find if the current event is a note off event
   * @param event the current event taking place on the midifile
   * @return whether the current event is a note off
   */
  public static boolean noteIsOff(MidiEvent event) {
    if (Integer.toString((int)event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '8' ||
        (noteIsOn(event) && event.getMessage().getLength() >= 3 && ((int)event.getMessage().getMessage()[2] & 0xFF) == 0)) return true;
    return false;
  }

  /**
   * Find if the current event is a note off event
   * @param event the current event taking place on the midifile
   * @return whether the current event is a note on
   */
  public static boolean noteIsOn(MidiEvent event) {
    if (Integer.toString(event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '9') return true;
    return false;
  }

  /**
   * Find if the current event is a program change event
   * @param event the current event taking place on the midifile
   * @return whether the current event is a program change
   */
  public static boolean programChange(MidiEvent event){
    MidiMessage msg = event.getMessage();
    if ( msg instanceof ShortMessage ) {
      final long         tick = event.getTick();
      final ShortMessage smsg = (ShortMessage) msg;
      final int          chan = smsg.getChannel();
      final int          cmd  = smsg.getCommand();
      final int          dat1 = smsg.getData1();

      switch( cmd ) {
        case ShortMessage.PROGRAM_CHANGE :
          return true;
        default :
          /* ignore other commands */
          break;
      }
    }
    return false;
  }

  /**
   * Find if there has been a change in tempo during the event
   * @param event the current event taking place on the midifile
   * @return whether the tempo has changed or not
   */
  public static boolean changeTemp(MidiEvent event) {
    if ((int)Integer.valueOf((""+Integer.toString((int)event.getMessage().getStatus(), 16).toUpperCase().charAt(0)), 16) == 15
        && (int)Integer.valueOf((""+((String)(Integer.toString((int)event.getMessage().getStatus(), 16).toUpperCase())).charAt(1)), 16) == 15
        && Integer.toString((int)event.getMessage().getMessage()[1],16).toUpperCase().length() == 2
        && Integer.toString((int)event.getMessage().getMessage()[1],16).toUpperCase().equals("51")
        && Integer.toString((int)event.getMessage().getMessage()[2],16).toUpperCase().equals("3")) return true;
    return false;
  }

  /**
   * Find the next midi event to take place across all tracks
   * @return the next midi event to take place
   */
  public static MidiEvent getNextEvent(){
    ArrayList<MidiEvent> nextEvent = new ArrayList<MidiEvent>();
    ArrayList<Integer> trackOfNextEvent = new ArrayList<Integer>();
    for (int track = 0; track < seq.getTracks().length; track ++) {
      if (seq.getTracks()[track].size()-1 > (nextMessageOf.get(track))) {
        nextEvent.add(seq.getTracks()[track].get(nextMessageOf.get(track)));
        trackOfNextEvent.add(track);
      }
    }
    if (nextEvent.size() == 0) return null;
    int closestMessage = 0;
    int smallestTick = (int)nextEvent.get(0).getTick();
    for (int trialMessage = 1; trialMessage < nextEvent.size(); trialMessage ++) {
      if ((int)nextEvent.get(trialMessage).getTick() < smallestTick) {
        smallestTick = (int)nextEvent.get(trialMessage).getTick();
        closestMessage = trialMessage;
      }
    }
    currentTrack = trackOfNextEvent.get(closestMessage);
    nextMessageOf.set(currentTrack,(nextMessageOf.get(currentTrack)+1));
    return nextEvent.get(closestMessage);
  }

  /**
   * Returns the name of nth note
   *
   * @param n the note number
   * @return the note name
   */
  public static String noteName( int n ) {
    final String[] NAMES =
        { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
    final int octave = (n / 12) - 1;
    final int note   = n % 12;
    return NAMES[ note ] + octave;
  }

  /**
   * Finds the lead guitar out of the given instrument numbers
   * @param instrumentTracks an array of instrument numbers where index corresponds to track number
   * @return the instrument number of the lead guitar
   */
  public static int findLeadGuitar(ArrayList<Integer> instrumentTracks){
    // currently lead guitar is the first track containing a guitar
    // TODO find an actual lead guitar
    // could do on range of notes?
    for(int i=0; i < instrumentTracks.size(); i++){
      if(instrumentTracks.get(i) != 0){
        guitarTrack = i;
        return instrumentTracks.get(i);
      }
    }
    return -1;
  }

  /**
   * Iterates over every track, calling the method findGuitarInTrack on each
   *
   * @param seq the midi sequence to iterate through tracks
   * @return a list of guitar instrument numbers for their respective track
   */
  public static ArrayList<ArrayList<Integer>> findAllGuitars(Sequence seq){
    Track[] tracks = seq.getTracks();
    ArrayList<ArrayList<Integer>> guitarTracks = new ArrayList<ArrayList<Integer>>();
    for(int i = 0; i < tracks.length; i++){
      guitarTracks.add(findGuitarInTrack(tracks[i]));
    }
    return guitarTracks;
  }

  /**
   * Find the guitar on each track
   *
   * @param track the current track to look for a guitar in
   * @return the instrument number of the first guitar in the track
   */
  public static ArrayList<Integer> findGuitarInTrack(Track track){
    // return list of all guitars, not int
    ArrayList<Integer> guitarsInTrack = new ArrayList<Integer>();
    for(int i=0; i < track.size(); i++){
      MidiEvent event = track.get(i);
      MidiMessage msg = event.getMessage();
      if ( msg instanceof ShortMessage ){
        final ShortMessage smsg = (ShortMessage) msg;
        final int cmd = smsg.getCommand();
        final int dat1 = smsg.getData1();

        if(cmd == ShortMessage.PROGRAM_CHANGE){
          if(dat1 >= Constants.FirstGuitar && dat1 <= Constants.LastGuitar && !guitarsInTrack.contains(dat1)){
            guitarsInTrack.add(dat1);
            // evaluate guitar?
            // count occurences? so only record most dominant in track?
            // should probably record channel number with it
          }
        }
      }
    }
    return guitarsInTrack;
  }

  public static void playWithoutGuitar(){
    try {
      final Sequencer   sequen = MidiSystem.getSequencer();
      final Transmitter trans  = sequen.getTransmitter();

      sequen.open();

      sequen.setSequence( MidiSystem.getSequence( new File( FILE ) ) );

      sequen.setTrackMute(guitarTrack, true);
      //sequen.setTrackSolo(guitarTrack, true);

      sequen.addMetaEventListener( new MetaEventListener() {
        public void meta( MetaMessage mesg ) {
          if ( mesg.getType() == 0x2F /* end-of-track */ ) {
            sequen.close();
          }
        }
      });
      sequen.start();
    } catch ( Exception exn ) {
      System.out.println( exn ); System.exit( 1 );
    }
  }
}

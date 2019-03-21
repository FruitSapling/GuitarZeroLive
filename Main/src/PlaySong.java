import java.io.File;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

/**
 * Plays the midi file music with various settings
 */
public class PlaySong {

  private static Sequencer sequen;

  /**
   * Plays the song specified, muting or soloing the track given if required
   * @param filename the songname of the file to be played
   * @param trackNumber the track number the lead guitar is stored on
   * @param mute should the lead guitar be muted
   * @param solo should only the lead guitar be played
   */
  public static void playMidi(String filename, int trackNumber, boolean mute, boolean solo){
    try {
      sequen = MidiSystem.getSequencer();
      sequen.open();
      sequen.setSequence( MidiSystem.getSequence( new File( filename ) ) );
      if(mute) sequen.setTrackMute(trackNumber, true);
      if(solo) sequen.setTrackSolo(trackNumber, true);
      sequen.addMetaEventListener( new MetaEventListener() {
        public void meta( MetaMessage mesg ) {
          if ( mesg.getType() == Constants.END_OF_SONG ) {
            sequen.close();
          }
        }
      });
      sequen.start();
    } catch ( Exception e ) {
      e.printStackTrace();
      System.exit( 1 );
    }
  }

  /**
   * Plays a 30 second portion of a song, starting at 10 seconds in, on repeat
   * @param filename the filename of the song to be played
   */
  public static void playPreview(String filename){
    try{
      sequen = MidiSystem.getSequencer();
      sequen.open();
      sequen.setSequence(MidiSystem.getSequence(new File( "Main/src/Music/" + filename + "/" + filename + ".mid")));
      sequen.setLoopStartPoint(Constants.LOOP_START);
      sequen.setLoopEndPoint(Constants.LOOP_END);
      sequen.setTickPosition(Constants.LOOP_START);
      sequen.setLoopCount(sequen.LOOP_CONTINUOUSLY);
      sequen.start();
    } catch(Exception e){
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Stops the preview song from playing
   */
  public static void stopPreview(){
    try{
      sequen.close();
    } catch(Exception e){
      // do nothing
    }
  }
}
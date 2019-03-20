import java.io.File;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

public class PlaySong {
  /**
   * Plays the song specified, muting or soloing the track given if required
   */
  public static void playMidi(String filename, int trackNumber, boolean mute, boolean solo){
    try {
      final Sequencer sequen = MidiSystem.getSequencer();

      sequen.open();
      sequen.setSequence( MidiSystem.getSequence( new File( "Main/src/" + filename ) ) );
      if(mute) sequen.setTrackMute(trackNumber, true);
      if(solo) sequen.setTrackSolo(trackNumber, true);
      sequen.addMetaEventListener( new MetaEventListener() {
        public void meta( MetaMessage mesg ) {
          if ( mesg.getType() == 0x2F /* end-of-track */ ) {
            sequen.close();
          }
        }
      });
      sequen.start();
    } catch ( Exception e ) {
      System.exit( 1 );
    }
  }
}
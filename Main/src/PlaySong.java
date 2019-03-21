import java.io.File;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

public class PlaySong {

  private static Sequencer sequen;

  public static void main(String[] args){
    playPreview("HeyBrother.mid");
    stopPreview();
  }

  /**
   * Plays the song specified, muting or soloing the track given if required
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
          if ( mesg.getType() == 0x2F /* end-of-track */ ) {
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

  public static void playPreview(String filename){
    try{
      sequen = MidiSystem.getSequencer();
      sequen.open();
      sequen.setSequence(MidiSystem.getSequence(new File( filename)));
      sequen.setLoopStartPoint(Constants.LOOP_START);
      sequen.setLoopEndPoint(Constants.LOOP_END);
      sequen.setTickPosition(Constants.LOOP_START);
      sequen.setLoopCount(sequen.LOOP_CONTINUOUSLY);
      sequen.addMetaEventListener(new MetaEventListener() {
        public void meta(MetaMessage mesg) {
            if ( mesg.getType() == 0x2F /* end-of-track */ ) {
              sequen.close();
            }
          }
        });
      sequen.start();
    } catch(Exception e){
      System.exit(1);
    }
  }

  public static void stopPreview(){
    sequen.close();
  }
}
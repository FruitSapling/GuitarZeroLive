import java.io.File;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.MidiSystem;

/**
 * Make MIDI sequence, and save in file.
 *
 * @author  David Wakeling
 * @version 1.00, January 2019.
 */
public class MakeMidi {
  final static String FILE       = "midifile.mid";
  final static int    CHANNEL    =   7; /* 0..15       */
  final static int    INSTRUMENT =   6; /* Harpsichord */
  final static int    PITCH      =  60; /* 0..127      */
  final static int    VELOCITY   = 100; /* 0..127      */

  /**
   * Return example MIDI sequence.
   *
   * @return an example MIDI sequence.
   */
  private static Sequence makeMIDISequence() {
    try {
      /* Create a new sequence with 24 ticks per beat. */
      Sequence seq = new Sequence( Sequence.PPQ, 24 );
      Track    trk = seq.createTrack();

      /* Channel 7, Program change: Acoustic Grand Piano. */
      ShortMessage msg1 = new ShortMessage();
      msg1.setMessage( 0xc0 | CHANNEL, INSTRUMENT, 0x00 );
      MidiEvent evt1 = new MidiEvent( msg1, (long) 0 );
      trk.add( evt1 );

      /* Channel 7, Note on: C4 */
      ShortMessage msg2 = new ShortMessage();
      msg2.setMessage( 0x90 | CHANNEL, PITCH, VELOCITY );
      MidiEvent evt2 = new MidiEvent( msg2, (long) 1 );
      trk.add( evt2 );

      /* Channel 7, Note off: C4, 120 ticks later. */
      ShortMessage msg3 = new ShortMessage();
      msg3.setMessage( 0x80 | CHANNEL, PITCH, 0x00 );
      MidiEvent evt3 = new MidiEvent( msg3, (long) 121 );
      trk.add( evt3 );

      /* End of track. */
      MetaMessage msg4 = new MetaMessage();
      byte[] empty = {}; // empty array
      msg4.setMessage( 0x2F, empty, 0 );
      MidiEvent evt4 = new MidiEvent( msg4, (long) 122 );
      trk.add( evt4 );

      return seq;
    } catch( Exception exn ) {
      System.out.println( exn ); System.exit( 1 ); return null;
    }
  }

  /*
   * Main
   *
   * @param argv the command line arguments
   */
  public static void main( String[] argv ) {
    try {
      final Sequence seq = makeMIDISequence();
      MidiSystem.write( seq, 1, new File( FILE ) );
    } catch( Exception exn ) {
      System.out.println( exn ); System.exit( 1 );
    }
  }
}

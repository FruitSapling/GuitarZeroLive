import java.io.*;
import java.util.*;
import javax.sound.midi.*;

public class ExtractNotes {

   final static String FILE = "Wonderwall.mid";

   public static void playWithoutGuitar(int n){
	try {
      final Sequencer   sequen = MidiSystem.getSequencer();
      final Transmitter trans  = sequen.getTransmitter();

      sequen.open();

	  Sequence seq = MidiSystem.getSequence(new File(FILE));
	  System.out.println(seq.getDivisionType());
	  System.out.println(seq.getResolution());
	  
	  if(seq.getDivisionType() == Sequence.SMPTE_30DROP){
		  System.out.println("24");
	  }
	  
      sequen.setSequence( MidiSystem.getSequence( new File( FILE ) ) );
      // trans.setReceiver( new Display() );

	  //sequen.setTrackMute(n, true);
	  sequen.setTrackSolo(8, true);
	  
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
   
  /**
   * Returns the name of nth instrument in the current MIDI soundbank.
   *
   * @param n the instrument number
   * @return  the instrument name
   */
  public static String instrumentName( int n ) {
    try {
      final Synthesizer synth = MidiSystem.getSynthesizer();
      synth.open();
      final Instrument[] instrs = synth.getAvailableInstruments();
      synth.close();
      return instrs[ n ].getName();
    } catch ( Exception exn ) {
      System.out.println( exn ); System.exit( 1 ); return "";
    }
  }


  /**
   * Returns the name of nth note.
   *
   * @param n the note number
   * @return  the note name
   */
  public static String noteName( int n ) {
    final String[] NAMES =
      { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
    final int octave = (n / 12) - 1;
    final int note   = n % 12;
    return NAMES[ note ] + octave;
  }

  /**
   * Display a MIDI track.
   */
  public static void displayTrack( Track trk ) {
	
	ArrayList<Long> guitarAppearances = new ArrayList<Long>();
	
    for ( int i = 0; i < trk.size(); i = i + 1 ) { 
      MidiEvent   evt  = trk.get( i );
      MidiMessage msg = evt.getMessage();
      if ( msg instanceof ShortMessage ) {
        final long         tick = evt.getTick();
        final ShortMessage smsg = (ShortMessage) msg;
        final int          chan = smsg.getChannel();
        final int          cmd  = smsg.getCommand();
        final int          dat1 = smsg.getData1();

        switch( cmd ) {
        case ShortMessage.PROGRAM_CHANGE :
	      System.out.print  ( "@" + tick + ", " );
          System.out.print  ( "Channel " + chan + ", " );
          System.out.println( "Program change: " + (dat1+1) + ", " + instrumentName( dat1 ) );
		  if(dat1 >= 25 && dat1 <= 38){
			  guitarAppearances.add(tick);
		  }
		  break;
        case ShortMessage.NOTE_ON :
	  System.out.print  ( "@" + tick + ", " );
          System.out.print  ( "Channel " + chan + ", " );
          System.out.println( "Note on:  " + noteName( dat1 ) );
		  if(dat1 >= 25 && dat1 <= 38){
			  guitarAppearances.add(tick);
		  }
          break;
        case ShortMessage.NOTE_OFF :
	  System.out.print  ( "@" + tick + ", " );
          System.out.print  ( "Channel " + chan + ", " );
          System.out.println( "Note off: " + noteName( dat1 ) );
		  if(dat1 >= 25 && dat1 <= 38){
			  guitarAppearances.add(tick);
		  }
          break;
        default :
          /* ignore other commands */
          break;
        }
      }
    }
	
	System.out.println(guitarAppearances);
  }
  
    public static int findGuitarTracks( Track trk ) {
	
    for ( int i = 0; i < trk.size(); i = i + 1 ) { 
      MidiEvent   evt  = trk.get( i );
      MidiMessage msg = evt.getMessage();
      if ( msg instanceof ShortMessage ) {
        final long         tick = evt.getTick();
        final ShortMessage smsg = (ShortMessage) msg;
        final int          chan = smsg.getChannel();
        final int          cmd  = smsg.getCommand();
        final int          dat1 = smsg.getData1();

        switch( cmd ) {
        case ShortMessage.PROGRAM_CHANGE :
		  if(dat1 >= 25 && dat1 <= 38){
			  return dat1 + 1;
		  }
		  break;
        
        default :
          /* ignore other commands */
          break;
        }
      }
    }
	return 0;
  }
  
    /**
   * Display a MIDI track.
   */
  public static void displayTrackNoGuitar( Track trk ) {
	
	ArrayList<Long> guitarAppearances = new ArrayList<Long>();
	
    for ( int i = 0; i < trk.size(); i = i + 1 ) { 
      MidiEvent   evt  = trk.get( i );
      MidiMessage msg = evt.getMessage();
      if ( msg instanceof ShortMessage ) {
        final long         tick = evt.getTick();
        final ShortMessage smsg = (ShortMessage) msg;
        final int          chan = smsg.getChannel();
        final int          cmd  = smsg.getCommand();
        final int          dat1 = smsg.getData1();

        switch( cmd ) {
        case ShortMessage.NOTE_ON :
		  guitarAppearances.add((long) 1);
		  guitarAppearances.add(tick);
          break; 
        case ShortMessage.NOTE_OFF :
		  guitarAppearances.add((long) 0);
		  guitarAppearances.add(tick);
          break;
        default :
          /* ignore other commands */
          break;
        }
      }
    }
	
	System.out.println(guitarAppearances);
  }

  /**
   * Display a MIDI sequence for each track in the file.
   */
  public static void displaySequence( Sequence seq ) {
    Track[] trks = seq.getTracks();

    for ( int i = 0; i < trks.length; i++ ) {
      System.out.println( "Track " + i );
      displayTrack( trks[ i ] );
    }
  }
  
  public static ArrayList<Integer> displaySequenceTRACKS( Sequence seq ) {
    Track[] trks = seq.getTracks();
	ArrayList<Integer> guitarTracks = new ArrayList<Integer>();
    for ( int i = 0; i < trks.length; i++ ) {
		guitarTracks.add(findGuitarTracks( trks[ i ] ));
    }
	System.out.println(guitarTracks);
	return guitarTracks;
  }
  
  public static void displaySequenceNoGuitar( Sequence seq, int n ) {
    Track[] trks = seq.getTracks();
	System.out.println("Playing track " + n);
	displayTrackNoGuitar( trks[ n ] );
  }

  public static int findGuitar(ArrayList<Integer> instrumentTracks){
	  for(int i=0; i < instrumentTracks.size(); i++){
		  if(instrumentTracks.get(i) != 0){
			  return i;
		  }
	  } 
	  return -1;
  }


  /*
   * Main.
   *
   * @param argv the command line arguments
   */
  public static void main( String[] argv ) {
	int guitarTrack = -1;
	// loop tracks to find lead guitar?
	// assumes lead guitar is on the first track containing a guitar
	try {
      Sequence seq = MidiSystem.getSequence( new File( FILE ) );
      guitarTrack = findGuitar(displaySequenceTRACKS( seq ));
    } catch ( Exception exn ) {
       System.out.println( exn ); System.exit( 1 );
    }

	// record track with guitar playing only
	try {
      Sequence seq = MidiSystem.getSequence( new File( FILE ) );
	  displaySequenceNoGuitar( seq, guitarTrack );
    } catch ( Exception exn ) {
       System.out.println( exn ); System.exit( 1 );
    }
	System.out.println("Muting track " + guitarTrack);
	playWithoutGuitar(guitarTrack);
  }
}
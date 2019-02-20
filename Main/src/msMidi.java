import java.util.*;
import javax.sound.midi.*;
import java.io.*;
import java.text.*;

public class msMidi {
    final static String FILE = "wonderwall.mid";
    static Sequence seq;
    static int currentTrack = 0;
    static int trackNumber = -1;
    static ArrayList<Integer> nextMessageOf = new ArrayList<Integer>();

    public static void main(String[] args) {
        try {
            seq = MidiSystem.getSequence(new File( FILE ));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        int leadGuitar = findLeadGuitar(seq);
        System.out.println(leadGuitar);
        convertMidi2RealTime(seq, leadGuitar);
        playWithoutGuitar();
    }

    public static void convertMidi2RealTime(Sequence seq, int leadGuitar) {
        double currentTempo = 500000;
        int tickOfTempoChange = 0;
        double msb4 = 0;
        double division = seq.getResolution();
        int lastTick = 0;
        int count = 0;
        for (int track = 0; track < seq.getTracks().length; track ++) nextMessageOf.add(0);

        NoteFileMaker notes = new NoteFileMaker(FILE + "notes", trackNumber);
        ArrayList<String> noteList = new ArrayList<String>();
        MidiEvent nextEvent;
        while ((nextEvent = getNextEvent()) != null) {
            int tick = (int)nextEvent.getTick();
            if (noteIsOff(nextEvent)) {

                int instrumentNumber = ((int)nextEvent.getMessage().getMessage()[1] & 0xFF) + 1;


                if(instrumentNumber == leadGuitar){
                    double time = (msb4+(((currentTempo/seq.getResolution())/1000)*(tick-tickOfTempoChange)));
                    // change instrument number to?
                    // is instrument number also the same as note number?
                    noteList.add("OFF," + noteName(instrumentNumber) + "," + (int)(time+0.5));
                }
            }
            else
            if (noteIsOn(nextEvent)) {

                int instrumentNumber = ((int)nextEvent.getMessage().getMessage()[1] & 0xFF) + 1;

                if(instrumentNumber == leadGuitar){
                    double time = (msb4+(((currentTempo/seq.getResolution())/1000)*(tick-tickOfTempoChange)));
                    noteList.add("ON," + noteName(instrumentNumber) + "," + (int)(time+0.5));
                }
            }
            else
            if (changeTemp(nextEvent)) {
                String a = (Integer.toHexString((int)nextEvent.getMessage().getMessage()[3] & 0xFF));
                String b = (Integer.toHexString((int)nextEvent.getMessage().getMessage()[4] & 0xFF));
                String c = (Integer.toHexString((int)nextEvent.getMessage().getMessage()[5] & 0xFF));
                if (a.length() == 1) a = ("0"+a);
                if (b.length() == 1) b = ("0"+b);
                if (c.length() == 1) c = ("0"+c);
                String whole = a+b+c;
                int newTempo = Integer.parseInt(whole,16);
                double newTime = (currentTempo/seq.getResolution())*(tick-tickOfTempoChange);
                msb4 += (newTime/1000);
                tickOfTempoChange = tick;
                currentTempo = newTempo;
            }
        }
        notes.writeSong(noteList);
    }

    public static MidiEvent getNextEvent() {
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

    public static boolean noteIsOff(MidiEvent event) {
        if (Integer.toString((int)event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '8' ||
                (noteIsOn(event) && event.getMessage().getLength() >= 3 && ((int)event.getMessage().getMessage()[2] & 0xFF) == 0)) return true;
        return false;
    }

    public static boolean noteIsOn(MidiEvent event) {
        if (Integer.toString(event.getMessage().getStatus(), 16).toUpperCase().charAt(0) == '9') return true;
        return false;
    }

    public static boolean changeTemp(MidiEvent event) {
        if ((int)Integer.valueOf((""+Integer.toString((int)event.getMessage().getStatus(), 16).toUpperCase().charAt(0)), 16) == 15
                && (int)Integer.valueOf((""+((String)(Integer.toString((int)event.getMessage().getStatus(), 16).toUpperCase())).charAt(1)), 16) == 15
                && Integer.toString((int)event.getMessage().getMessage()[1],16).toUpperCase().length() == 2
                && Integer.toString((int)event.getMessage().getMessage()[1],16).toUpperCase().equals("51")
                && Integer.toString((int)event.getMessage().getMessage()[2],16).toUpperCase().equals("3")) return true;
        return false;
    }

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

    public static String noteName( int n ) {
        final String[] NAMES =
                { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
        final int octave = (n / 12) - 1;
        final int note   = n % 12;
        return NAMES[ note ] + octave;
    }

    public static int findLeadGuitar(Sequence seq){
        int leadGuitar = -1;
        int guitarTrack = -1;
        try {
            guitarTrack = findFirstGuitar(loopTRACKS( seq ));
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
        return guitarTrack;
    }

    // returns the first guitar number in the track, 0 if no guitar
    public static int findGuitarInTrack( Track trk ) {
        for ( int i = 0; i < trk.size(); i = i + 1 ) {
            MidiEvent   evt  = trk.get( i );
            MidiMessage msg = evt.getMessage();
            if ( msg instanceof ShortMessage ) {
                final long         tick = evt.getTick();
                final ShortMessage smsg = (ShortMessage) msg;
                final int          chan = smsg.getChannel();
                final int          cmd  = smsg.getCommand();
                final int          dat1 = smsg.getData1() + 1;

                switch( cmd ) {
                    case ShortMessage.PROGRAM_CHANGE :
                        if(dat1 >= 25 && dat1 <= 38){
                            return dat1;
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

    // return instrument number of the first guitar
    public static int findFirstGuitar(ArrayList<Integer> instrumentTracks){
        for(int i=0; i < instrumentTracks.size(); i++){
            if(instrumentTracks.get(i) != 0){
                trackNumber = i;
                return instrumentTracks.get(i);
            }
        }
        return -1;
    }

    // returns a list of the first guitar in each track
    public static ArrayList<Integer> loopTRACKS( Sequence seq ) {
        Track[] trks = seq.getTracks();
        ArrayList<Integer> guitarTracks = new ArrayList<Integer>();
        for ( int i = 0; i < trks.length; i++ ) {
            guitarTracks.add(findGuitarInTrack( trks[ i ] ));
        }
        System.out.println("first guitar on each track: " + guitarTracks);
        return guitarTracks;
    }

    public static void playWithoutGuitar(){
        try {
            final Sequencer   sequen = MidiSystem.getSequencer();
            final Transmitter trans  = sequen.getTransmitter();

            sequen.open();

            sequen.setSequence( MidiSystem.getSequence( new File( FILE ) ) );

            //sequen.setTrackMute(trackNumber, true);
            sequen.setTrackSolo(trackNumber, true);

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
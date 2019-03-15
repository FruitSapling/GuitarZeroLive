import java.util.Arrays;
import java.util.List;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

// TODO very inefficient code
public class PlayGuitar {

  private static final List<String> NOTES = Arrays
      .asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");
  private static final int VOLUME = 127; // between 0 et 127

  public static Instrument play(int instrument){
    /*
        playNote("C", 6, 1000);
        playNote("C#", 6, 1000);
     */
    Instrument guitar = null;
    try{
      Synthesizer synth = MidiSystem.getSynthesizer();
      synth.open();

      Soundbank bank = synth.getDefaultSoundbank();
      synth.loadAllInstruments(bank);
      Instrument instrs[] = synth.getLoadedInstruments();

      for (int i=0; i < instrs.length; i++) {
        if (instrs[i].getPatch().getProgram() == instrument) {
          guitar = instrs[i];
          break;
        }
      }
      if (guitar == null) {
        System.exit(1);
      }

      synth.close();

    } catch (Exception e) {
      System.exit(1);
    }
    return guitar;
  }

  /**
   * Plays the given note for the given duration
   */
  public static void playNote(Instrument guitar, String note, int octave, int duration) throws InterruptedException {
    try{
       Synthesizer synth = MidiSystem.getSynthesizer();
       synth.open();
       MidiChannel[] channels = synth.getChannels();

       Patch guitarPatch = guitar.getPatch();
       channels[1].programChange(guitarPatch.getBank(), guitarPatch.getProgram());
       // * start playing a note
       channels[1].noteOn(findNote(note, octave), VOLUME );
       // * wait
       Thread.sleep( duration );
       // * stop playing a note
       channels[1].noteOff(findNote(note, octave));
       synth.close();
    } catch (Exception e) {
      System.exit(1);
    }
  }

  /**
   * Returns the MIDI id for a given note: eg. 4C -> 60
   * @return
   */
  private static int findNote(String note, int octave) {
    return NOTES.indexOf(note.substring(0)) + 12 * octave + 12;
  }
}

package tictactoe;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke SoundEffect.SOUND_NAME.play().
 * 3. You might optionally invoke the static method SoundEffect.initGame() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can the static variable SoundEffect.volume to SoundEffect.Volume.MUTE
 *    to mute the sound.
 *
 * For Eclipse, place the audio file under "src", which will be copied into "bin".
 */
public enum SoundEffect {
   PLAYER_X("Assets\\Sounds\\XO.wav"),
   PLAYER_O("Assets\\Sounds\\PLAYER_O.wav"),
   WINNER("Assets\\Sounds\\Winner.wav"),
   DRAW("Assets\\Sounds\\Draw.wav");

   /** Nested enumeration for specifying volume */
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;

   /** Each sound effect has its own clip, loaded with its own sound file. */
   private Clip clip;

   /** Private Constructor to construct each element of the enum with its own sound file. */
   private SoundEffect(String soundFilePath) {
      try {
         File soundFile = new File(soundFilePath);
         AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
         this.clip = AudioSystem.getClip();  // Use 'this.clip' to assign to enum's clip
         this.clip.open(audioStream);
      } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
         System.err.println("Error playing sound: " + e.getMessage());
      }
   }

   /** Play or Re-play the sound effect from the beginning, by rewinding. */
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();  // Stop the player if it is still running
         clip.setFramePosition(0); // Rewind to the beginning
         clip.start(); // Start playing
      }
   }

   /** Optional static method to pre-load all the sound files. */
   static void initGame() {
      values(); // Calls the constructor for all the elements
   }
}

package Son;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;

public class Audio extends Thread {

	private org.newdawn.slick.openal.Audio sound;
	private static Audio mel = null;

	public static Audio getSon(String arb) {
		if (Audio.mel == null) {
			try {
				Audio.mel = new Audio(arb);
			} catch (Exception e) {
				System.out.print(e.getMessage());
			}

		}
		return Audio.mel;
	}
	
	public Audio(String ref) throws SlickException {
		SoundStore.get().init();
	
		try {
			if (ref.toLowerCase().endsWith(".ogg")) {
				sound = SoundStore.get().getOgg(ref);
			} else if (ref.toLowerCase().endsWith(".wav")) {
				sound = SoundStore.get().getWAV(ref);
			} else if (ref.toLowerCase().endsWith(".aif")) {
				sound = SoundStore.get().getAIF(ref);
			} else if (ref.toLowerCase().endsWith(".xm") || ref.toLowerCase().endsWith(".mod")) {
				sound = SoundStore.get().getMOD(ref);
			} else {
				throw new SlickException("Only .xm, .mod, .aif, .wav and .ogg are currently supported.");
			}
		} catch (Exception e) {
			throw new SlickException("Failed to load sound: "+ref);
		}
	}
	
	
	public void play() {
		play(1.0f, 1.0f);
	}
	
	public void play(float pitch, float volume) {
		sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), false);
	}
	
	
	public void loop() {
		loop(1.0f, 1.0f);
	}
	
	
	public void loop(float pitch, float volume) {
		sound.playAsSoundEffect(pitch, volume * SoundStore.get().getSoundVolume(), true);
	}
	

	public boolean playing() {
		return sound.isPlaying();
	}
	
    
	public void Stop() {
		sound.stop();
	}

}
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 
public class BGM {
  
 
   public void start1(){
       this.music1();
   }

   public void start2(){
	this.music2();
}
public void start3(){
	this.music3();
}
   
public void start4(){
	this.music4();
}
           
	
   public void music1(){
      
       try {
           AudioInputStream ais;
           AudioFormat af;
           File bgm = new File("../src_sample/ImageFile/NES-Shooter-C04-1_Stage3_.wav");
           ais = AudioSystem.getAudioInputStream(bgm);
           af = ais.getFormat();
           DataLine.Info info = new DataLine.Info(Clip.class, af);
           Clip clip = (Clip)AudioSystem.getLine(info);
		   
           clip.open(ais);
           clip.loop(100);
		   
           //clip.flush();

           while(clip.isActive()) {
              Thread.sleep(0);
           }
       } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e ) {
           e.printStackTrace();
       }
   }

   public void music2(){
      
	try {
		AudioInputStream ais;
		AudioFormat af;
		File bgm = new File("../src_sample/ImageFile/bomb (online-audio-converter.com).wav");
		ais = AudioSystem.getAudioInputStream(bgm);
		af = ais.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, af);
		Clip clip = (Clip)AudioSystem.getLine(info);
		
		clip.open(ais);
		clip.loop(0);
		
		//clip.flush();

		while(clip.isActive()) {
		   Thread.sleep(0);
		}
	} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e ) {
		e.printStackTrace();
	}
   }

   public void music3(){
      
	try {
		AudioInputStream ais;
		AudioFormat af;
		File bgm = new File("../src_sample/ImageFile/20220202012521.wav");
		ais = AudioSystem.getAudioInputStream(bgm);
		af = ais.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, af);
		Clip clip = (Clip)AudioSystem.getLine(info);
		
		clip.open(ais);
		clip.loop(0);
		
		//clip.flush();

		while(clip.isActive()) {
		   Thread.sleep(0);
		}
	} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e ) {
		e.printStackTrace();
	}
   }
   public void music4(){
      
	try {
		AudioInputStream ais;
		AudioFormat af;
		File bgm = new File("../src_sample/ImageFile/パワーアップ.wav");
		ais = AudioSystem.getAudioInputStream(bgm);
		af = ais.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, af);
		Clip clip = (Clip)AudioSystem.getLine(info);
		
		clip.open(ais);
		clip.loop(0);
		
		//clip.flush();

		while(clip.isActive()) {
		   Thread.sleep(0);
		}
	} catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e ) {
		e.printStackTrace();
	}
   }
}
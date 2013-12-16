package com.ionprogramming.ld28.sfx;

import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sounds {
	
	static MediaPlayer music;
	static ArrayList<MediaPlayer> players = new ArrayList<MediaPlayer>();

	public static Media pit;
	public static Media s1;
	public static Media s2;
	public static Media s3;
	public static Media s4;
	public static Media s5;

	public static void load(){
		try{
			pit = new Media(Sounds.class.getClassLoader().getResource("res/sfx/pit.mp3").toString());
			s1 = new Media(Sounds.class.getClassLoader().getResource("res/music/1.mp3").toString());
			s2 = new Media(Sounds.class.getClassLoader().getResource("res/music/2.mp3").toString());
			s3 = new Media(Sounds.class.getClassLoader().getResource("res/music/3.mp3").toString());
			s4 = new Media(Sounds.class.getClassLoader().getResource("res/music/4.mp3").toString());
			s5 = new Media(Sounds.class.getClassLoader().getResource("res/music/5.mp3").toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void play(Media m){
		final MediaPlayer med = new MediaPlayer(m);
		med.play();
		med.setOnEndOfMedia(new Runnable() {
		    @Override
		    public void run() {
		       players.remove(med);
		    }
		});
		players.add(med);
	}
	
	public static int getPlayerIndex(Media m){
		int i = -1;
		for(int n = 0; n < players.size(); n++){
			if(players.get(n).getMedia() == m){
				i = n;
				break;
			}
		}
		return i;
	}
	
	public static MediaPlayer getPlayer(Media m){
		MediaPlayer p = null;
		for(int n = 0; n < players.size(); n++){
			if(players.get(n).getMedia() == m){
				p = players.get(n);
				break;
			}
		}
		return p;
	}
}

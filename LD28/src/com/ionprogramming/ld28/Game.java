package com.ionprogramming.ld28;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javafx.embed.swing.JFXPanel;

import com.ionprogramming.ld28.entities.Entity;
import com.ionprogramming.ld28.entities.Player;
import com.ionprogramming.ld28.gfx.Cutscenes;
import com.ionprogramming.ld28.gfx.HUD;
import com.ionprogramming.ld28.gfx.Images;
import com.ionprogramming.ld28.gfx.TitleScreen;
import com.ionprogramming.ld28.gfx.Update;
import com.ionprogramming.ld28.input.Keys;
import com.ionprogramming.ld28.sfx.Sounds;


public class Game extends Applet implements Runnable, KeyListener{
	
	public static int timeLimit = 1200;
	public static int torchTimeLeft = 1200;
	
	public static int povx = 0;
	public static int povy = 0;
	
	private static final long serialVersionUID = 1L;
	
	private Image dbImage;
	private Graphics dbg;
		
	public static int width = 780;
	public static int height = 520;
	
	static double ticker = System.nanoTime();
	static double time;
	double sleepTime;
	static double FPS = 60;
	
	public static boolean titleScreen = true;
	
	public static boolean won = false;
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	JFXPanel FX = new JFXPanel();
	
	public void init(){
		addKeyListener(this);
		setSize(width, height);
		setFocusable(true);
		setBackground(Color.black);
		try {
			Images.load();
			Sounds.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		HUD.initSmoke();
		Sounds.playMusic(Sounds.title);
	}
	
	
	public void start(){
		Thread th = new Thread(this);
		th.start();
	}
	
	public void run(){
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while(true){
			time = System.nanoTime();
			repaint();
			sleepTime = 1000/FPS - (System.nanoTime() - time)/1000000;
			try{
				if(sleepTime > 10){
					Thread.sleep((long) sleepTime);
				}
				else{
					Thread.sleep(10);
				}
			} 	
			catch(InterruptedException ex){
				ex.printStackTrace();
			}
			Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}
	
	public void paint(Graphics g){
		width = Window.frame.getWidth() - 6;
		height = Window.frame.getHeight() - 32;
		setSize(width, height);
		if(titleScreen){
			TitleScreen.renderScreen(g);
		}
		else if(won){
			g.drawImage(Images.won, 0, 0, null);
		}
		else{
			Update.update(g);
			if(System.nanoTime() - ticker >= 1000000000){
				torchTimeLeft--;
				ticker = System.nanoTime();
			}
			if(torchTimeLeft <= 0){
				Player.die = true;
				
				Cutscenes.trig(2);
			}
		}
	}
	
	public void update(Graphics g){
		if (dbImage == null){
	        dbImage = createImage (this.getSize().width, this.getSize().height);
	        dbg = dbImage.getGraphics ();
	    }
	    dbg.setColor (getBackground ());
	    dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
	    dbg.setColor (getForeground());	    
	    paint (dbg);
	    g.drawImage (dbImage, 0, 0, this);
	}
	
	
	@Override
	public void keyPressed(KeyEvent e){
		Keys.keyPressed(e);			
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keyReleased(e);		
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		Keys.keyTyped(e);
	}
}
		

/*Source : http://igm.univ-mlv.fr/~dr/XPOSE2005/JavaSound_arinie/exemples/recorder.html*/
/*A faire, récupérer un flux audio en l'envoyer via le socket TCP*/

import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import java.net.InetAddress;
import java.net.Socket;
	
public class voiceDigital {
	
		//chaine de sortie
		String sortie;
		//chaine entre
		String entre;
		
		
		
		private Socket clientSocket ;/*Déclaration objet clientTCP*/
	    private AudioFormat format;
	    private DataLine.Info targetInfo;
	    private ObjectOutputStream outStream;
	    voiceDigital(String serverAddress, int serverPort,float sampleRate, int sampleSizeInBits,int channels,boolean signed, boolean bigEndian) throws Exception {
	    	
	    	this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
			this.targetInfo = new DataLine.Info(TargetDataLine.class, format);	
			this.clientSocket = new Socket(serverAddress, serverPort);
			this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
	    	
	}
	    
	     
	      
	        
	    
	    /*Non fonctionnel pour l'instant*/
	    void start() throws IOException, LineUnavailableException {
	    	System.out.println("Lancement de la fonction");
			AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
			
			DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
			DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);

			try {
				TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
				targetLine.open(format);
				targetLine.start();
				
				SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
				sourceLine.open(format);
				sourceLine.start();

				int numBytesRead;
				byte[] targetData = new byte[targetLine.getBufferSize() / 5];
				
				fromServer = new DataInputStream(socketConnection.getInputStream());
		        toServer = new DataOutputStream(socketConnection.getOutputStream());
		        
				while (true) {
			    byte[] readData = new byte[sourceLine.getBufferSize()];
			    
				//System.out.println("On rentre dans le while infie");
					
					numBytesRead = targetLine.read(targetData, 0, targetData.length);
					if (numBytesRead == -1)	break;
					
					toServer.write(targetData);
					toServer.flush();
					
					fromServer.read(readData);
					
					sourceLine.write(readData, 0, numBytesRead);//ecrit dans le hautparleur le son du micro
					//System.out.println("Envoi");
					readData=null;
				}
			}
			catch (Exception e) {
				System.err.println(e);
			}
	        
	    }
	    
	}
	    



/*Source : http://igm.univ-mlv.fr/~dr/XPOSE2005/JavaSound_arinie/exemples/recorder.html*/
/*A faire, récupérer un flux audio en l'envoyer via le socket TCP*/

import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.net.InetAddress;

	
public class voiceDigitalUdp {

		
		
		
		private DatagramSocket clientSocket ;/*Déclaration objet clientUDP*/
	    private AudioFormat format;
	    private DataLine.Info targetInfo;
	    private InetAddress IPAddress ;
	    private DatagramPacket sendPacket;
	    private int port;
	    /*Non fonctionnel pour l'instant*/
	    voiceDigitalUdp(String serverAddress, int serverPort,float sampleRate, int sampleSizeInBits,int channels,boolean signed, boolean bigEndian) throws Exception {
	    	
	    	this.format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
			this.targetInfo = new DataLine.Info(TargetDataLine.class, format);	
			this.clientSocket = new DatagramSocket();
			this.IPAddress = InetAddress.getByName(serverAddress);
			this.port=serverPort;
			   
	}
	    
	     
	      
	        
	    
	    
	    void start() throws IOException, LineUnavailableException {
	    	try {
				TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
				targetLine.open(format);
				targetLine.start();
				
				

				int numBytesRead;
				byte[] targetData = new byte[targetLine.getBufferSize() / 5];

				while (true) {
					numBytesRead = targetLine.read(targetData, 0, targetData.length);
					 
					if (numBytesRead == -1)	break;
					this.sendPacket = new DatagramPacket(targetData, numBytesRead, this.IPAddress, port);
					clientSocket.send(sendPacket);
					
				}
			}
			catch (Exception e) {
				System.err.println(e);
			}
	        
	    }
	    
	}
	    


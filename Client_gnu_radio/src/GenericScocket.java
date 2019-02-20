import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class GenericScocket {
	/*Constante signalisation*/
	String SOF = "SOF";/*Start of File*/
	String EOF = "EOF";/*End of File*/
	String SOV = "SOV";/*Start of Voice*/
	String EOV = "EOV";/*End of voice*/
	String SOC = "SOC";/*Start of chat*/
	String EOC = "EOC";/*End oF chat*/
	
	/*Fin constante signalisation*/
	protected Socket socket = null;
	protected Socket clientSocketTX;
	protected Socket clientSocketRX;
	protected Scanner clavier;/*D�claration objet clavier*/
	protected FileInputStream fileIn;
	protected DataInputStream fromServer;
	protected DataOutputStream toServer;
	
	GenericScocket(String IPaddr, int serverPortTX, int serverPortRX) throws UnknownHostException, IOException{// constructeur du socket client
       
	   this.clientSocketTX = new Socket(IPaddr, serverPortTX);
       this.clientSocketRX = new Socket(IPaddr,serverPortRX); 
       
	}
	
	public void VoiceOverNetwork(float sampleRate, int sampleSizeInBits,int channels,boolean signed, boolean bigEndian){/*M�thode Voix*/
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
			
			this.fromServer = new DataInputStream(clientSocketRX.getInputStream());
	        this.toServer = new DataOutputStream(clientSocketTX.getOutputStream());
	        
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
	
	public void fileExchangeTX(String absolutePath) throws IOException {/*M�thode permettant d'envoyer un fichier*/
		
		this.fileIn = new FileInputStream(absolutePath);
        
		byte[] buffer = new byte[8];
		
        // On cr�e une variable de type int pour y affecter le r�sultat de
        // la lecture
        // Vaut -1 quand c'est fini
		
        int n = 0;
        while ((n = fileIn.read(buffer)) >= 0) {//Envoi tant que le fichier entier n'as pas �t� lu
        	this.toServer = new DataOutputStream(this.clientSocketTX.getOutputStream());// A placer ici ?
            // On envoie via le socket 
        	this.toServer.write(buffer);
        	this.toServer.flush();
            // On affiche ce qu'a lu notre boucle au format byte et au
            // format char
            for (byte bit : buffer) {

               System.out.print("\t" + bit + "(" + (char) bit + ")");

            }

            System.out.println("");

            //Nous r�initialisons le buffer � vide
            //au cas o� les derniers byte lus ne soient pas un multiple de 8
            //Ceci permet d'avoir un buffer vierge � chaque lecture et ne pas avoir de doublon en fin de fichier

            buffer = new byte[8];


         }

         System.out.println("Tranfert termin�");
	}
	
	public void msgExchange(String message) {
		
		
		
	}
	
	
	public String getSig() throws IOException {/*m�thode permettant de r�cuperer la signalisation*/
		
		InputStream sig = null ;/*On d�clare le InputStream pour r�cup�rer la signalisation*/
		sig = this.clientSocketRX.getInputStream();	/*On r�cup�re la signalisation en entr�e du serveur*/
		String SIG = sig.toString();/*Conversion en chaine de caract�re*/

		return SIG;
	}
    
	public void sendSig(String SIG) throws IOException {/*m�thode permettant d'envoyer la signalisation*/
		
		this.toServer = new DataOutputStream(this.clientSocketTX.getOutputStream());
		this.toServer.writeChars(SIG);/*On �crit la signalisation voulue*/
		this.toServer.flush();/*On balance tout*/
		
	}
	
	
	
	
	}
	


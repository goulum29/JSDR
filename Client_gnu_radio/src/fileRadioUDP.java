/*A faire, r�cup�rer un fichier via le chemin absolu et l'envoyer via TCP*/
/*Pour pas impl�menter une sig style SOF "Start Of File" & EOF "End Of File" */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class fileRadioUDP {

	private FileInputStream fileIn = null;
	private DatagramSocket clientSocket ;/*D�claration objet clientTCP*/
	private ObjectOutputStream outStream;
	private InetAddress IPAddress;
	private DatagramPacket sendPacket;
	private int port;
	public fileRadioUDP(String absolutePath,String IPaddr,int port) throws IOException {

		this.clientSocket = new DatagramSocket();			
		this.fileIn = new FileInputStream(absolutePath);
		this.IPAddress = InetAddress.getByName(IPaddr);	
		this.port=port;	
	}
	
	public void start() throws IOException {
		byte[] buffer = new byte[8];
		
        // On cr�e une variable de type int pour y affecter le r�sultat de
        // la lecture
        // Vaut -1 quand c'est fini
		
        int n = 0;
        
        while ((n = fileIn.read(buffer)) >= 0) {//Envoi tant que le fichier entier n'as pas �t� lu
        	
        	
        	
        	this.sendPacket = new DatagramPacket(buffer,buffer.length, this.IPAddress, this.port);// A placer ici ?
        	clientSocket.send(sendPacket);
            // On envoie via le socket
            // On affiche ce qu'a lu notre boucle au format byte et au
            // format char
           /* for (byte bit : buffer) {

              System.out.print("\t" + bit + "(" + (char) bit + ")");

           }

            System.out.println("");*/

            //Nous r�initialisons le buffer � vide
            //au cas o� les derniers byte lus ne soient pas un multiple de 8
            //Ceci permet d'avoir un buffer vierge � chaque lecture et ne pas avoir de doublon en fin de fichier

            buffer = new byte[8];


         }

         System.out.println("Tranfert termin�");
		
	

	
	}
	
	
}

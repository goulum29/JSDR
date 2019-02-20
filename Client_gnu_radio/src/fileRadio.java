/*A faire, récupérer un fichier via le chemin absolu et l'envoyer via TCP*/
/*Pour pas implémenter une sig style SOF "Start Of File" & EOF "End Of File" */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class fileRadio {

	private FileInputStream fileIn = null;
	private Socket clientSocket ;/*Déclaration objet clientTCP*/
	private ObjectOutputStream outStream;
	
	public fileRadio(String absolutePath,String IPaddr,int port) throws IOException {

			this.clientSocket = new Socket(IPaddr, port);
			this.fileIn = new FileInputStream(absolutePath);
			
			
	}
	
	public void start() throws IOException {
		byte[] buffer = new byte[8];
		
        // On crée une variable de type int pour y affecter le résultat de
        // la lecture
        // Vaut -1 quand c'est fini
		
        int n = 0;
        while ((n = fileIn.read(buffer)) >= 0) {//Envoi tant que le fichier entier n'as pas été lu
        	this.outStream = new ObjectOutputStream(this.clientSocket.getOutputStream());// A placer ici ?
            // On envoie via le socket 
            outStream.write(buffer);
            outStream.flush();
            // On affiche ce qu'a lu notre boucle au format byte et au
            // format char
            for (byte bit : buffer) {

               System.out.print("\t" + bit + "(" + (char) bit + ")");

            }

            System.out.println("");

            //Nous réinitialisons le buffer à vide
            //au cas où les derniers byte lus ne soient pas un multiple de 8
            //Ceci permet d'avoir un buffer vierge à chaque lecture et ne pas avoir de doublon en fin de fichier

            buffer = new byte[8];


         }

         System.out.println("Tranfert terminé");
		
	

	
	}
	
	
}

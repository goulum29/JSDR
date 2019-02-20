import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class clientTCP {
	//chaine de sortie
	String sortie;
	//chaine entre
	String entre;
	
	private Socket clientSocket ;/*Déclaration objet clientTCP*/
    private Scanner clavier;/*Déclaration objet clavier*/
    /*Ne fonctionne pas pour l'instant utilité de TCP ? pour le transfert de fichier sans doute*/
    clientTCP(String serverAddress, int serverPort) throws Exception {
    	
        this.clientSocket = new Socket(serverAddress, serverPort);
        
        this.clavier = new Scanner(System.in);
    
    }
    
    void start() throws IOException {
        
    	String input;
       
    	while (true) {
        	
        
            input = clavier.nextLine();
            
            PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            
            out.println(input);
            
            out.flush();
            
        }
    }
    
}
    
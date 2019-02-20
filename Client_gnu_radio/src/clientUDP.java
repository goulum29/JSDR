
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class clientUDP {
	//chaine de sortie
	String sortie;
	//chaine entre
	String entre;
	private DatagramSocket clientSocket ;
	private DatagramPacket sendPacket;/*Déclaration objet clientTCP*/
    private Scanner clavier;/*Déclaration objet clavier*/
	private InetAddress IPAddress;
	private int port;
    /*fonctionne*/
    clientUDP(String serverAddress, int serverPort) throws Exception {
    	
    	this.clientSocket = new DatagramSocket();
		this.IPAddress = InetAddress.getByName(serverAddress);
		this.port=serverPort;
        
        this.clavier = new Scanner(System.in);
    
    }
    
    void start() throws IOException {
        
    	String input;
    	
       
    	while (true) {
    		
    		input = clavier.nextLine();
    		this.sendPacket = new DatagramPacket(input.getBytes(),input.getBytes().length, this.IPAddress, port);
			clientSocket.send(sendPacket);
			input = "\n";
			this.sendPacket = new DatagramPacket(input.getBytes(),input.getBytes().length, this.IPAddress, port);
			clientSocket.send(sendPacket);
           
            
           
            
        }
    }
    
}
    
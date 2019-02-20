import java.util.Scanner;
import java.io.IOException;
import java.net.UnknownHostException;

public class main {

	public static void main(String[] args) throws Exception {
		
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in); // Création de l'objet scanner (lecture entrée clavier)
		
		System.out.println("Veuillez renseigner l'adresse IP de l'emétteur : ");

		String adresseIPemetteur = sc.nextLine();

		System.out.println("Veuillez renseigner le port de l'emétteur : ");
		
		String portemetteur = sc.nextLine();
		System.out.println("Voulez vous établir une communication bi-dirrectionnele ? y/n");
		String choix = sc.nextLine();
		if(choix == "y") {/*Choix de l'établissement d'une communication bidirectionnele*/
			
		System.out.println("Veuillez renseigner l'adresse IP du récepteur : ");

		String adresseIPrecepteur = sc.nextLine();

		System.out.println("Veuillez renseigner le port du récepteur : ");
		
		String portrecepteur = sc.nextLine();
		}
		System.out.println("Voulez vous enregistrer ces valeurs pour les prochaines executions ? y/n ");
		
		String enregistrement = sc.nextLine();
		
		System.out.println("Voulez vous transferer un fichier, chatter, parler ? 'f' pour fichier, 'c' pour chatter, 'v' pour parler, exit pour sortir  ");
		
		String choix_emission = sc.nextLine();
        
		System.out.println("Quel protocole de transmission voulez-vous choisir ? TCP choix '1' ou UDP choix '2'");
		
		String choix_protocole = sc.nextLine();
		
		/* Conversion string to int*/
		int portemetteur_entier = Integer.parseInt(portemetteur);
		
		
		
		
		while(choix_emission != "exit") {
		
		switch(choix_emission) {	
		
		case "f" :/*Transfer de fichier*/
			String absolutePath;
			System.out.println("Tranfert de fichier activé");
			System.out.println("Veuillez renseigner le chemin absolu du fichier");
			absolutePath = sc.nextLine();//On renseigne le chemin absolu du fichier
			if(choix_protocole == "1") {//choix tcp
			fileRadio file1 = new fileRadio(absolutePath, adresseIPemetteur, portemetteur_entier);//Création de l'objet de transfert
			file1.start();// démarrage de l'objet
			}else {
				fileRadioUDP file1 = new fileRadioUDP(absolutePath, adresseIPemetteur, portemetteur_entier);//Création de l'objet de transfert
				file1.start();// démarrage de l'objet
			}
			break;
			
		case "c" :/*Messagerie texte*/
			
			System.out.println("Messagerie activée");
			
			
			
			/*Thread ThreadClient = new Thread() {*/
				
				//public void run() {/*Thread client*/
				
					//try {
						if(choix_protocole == "1") {//choix tcp
						clientTCP client1 = new clientTCP(adresseIPemetteur,portemetteur_entier);//Objet chat TCP
						client1.start(); 
						}else {//choix udp
			            clientUDP client1 = new clientUDP(adresseIPemetteur,portemetteur_entier);//Objet chat UDP
			            client1.start(); 
						}
			            System.out.println("\r\nConnected to Server: " + adresseIPemetteur + ":" + portemetteur_entier);
				                       
				        
					//} catch (Exception e) {
					//	System.out.println(e);
					//}
					
			//	}};
				
				if(choix == "y") {
				Thread ThreadServer = new Thread() {/*Thread Serveur*/
					
					public void run() {
						serverTCP server1;
						try {
							server1 = new serverTCP("localhost",9999);
							System.out.println("\r\nRunning Server: " + "Host=" + server1.getServeurAddress().getHostAddress() + " Port=" + server1.getPort());
							server1.listen();
						} catch (Exception e1) {
							
							System.out.println(e1);
							
						}
					
						
					}};
				
				ThreadServer.start();	
				}
				
				//ThreadClient.start();
				
				
			break;
			
		case "v" :/*Discuter en vocale*/
			System.out.println("Communication vocale activée");
			/*Valeurs en dure mais doit pouvoir être choisi*/
			if(choix_protocole == "1") {//choix tcp
		    voiceDigital voix1 = new voiceDigital(adresseIPemetteur,portemetteur_entier,48000 ,16 ,1,true ,true );
		    voix1.start();
			}else {//choix udp
			voiceDigitalUdp voix1 = new voiceDigitalUdp(adresseIPemetteur,portemetteur_entier,48000 ,16 ,1,true ,true );
			voix1.start();
			}
			
			break;
		
		
		}
		//choix_emission = sc.nextLine();/*Attente du caractère de sortie*/		
		}
			
	}//fin main
	
	
}//Fin de classe



	
	


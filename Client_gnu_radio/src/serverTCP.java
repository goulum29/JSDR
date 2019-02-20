import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class serverTCP {/*Classe de serveur TCP à ne pas utiliser, à faire pour du full duplex, faire la même en udp*/
	 
	  
    private ServerSocket server;
    
    public serverTCP(String ipAddress,int port) throws Exception {
    	
        if (ipAddress != null && !ipAddress.isEmpty()) { 
        	
          this.server = new ServerSocket(port);
          
        }else{
        	
        	this.server = new ServerSocket(port);
          
        }
    }
    
    void listen() throws Exception {
    	
        String data = null;
        
        Socket client = this.server.accept();
        
        String clientAddress = client.getInetAddress().getHostAddress();
        
        System.out.println("\r\nNew connection from " + clientAddress);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));    
        
        while ((data = in.readLine()) != null ){
        	
            System.out.println("\r\nMessage from " + clientAddress + ": " + data);
            
        }
    }
    public InetAddress getServeurAddress() {
    	
        return this.server.getInetAddress();
        
    }
    
    public int getPort() {
    	
        return this.server.getLocalPort();
    }
	  
	  
}

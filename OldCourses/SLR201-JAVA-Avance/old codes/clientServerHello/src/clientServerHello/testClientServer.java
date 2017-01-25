package clientServerHello;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class testClientServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	   String finStr="Fin de la Connexion";
	   InetAddress inetAddress= null;
	   try {
		inetAddress= InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   int port= 50000;
	   Server server= new Server(port);
	   Client client= new Client(inetAddress,port);	   
	   client.setFinStr(finStr);
	   server.setFinStr(finStr);
		
	   client.start();
	   server.start();	   
	}

}

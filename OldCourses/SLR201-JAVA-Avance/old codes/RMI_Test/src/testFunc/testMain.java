package testFunc;

import client.HelloClient;
import server.HelloServer;

public class testMain {

	public static void main(String[] args) {
//		System.setProperty("java.security.policy", "security.policy");
		// TODO Auto-generated method stub
		if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
            System.setSecurityManager(new SecurityManager());
        }
		String message= "je suis en france!";
		HelloServer helloServer = new HelloServer(message);
		HelloClient helloClient = new HelloClient();
//		helloServer.setHelloServer(helloServer);
		helloServer.start();
		helloClient.start();
		
		
	}

}

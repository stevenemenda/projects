package client;

import hello.Hello;
import hello.SayHello;

import java.net.InetAddress;
import java.net.UnknownHostException;
//import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.AccessController;


/**
 * the client that will connect to the remote server object (HelloServer), 
 * 		request a message from it and display the message received 
 * 
 * --> by default, the message will be displayed as text in the output console; 
 * --> use HelloClientGraphic instead for displaying the result graphically (in a GUI window) 
 */
public class HelloClient extends Thread{

	//attribute storing the message from the server
	String message = "";
	
	//the name of the hello service to use
	static final String HELLO_SERVICE_NAME = "HelloService";
//	private String host= InetAddress.getLocalHost();// default: use local host
	private String host= "localhost";
	// default: use local host
//	private int port= 10009;
	private int port= 50007;
	
	public HelloClient () {
		
	}

	/**
	 * main method: check input parameters and create a HelloClient object
	 * 
	 * @param args: two input parameters expected: 
	 * 	1. the listening port of the rmiregistry executing on the server 
	 * 	2. the server's host name 
	 */
	public void run () {
//		public static void main (String args[]) {
		/*if (args.length != 2) {
			System.out.println ("Deux arguments : 1) port-rmiregistry; 2) machine ");
			System.exit (2);
		}*/
		// Port permettant d'acceder a rmiregistry
		//
//		int portRmiregistry = new Integer(args[0]).intValue();
//		int portRmiregistry = 50007;
		// Machine du rmiregistry du serveur
//		String remoteHost = args[1];
//		String remoteHost = null;
		
//		host= null;// use local host!
		//new HelloClient(remoteHost, portRmiregistry);
		try {
			InetAddress inetAddr= InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//if there is no security manager then create one
			if (System.getSecurityManager() == null) {
//	            System.setSecurityManager(new SecurityManager());
	            System.setSecurityManager(new SecurityManager());
	        }
			//get a reference to the remote object Registry on the specified host and port.
			Registry registry = LocateRegistry.getRegistry(host, port);
			
			//lookup the hello service in the rmiregistry using its service name
			//obtain a reference to the remote server object
			//this reference will be used later to call methods on the remote object (e.g. readMessage())
			SayHello sayHello = (SayHello)registry.lookup(HELLO_SERVICE_NAME);
			
			System.out.println("Connection to service: " + HELLO_SERVICE_NAME + "; on host: " + host);

			//use the remote object's reference to call methods on this object 
			//the remote object executes on the server host 
			this.message = sayHello.readMessage ();
			
			//print the message
			System.out.println("Message from remote HelloService: " + this.message);
		}

		catch (Exception e) {
			System.out.println ("Hello exception: " + e.getMessage ());
			e.printStackTrace ();
		}

	}



}

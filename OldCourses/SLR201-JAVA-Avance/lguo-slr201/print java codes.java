// client

package client;

import hello.Hello;

//import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * the client that will connect to the remote server object (HelloServer), 
 * 		request a message from it and display the message received 
 * 
 * --> by default, the message will be displayed as text in the output console; 
 * --> use HelloClientGraphic instead for displaying the result graphically (in a GUI window) 
 */
public class HelloClient {

	//attribute storing the message from the server
	String message = "";
	
	//the name of the hello service to use
	static final String HELLO_SERVICE_NAME = "HelloService";

	
	public HelloClient (String host, int port) {
		try {
			//if there is no security manager then create one
			if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
			//get a reference to the remote object Registry on the specified host and port.
			Registry registry = LocateRegistry.getRegistry(host, port);
			
			//lookup the hello service in the rmiregistry using its service name
			//obtain a reference to the remote server object
			//this reference will be used later to call methods on the remote object (e.g. readMessage())
			Hello hello = (Hello)registry.lookup(HELLO_SERVICE_NAME);
			
			System.out.println("Connection to service: " + HELLO_SERVICE_NAME + "; on host: " + host);

			//use the remote object's reference to call methods on this object 
			//the remote object executes on the server host 
			this.message = hello.readMessage ();
			
			//print the message
			System.out.println("Message from remote HelloService: " + this.message);
		}

		catch (Exception e) {
			System.out.println ("Hello exception: " + e.getMessage ());
			e.printStackTrace ();
		}

	}

	/**
	 * main method: check input parameters and create a HelloClient object
	 * 
	 * @param args: two input parameters expected: 
	 * 	1. the listening port of the rmiregistry executing on the server 
	 * 	2. the server's host name 
	 */
	public static void main (String args[]) {
		if (args.length != 2) {
			System.out.println ("Deux arguments : 1) port-rmiregistry; 2) machine ");
			System.exit (2);
		}
		// Port permettant d'acceder a rmiregistry
		//
		int portRmiregistry = new Integer(args[0]).intValue();
		// Machine du rmiregistry du serveur
		String remoteHost = args[1];

		new HelloClient(remoteHost, portRmiregistry);
	}
}


// server

package server;

import hello.Hello;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;


/**
 * the server class: implements the Remote interface Hello 
 * so its objects will be remote objects (accessible remotely) 
 */
public class HelloServer implements Hello {
 
	//stores the message to be returned to clients
	private String message;
	
	//the symbolic name under which the HelloServer object 
	//	will be registered in the rmiregistry  - Java's basic default naming service
	static final String HELLO_SERVER_NAME = "HelloService";

	//implements the readMessage method defined in the Hello interface 
	//Note the @Override annotation: 
	//		helps check that the method's signature matches the interface definition
	@Override
	public String readMessage() throws RemoteException {
		return message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * main method: 
	 * 	> checks input arguments, 
	 * 	> creates a HelloServer instance, and 
	 * 	> registers the instance with the rmiregistry
	 *  
	 * @param args: expected input parameters:
	 * 		1. the port on which the local rmiregistry is listening 
	 * 		2. the message to return to clients, when they call the readMessage method 
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
     
		//check parameters 
		if (args.length != 2) {
			System.out.println("Deux arguments : 1) port-rmiregistry; 2) message");
			System.exit(1);
		}
      
		//if there is no security manager then create one
		if (System.getSecurityManager() == null) 
			System.setSecurityManager(new RMISecurityManager());

		try {
 
			//create a HelloServer instance (object) to be invoked by remote clients
			HelloServer myHelloServer = new HelloServer();

			//customize the HelloServer instance:
			//	set the value of the message attribute using the second input parameter
			myHelloServer.setMessage(args[1]);

			//the port on which the local rmiregistry is listening 
			int rmiPort = new Integer(args[0]).intValue();
			
			//export the HelloServer instance to the RMI runtime, 
			//		so that it can receive remote method calls;
            //the second parameter (0) is the TCP port to use 
			//		to listen for incoming remote invocation requests for the object
            // 		-- it is common to use the value zero, which specifies the use of an anonymous port.
			//
            Hello stub = (Hello)UnicastRemoteObject.exportObject(myHelloServer, 0);

            //obtain a reference to the rmiregistry executing on the local host on the specified port (default port 1099)
            //	--> for TP: please follow TP instructions to set customized port, based on your student ID
            //
            //for security reasons, an application can only bind, unbind, or rebind remote object references 
            //	with a RmiRegistry running **on the same host**.
            Registry registry = LocateRegistry.getRegistry(rmiPort);
            
            //register the stub with the rmiregistry
            registry.rebind(HELLO_SERVER_NAME, stub);

			System.out.println("HelloServer bound: " + HELLO_SERVER_NAME);
		} 
		catch (RemoteException e) {
			System.out.println("HelloServer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}

// Hello

package hello;

/**
 * the remote object's interface
 *  
 * this interface must be public and must implement java.rmi.Remote
 * all remote methods must throw java.rmi.RemoteException
 */
public interface Hello extends java.rmi.Remote {
	
	/**
	 * returns a message to (remote) clients
	 * @return a message
	 * @throws java.rmi.RemoteException
	 */
	String readMessage() throws java.rmi.RemoteException;
}



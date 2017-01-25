package server;

import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.security.AccessControlContext;
import java.security.AccessController;

import hello.Hello;
import hello.SayHello;


/**
 * the server class: implements the Remote interface Hello 
 * so its objects will be remote objects (accessible remotely) 
 */
public class HelloServer extends Thread {
 
	//stores the message to be returned to clients
	private SayHello sayHello= null;
//	private HelloServer helloServer;// used for referring itself;
//	public void setHelloServer(HelloServer helloServer) {
//		this.helloServer = helloServer;
//	}
	
	//the symbolic name under which the HelloServer object 
	//	will be registered in the rmiregistry  - Java's basic default naming service
	static final String HELLO_SERVER_NAME = "HelloService";

	//implements the readMessage method defined in the Hello interface 
	//Note the @Override annotation: 
	//		helps check that the method's signature matches the interface definition
	public HelloServer(String message){
		sayHello= new SayHello(message);
	}

	public void setMessage(String message) {
		sayHello.setMessage(message);
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
	public void run() {
//		public static void main(String args[]) throws IOException {
     
		//check parameters 
		/*if (args.length != 2) {
			System.out.println("Deux arguments : 1) port-rmiregistry; 2) message");
			System.exit(1);
		}*/
      
		//if there is no security manager then create one
		if (System.getSecurityManager() == null){
//			SecurityManager securityManager= new SecurityManager();
//			AccessControlContext acc= AccessController.getContext();
			System.setSecurityManager(new SecurityManager());// change RMISecurityManager() to SecurityManager()
		}
			

		try {
 
			//create a HelloServer instance (object) to be invoked by remote clients
//			HelloServer helloServer = new HelloServer();// change myHelloServer to helloServer 
//			HelloServer myHelloServer = new HelloServer();

			//customize the HelloServer instance:
			//	set the value of the message attribute using the second input parameter
	//		myHelloServer.setMessage(args[1]);
			sayHello.setMessage("guolieqiang@telecom-paristech.fr");

			//the port on which the local rmiregistry is listening 
//			int rmiPort = new Integer(args[0]).intValue();
			int rmiPort = 50007;
			
			//export the HelloServer instance to the RMI runtime, 
			//		so that it can receive remote method calls;
            //the second parameter (0) is the TCP port to use 
			//		to listen for incoming remote invocation requests for the object
            // 		-- it is common to use the value zero, which specifies the use of an anonymous port.
			//
            Hello stub = (Hello)UnicastRemoteObject.exportObject(sayHello, rmiPort);//change 0 to rmiPort
            if (stub!= null){
            	//obtain a reference to the rmiregistry executing on the local host on the specified port (default port 1099)
	            //	--> for TP: please follow TP instructions to set customized port, based on your student ID
	            //
	            //for security reasons, an application can only bind, unbind, or rebind remote object references 
	            //	with a RmiRegistry running **on the same host**.
	            Registry registry = LocateRegistry.getRegistry(rmiPort);
	            
	            //register the stub with the rmiregistry
	            registry.rebind(HELLO_SERVER_NAME, stub);
	
				System.out.println("HelloServer bound: " + HELLO_SERVER_NAME);
            }else{
            	System.out.println("server stub: is null" + HELLO_SERVER_NAME);
            }
            
		} 
		catch (RemoteException e) {
			System.out.println("HelloServer exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}

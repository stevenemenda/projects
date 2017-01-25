package philoCS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FourchetteServer extends Thread {
	private ServerSocket socket= null;
	private Socket ioSocket= null;
	private int port= 50000;
	private boolean startServer= true;
	public FourchetteServer(int port){
		this.port= port;
	}	
	public void setStartServer(boolean startServer) {
		this.startServer = startServer;
	}
	private boolean initialServer(){
		
		try {
			socket= new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (socket==null){
			//System.out.println("initial serverSocket error");
			return false;
		}else{
			//System.out.println("initial serverSocket succeed");
			return true;
		}
				
	}
	public void run(){
		if (initialServer()){
			while(startServer){	
				try {
					ioSocket=socket.accept();
					//System.out.println("sever socket accepts a connetion");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				AllocFourchetteService allocService= new AllocFourchetteService(ioSocket);
				allocService.start();
			}
		}		
	}

	
	
}

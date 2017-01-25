package persistenceSocketFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	ServerSocket socket= null;
	Socket ioSocket= null;
	private int port=49152;
	private String finStr=null;
	
	public Server(int port){
		this.port= port;
	}
	public void setFinStr(String finStr) {
		this.finStr = finStr;
	}
	public void initialSocket(){
		if (port<49152){
			return;
		}
		try {
			socket= new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ioSocket=socket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		initialSocket();
		InputStream inStream= null;
		OutputStream outStream= null;
		if (ioSocket==null){
			return;
		}
		try {
			outStream= ioSocket.getOutputStream();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			inStream= ioSocket.getInputStream();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int countByte=0;
		byte[] inByte= new byte[50];
		String message=null;
		while(true){			
			try {
				countByte= inStream.read(inByte);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message= new String(inByte,0,countByte);
			if (message.contains(finStr)){
				break;
			}
			System.out.println("Serveur a recu un message: "+ message);
			message= "Hello "+ message+"!";
			try {
				outStream.write(message.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		System.out.println("le Serveur est en train de éteindre!");
		try {
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}

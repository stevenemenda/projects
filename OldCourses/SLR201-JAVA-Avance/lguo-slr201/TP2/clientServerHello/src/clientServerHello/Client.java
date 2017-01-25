package clientServerHello;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread{
	Socket socket= null;
	InetAddress inetAddress= null;
	private int port=49152;
	private String finStr= null;
	public Client(InetAddress inetAddress, int port){
		this.inetAddress= inetAddress;
		this.port= port;
	}
	public void setFinStr(String finStr) {
		this.finStr = finStr;
	}
	public void initialSocket(){
		if (inetAddress==null){
			return;
		}else if (port<49152){
			return;
		}
		socket= new Socket();
		try {
			InetSocketAddress endpoint = new InetSocketAddress(inetAddress,port);
			socket.connect(endpoint,100000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		initialSocket();
		if (socket==null){
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		InputStream inStream= null;
		OutputStream outStream= null;
		try {
			inStream= socket.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			outStream= socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int countByte=0;
		int messageID=1;
		byte[] inByte= new byte[50];		
		while(true){
			
			if (messageID>5){			
				try {					
					outStream.write(finStr.getBytes());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			String message= new String("Je suis le message "+ messageID);
			try {
				outStream.write(message.getBytes());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				countByte= inStream.read(inByte);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message= new String(inByte,0,countByte);
			System.out.println("Client a recu la reponse: "+ message);
			messageID++;
		}
		System.out.println("le client est en train de déconnecter!");
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

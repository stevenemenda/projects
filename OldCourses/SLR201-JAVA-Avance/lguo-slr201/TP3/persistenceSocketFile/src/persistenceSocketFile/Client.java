package persistenceSocketFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread {
	 Socket socket= null;
	 InetAddress inetAddress= null;
	 private int port=49152;
	 
	 private String finStr= "Fin de la Connexion";
	 
	 private static final String reponseReady= "serverReady"; 
	 private static final String reponseOK= "serializedOK"; 
	 private static String commandSerialized= "Je veux serializer un objec HelloData!";
	 private static String commandUnserialized= "Je veux unserializer un objec HelloData!";
	 
	 private InputStream inStream= null;
	 private OutputStream outStream= null;
	 private ObjectOutputStream objectOutStream= null;
	 private ObjectInputStream objectInStream= null;
	 
	 private HelloData helloDataSerial= null;
	 private HelloData helloDataUnserial= null;
	 
	 private String[] tasks= null;
	 
	public Client(InetAddress inetAddress, int port){
		this.inetAddress= inetAddress;
		this.port= port;
		helloDataSerial= new HelloData();
	}
	public void setTasks(String[] tasks) {
		this.tasks = tasks;
	}
	public HelloData getHelloData() {
		return helloDataSerial;
	}
	public void setHelloData(HelloData helloData) {
		this.helloDataSerial = helloData;
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
		try {
			inStream= socket.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			objectInStream= new ObjectInputStream(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStream= socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutStream= new ObjectOutputStream(outStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String readReponseInStream(){
		String str= null;
		try {
			str= (String) objectInStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
				
		/*int countByte= 0;
		byte[] inByte= new byte[50];		
		String readData= null;
		try {
			countByte= inStream.read(inByte);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		readData= new String(inByte,0,countByte);
		return readData;*/
	}
	

	public HelloData readHelloDataInStream(){
		HelloData helloData= null;
		try {
			helloData=(HelloData) objectInStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return helloData;
	}
	
	public void writeOutStream(String infoStr){
		try {
			objectOutStream.writeObject(infoStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeHelloDataOutStream(){
		try {
			objectOutStream.writeObject(helloDataSerial);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean checkHelloSerUnserMessage(){
		if (helloDataSerial.getMessage().equals(helloDataUnserial.getMessage())){
			return true;
		}else{
			return false;
		}
	}
	public boolean checkHelloSerUnserTransientMessage(){
		if (helloDataSerial.getTransientMessage().equals(helloDataUnserial.getTransientMessage())){
			return true;
		}else{
			return false;
		}
	}
 	public void closeClient(){
		try {
			inStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectInStream.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		initialSocket();
		for(int i=0;i<tasks.length;i++){
			if (tasks[i].equals(commandSerialized)){
				writeOutStream(commandSerialized);
				if(readReponseInStream().equals(reponseReady)){
					writeHelloDataOutStream();
					System.out.println("Client: sending helloData to Sever for being serialized in a file!");
					if(readReponseInStream().equals(reponseOK)){
						System.out.println("Client a recu une reponse: helloData is serialized well!");
					}
				}
			}else if (tasks[i].equals(commandUnserialized)){
				System.out.println("Client: requiring Server unserializing helloData!");
				writeOutStream(commandUnserialized);
				helloDataUnserial= readHelloDataInStream();
				System.out.println("Client: reciving unserialized helloData from Server!");
				System.out.println("Client: comparing local helloData and the helloData unserialized from Server!");
				if (checkHelloSerUnserMessage()){
					System.out.println("Client: the content of the filed message in both is the same: "+ helloDataUnserial.getMessage());
				}else{
					System.out.println("Client: helloData unserialized failed!");
				}
				if (checkHelloSerUnserTransientMessage()){
					System.out.println("Client: helloData unserialized failed!");
				}else{
					System.out.println("Client: the content of the filed transientMessage in both is the different: "
							+ "local helloData.transientMessage= "+ helloDataSerial.getTransientMessage()+"; "
									+ "unserialized helloData.transientMessage= "+ helloDataUnserial.getTransientMessage());
				}
			}else if (tasks[i].equals(finStr)){
				System.out.println("Client: la fin de task!");
				writeOutStream(finStr);// it's the last command				
			}
		}		
		//System.out.println("Client helloDataUnser: "+helloDataUnserial.getMessage());
		System.out.println("le client est en train de deconnecter!");
		closeClient();
		
	}
}

package persistenceSocketFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TraiteThread extends Thread {
	private Socket socket= null;
	private static String finStr= "Fin de la Connexion";
	
	private final long helloDataSerializedVersion= 20160926L;
	
	private static final String reponseReady= "serverReady"; 
    private static final String reponseOK= "serializedOK"; 
	private static String commandSerialized= "Je veux serializer un objec HelloData!";
	private static String commandUnserialized= "Je veux unserializer un objec HelloData!";
	
	private File file= new File("SerializedHelloData.txt");
	boolean outPutStyle= true;// false vers console; true vers fichier.
	
	private InputStream inStreamSocket= null;
	private OutputStream outStreamSocket= null;
	private ObjectOutputStream objectOutStreamSocket= null;
	private ObjectInputStream objectInStreamSocket= null;
	
	private ObjectOutputStream objectOutStreamFile= null;
	private ObjectInputStream objectInStreamFile= null;
	
	private FileOutputStream fileOutObj= null;
	private FileInputStream fileInObj= null;
	
	private HelloData helloDataSerial= null;
	private HelloData helloDataUnserial= null;
	
	public TraiteThread(Socket ioSocket) {
		// TODO Auto-generated constructor stub
		this.socket= ioSocket;
	}
	
	public void initialTraiteThread(){
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				inStreamSocket= socket.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				outStreamSocket= socket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				objectOutStreamSocket= new ObjectOutputStream(outStreamSocket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				objectInStreamSocket= new ObjectInputStream(inStreamSocket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fileInObj= new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				objectInStreamFile= new ObjectInputStream(fileInObj);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				fileOutObj= new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				objectOutStreamFile= new ObjectOutputStream(fileOutObj);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public boolean serializerHelloData(){
		try {
			objectOutStreamFile.writeObject(helloDataSerial);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean unserializerHelloData(){
		try {
			helloDataUnserial= (HelloData)objectInStreamFile.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("server: helloDataUnserial! "+ helloDataUnserial.getMessage());
		return true;
	}
	public String readCommandInStream(){
		String str= null;
		try {
			str= (String) objectInStreamSocket.readObject();
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
			countByte= inStreamSocket.read(inByte);
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
			helloData=(HelloData) objectInStreamSocket.readObject();
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
			objectOutStreamSocket.writeObject(infoStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutStreamSocket.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeHelloDataOutStream(){
		try {
			objectOutStreamSocket.writeObject(helloDataUnserial);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutStreamSocket.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setFinStr(String finStr) {
		this.finStr = finStr;
	}
	public void closeTraiteThread(){
		try {
			fileInObj.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fileOutObj.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectInStreamFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			inStreamSocket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			objectInStreamSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			objectOutStreamFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			outStreamSocket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			objectOutStreamSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){	//logic!!!	
		initialTraiteThread();
		while(true){
			String message= readCommandInStream();
			if(message.equals(commandSerialized)){
				writeOutStream(reponseReady);
				helloDataSerial= readHelloDataInStream();
				System.out.println("Server: reciving helloData Succeded!");
				if(serializerHelloData()){
					System.out.println("Server: serializing helloData well in a file!");
					writeOutStream(reponseOK);
					
				}		
			}else if(message.equals(commandUnserialized)){
				if(unserializerHelloData()){
					System.out.println("Server: unserializing helloData well from a file!");
					writeHelloDataOutStream();
					System.out.println("Server: sending helloData to Client!");
				}
			}else if(message.equals(finStr)){
				break;
			}			
		}		
		System.out.println("le TraiteThread est en train de fermer!");
		
		closeTraiteThread();
	}
}

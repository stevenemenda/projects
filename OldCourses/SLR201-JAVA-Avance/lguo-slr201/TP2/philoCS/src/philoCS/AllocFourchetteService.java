package philoCS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class AllocFourchetteService extends Thread {
	private static boolean[] fourchettes= {true,true,true,true,true};
	private static final String preFour="Prendre Fourchettes";
	private static final String laiFour="laisser Fourchettes";
	private static final String reponseOK="Reussir";
	private static String finStr= "Fin de la Connexion";
	private static int philoTotal= 5;
	
	private int clientID= -1;
	Socket ioSocket= null;	
	private InputStream inStream= null;
	private OutputStream outStream= null;
	
	public AllocFourchetteService(Socket ioSocket){
		this.ioSocket=ioSocket;
	}
	public void prendreFourchettes(){
	//	System.out.println("fourchettes: "+fourchettes[0] +fourchettes[1]+fourchettes[2] +fourchettes[3] +fourchettes[4]);
		synchronized(fourchettes){
			while(!(fourchettes[clientID]&&fourchettes[(clientID+1) % philoTotal])){
				try {
			//		System.out.println("client "+ clientID+" prend Fourchettes failed "+fourchettes[clientID]+fourchettes[(clientID+1) % philoTotal]);
					fourchettes.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			 }
			try {
			//	System.out.println("client "+ clientID+" prend Fourchettes succeed");
				fourchettes[clientID]= false;				
				fourchettes[(clientID+1) % philoTotal]=false;
				outStream.write(reponseOK.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
			
	}

	public void laisserFourchettes(){
		synchronized(fourchettes){
			fourchettes[clientID]= true;
			fourchettes[(clientID+1) % philoTotal]= true;
			try {
				outStream.write(reponseOK.getBytes());
				fourchettes.notifyAll();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
	}
	public void allocFourchettes(String faireQuoi) {
		// TODO Auto-generated method stub
		if (faireQuoi.equals(laiFour)){ //// tr¨¨s important !!! si faireQuoi== laiFour C'est la comparaison des address!!! c'est pas les contents
			laisserFourchettes();
		}
		else if(faireQuoi.equals(preFour)){
			prendreFourchettes();
		}else{
			//System.out.println("Error Command!");
			return;
		}		
		
	}
	
	public void run(){
		if (ioSocket==null){
		//	System.out.println("ioSocket error");
			return;
		}else{
		//	System.out.println("creat a threadService");
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
					//System.out.println("thread service reads instream");
					countByte= inStream.read(inByte);
					if(countByte!=-1){
						message= new String(inByte,0,countByte);
					//	System.out.println("thread service reads instream succeed");
					}else{
					//	System.out.println("thread service reads instream failed");
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}							
				if (message.contains(finStr)){
					break;
				}else{
					String [] infoStr= message.split(";");
				//	System.out.println("infoStr length: "+infoStr.length);
					if (clientID==-1){
						clientID=Integer.valueOf(infoStr[0]);
					}
					String faireQuoi= infoStr[1];
					//System.out.println("faireQuoi: "+faireQuoi);
					/*if (faireQuoi.equals(laiFour)){
						System.out.println("Serveur a recu un message: "+ "Philo "+clientID+" est en train de laisser deux fourchettes!");
					}else{
						System.out.println("Serveur a recu un message: "+ "Philo "+clientID+" est en train d'appliquer deux fourchettes!");
					}*/		
					allocFourchettes(faireQuoi);												
				}
				
			}
			
			System.out.println("le ServeurTraiteThread "+clientID+ " est en train de ¨¦teindre!");
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
			try {
				ioSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}

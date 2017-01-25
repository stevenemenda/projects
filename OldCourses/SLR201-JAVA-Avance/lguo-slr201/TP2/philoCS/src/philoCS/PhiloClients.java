package philoCS;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class PhiloClients extends Thread{
	private static final String preFour="Prendre Fourchettes";
	private static final String laiFour="laisser Fourchettes";
	private static final String reponseOK="Reussir";
	private static final String reponseNO="Rater";
	private static  String finStr= "Fin de la Connexion";
	private static final int AFFAMER = 0;
	private static final int MANGER = 1;
	private static final int REFLECHIR = 2;
	private static Random rand = new Random();
	private static int FOISMANGERTOTAL = 5;	
	private static int PHILOMAX= 0;
	private static List<Boolean> listfour=new ArrayList<Boolean>();
	private static boolean outPutStyle= false;//"false" means that the informations are outputed to the console otherwise to the file.
	private static String[] outStrInfo=null;
	private static int foisMangerTotal= 0;
	private static FileOutputStream fileOut= null;
	private  Socket socket= null;
	private  InputStream inStream= null;
	private  OutputStream outStream= null;
	
	private InetSocketAddress endPoint= null;
	private long TIME = 0;	
	private int foisManger= 0;
	private int foisAffamer= 0;
	private int foisReflechir= 0;
	private int etat= 0;
	private int philoID= 0;
	
	
	//int i = rand.nextInt(); //int
	//i = rand.nextInt(100); //0-100
	public PhiloClients(int PhiloID, InetSocketAddress inetSocketAddress){
		endPoint=inetSocketAddress;
		philoID= PhiloID;
		PHILOMAX= PHILOMAX+ 1; //si on utilise le symbole "final", ca marche pas!
//		this.FOURCHETTES= Fourchettes;
		listfour.add(true);
	}
	public String sendMessage(String message){
		
		return message;
		
	}
	public static  void setFinStr(String FinStr) {
		finStr = FinStr;
	}
	public static int getPHILOMAX() {
		return PHILOMAX;
	}
	public static void setOutStrInfo(String[] outStrInfo) {
		PhiloClients.outStrInfo = outStrInfo;
	}
	public static void setFileOut(FileOutputStream fileOut) {
		PhiloClients.fileOut = fileOut;
	}
	public static void setOutPutStyle(boolean outPutStyle) {
		PhiloClients.outPutStyle = outPutStyle;
	}
	public static int getFoisMangerTotal() {
		// TODO Auto-generated method stub
		return foisMangerTotal;
	}
	public static void outPutInfo() throws IOException{
		if (outPutStyle==true){
			System.out.println("Les resultats sont dans un fichier!");
			fileOut.write(new String(
					"philoManger APP: Les philosophes ont mang¨¦ totalement "
			+ foisMangerTotal+ " fois! Et on peux aussi le changer sans effort!\r\n").getBytes());
			for (int i=0; i<PHILOMAX; i++){
				fileOut.write(outStrInfo[i].getBytes());
			}
			
		}else if (outPutStyle==false){
			System.out.println("Les resultats sont dans console!");
			for (int i=0; i<PHILOMAX; i++){
				System.out.println(outStrInfo[i]);
			}
		}
		fileOut.close();
	}
	public static void setFoisMangerTotal(int fOISMANGERTOTAL) {
		FOISMANGERTOTAL = fOISMANGERTOTAL; // tr¨¨s important
	}
	public void setEtat(int Etat){
		this.etat=Etat;
	}	

	public int getPhiloID(){
		return philoID;
	}
	public void lasserFourchettes(){
		// lasser les fourchettes
		//it means that notifying all threads which are waiting for FOURCHETTES
		String reponse= fournirMessage(laiFour);
		if (reponse.equals(reponseOK)){
			setEtat(REFLECHIR);
		}
		
		//	System.out.println("Philosophe "+philoID+" est en train de laisser deux fourchettes!"+ FOURCHETTES[philoID] + FOURCHETTES[(philoID+1)%PHILOMAX]);
		
	}
	// synchronizing fonction is different.	
	public void enmanger(){
		TIME=rand.nextInt(256);
		foisMangerTotal++;
		foisManger++;
		System.out.println("Philosophe "+philoID+" est en train de manger pour "
		+TIME+" milliseconds!"+" C'est "+foisManger+" fois de manger!");
		try {
			sleep(TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lasserFourchettes();
	}
	// return the reponse
	public String fournirMessage(String infoStr){
		String temp= new String();
		int count=0;
		byte[] infoByte= new byte[100];
		temp= String.valueOf(philoID);
		temp= temp+";"+infoStr;
		try {
			outStream.write(temp.getBytes());
		//	System.out.println("philo "+philoID+" send message: philoID="+ temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		try {
			count= inStream.read(infoByte);
			temp= new String(infoByte,0,count);
	//		System.out.println("philo "+philoID+" gets reponse:"+ temp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
	}
	public void  enaffamer(){
		foisAffamer++;
		System.out.println("Philosophe "+philoID+" est en train de s'affamer!"+" C'est "+foisAffamer+" fois de s'affamer!");
		//trouver deux fourchettes
		// it specifies that the object to be synchronized is FOURCHETTES!
			//and we can create different objects used to synchronize for others proposes! 
			//and the object must be the same object in others different threads, they all share the same memory space.
			//you can transfer the object by references other than values to others threads!
		String reponse= fournirMessage(preFour);
		
		if (reponse.equals(reponseOK)){
			setEtat(MANGER);			
		}else{
			return;
	//		System.out.println("prendre les Fouchettes error!");
		}
			
	}

	public void enreflechir(){
		TIME=rand.nextInt(256);
		foisReflechir++;
		System.out.println("Philosophe "+philoID+" est en train de reflechir pour "+TIME+" milliseconds!"+" C'est "+foisReflechir+" fois de reflechir!");		
		try {
			sleep(TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		setEtat(AFFAMER);
	}
	public boolean initialClient() {// static object can be  initialed for only once
		// TODO Auto-generated method stub
			socket= new Socket();
			try {
				socket.connect(endPoint,100000);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				
			if (socket==null){
			//	System.out.println("philo "+philoID+" client soket error!");
				return false;
			}else{
			//	System.out.println("philo "+philoID+" get Socket succeed");
				try {
					inStream= socket.getInputStream();
					if(inStream!=null){
			//			System.out.println("philo "+philoID+" get inStream succeed");
					}else{
			//			System.out.println("philo "+philoID+" get inStream failed");
						return false;
					}
										
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				try {
					outStream= socket.getOutputStream();
					if (outStream!= null){
				//		System.out.println("philo "+philoID+" get outStream succeed");
					}else{
				//		System.out.println("philo "+philoID+" get outStream failed");
						return false;
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			return true;			
	}	
	public void run(){
		if(initialClient()){
			while (foisMangerTotal<FOISMANGERTOTAL){
				if (etat== AFFAMER){
					enaffamer();
				}else if (etat== MANGER){
					enmanger();
				}else if (etat== REFLECHIR){
					enreflechir();
				}else etat=AFFAMER;
			}		
			outStrInfo[philoID]="Philo "+ philoID+" a mang¨¦ pour "+foisManger +" fois.\r\n";
			System.out.println("le PhiloClient " + philoID + " est en train de d¨¦connecter!");
			try {
				outStream.write(finStr.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("create PhiloClient " + philoID + " failed!");
		}
		
	}
	
		
}

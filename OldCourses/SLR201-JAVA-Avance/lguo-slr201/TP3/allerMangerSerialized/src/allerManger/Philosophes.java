package allerManger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Philosophes extends Thread{
	private static final int AFFAMER = 0;
	private static final int MANGER = 1;
	private static final int REFLECHIR = 2;
	private static Random rand = new Random();
	private static int FOISMANGERTOTAL = 5;	
	private static int PHILOMAX= 0;
	private static List<Boolean> listfour=new ArrayList<Boolean>();
	private static boolean[ ] FOURCHETTES={true,true,true,true,true};
	private static boolean outPutStyle= false;//"false" means that the informations are outputed to the console otherwise to the file.
	private static String[] outStrInfo=null;
	private static int foisMangerTotal= 0;
	private static FileOutputStream fileOut= null;
	
	private long TIME = 0;	
	private int foisManger= 0;
	private int foisAffamer= 0;
	private int foisReflechir= 0;
	private int etat= 0;
	private int philoID= 0;
	
	
	//int i = rand.nextInt(); //int
	//i = rand.nextInt(100); //0-100
	public Philosophes(int PhiloID){
		philoID= PhiloID;
		PHILOMAX= PHILOMAX+ 1; //si on utilise le symbole "final", ca marche pas!
//		this.FOURCHETTES= Fourchettes;
		listfour.add(true);
	}
	public static int getPHILOMAX() {
		return PHILOMAX;
	}
	public static void setOutStrInfo(String[] outStrInfo) {
		Philosophes.outStrInfo = outStrInfo;
	}
	public static void setFileOut(FileOutputStream fileOut) {
		Philosophes.fileOut = fileOut;
	}
	public static void setOutPutStyle(boolean outPutStyle) {
		Philosophes.outPutStyle = outPutStyle;
	}
	public static void outPutInfo() throws IOException{
		if (outPutStyle==true){
			System.out.println("Les resultats sont dans un fichier!");
			fileOut.write(new String("philoManger APP: Les philosophes ont mang¨¦ totalement "+ foisMangerTotal+ " fois! Et on peux aussi le changer sans effort!\r\n").getBytes());
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
	public static int getFoisManger() {
		return foisMangerTotal;
	}
	public void setEtat(int Etat){
		etat=Etat;
	}	

	public int getPhiloID(){
		return philoID;
	}
	public void lasserFourchettes(){
		// lasser les fourchettes
		synchronized(FOURCHETTES){//it means that notifying all threads which are waiting for FOURCHETTES
			FOURCHETTES[philoID]= true;
			FOURCHETTES[(philoID+1)%PHILOMAX]= true;
			setEtat(REFLECHIR);
			FOURCHETTES.notifyAll();
		//	System.out.println("Philosophe "+philoID+" est en train de laisser deux fourchettes!"+ FOURCHETTES[philoID] + FOURCHETTES[(philoID+1)%PHILOMAX]);
		}
		
	}
	// synchronizing fonction is different.

	public synchronized void prendreFourchettes(){
		// prendre les fourchettes
		FOURCHETTES[philoID]= false;
		FOURCHETTES[(philoID+1)%PHILOMAX]= false;
		setEtat(MANGER);
	}
	
	public void enmanger(){
		TIME=rand.nextInt(256);
		foisMangerTotal++;
		foisManger++;
		System.out.println("Philosophe "+philoID+" est en train de manger pour "+TIME+" milliseconds!"+" C'est "+foisManger+" fois de manger!");
		try {
			sleep(TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lasserFourchettes();
	}
	
	public void  enaffamer(){
		foisAffamer++;
		System.out.println("Philosophe "+philoID+" est en train de s'affamer!"+" C'est "+foisAffamer+" fois de s'affamer!");
		//trouver deux fourchettes
		synchronized (FOURCHETTES){// it specifies that the object to be synchronized is FOURCHETTES!
			//and we can create different objects used to synchronize for others proposes! 
			//and the object must be the same object in others different threads, they all share the same memory space.
			//you can transfer the object by references other than values to others threads!
			while (!(FOURCHETTES[philoID] && FOURCHETTES[(philoID+1)%PHILOMAX])){
				try {
					FOURCHETTES.wait();// it means that "I am waiting for the notice of FOURCHETTES!";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		prendreFourchettes();	
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
	public void run(){
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
	}		
}

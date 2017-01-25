package allerManger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Philosophes extends Thread{
	private static final int AFFAMER = 0;
	private static final int MANGER = 1;
	private static final int REFLECHIR = 2;
	private static Random rand = new Random();
	private static final int FOISMANGER = 5;
	private static int PHILOMAX= 0;
	private static List<Boolean> listfour=new ArrayList<Boolean>();
	private static boolean[ ] FOURCHETTES={true,true,true,true,true};
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
		foisManger++;
		System.out.println("Philosophe "+philoID+" est en train de manger pour "+TIME+" milliseconds!"+" C'est "+foisManger+" fois de manger!"+" FOURCHETTES= "+ FOURCHETTES[philoID] + FOURCHETTES[(philoID+1)%PHILOMAX]);
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
		System.out.println("Philosophe "+philoID+" est en train de s'affamer!"+" C'est "+foisAffamer+" fois de s'affamer!"+" FOURCHETTES= "+ FOURCHETTES[philoID] + FOURCHETTES[(philoID+1)%PHILOMAX]);
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
		System.out.println("Philosophe "+philoID+" est en train de reflechir pour "+TIME+" milliseconds!"+" C'est "+foisReflechir+" fois de reflechir!"+" FOURCHETTES= "+ FOURCHETTES[philoID] + FOURCHETTES[(philoID+1)%PHILOMAX]);		
		try {
			sleep(TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		setEtat(AFFAMER);
	}
	public void run(){
		while (foisManger<FOISMANGER){
			if (etat== AFFAMER){
				enaffamer();
			}else if (etat== MANGER){
				enmanger();
			}else if (etat== REFLECHIR){
				enreflechir();
			}else etat=AFFAMER;
		}		
	}		
}

package philoCS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class allerManger {	
	public static  void main(String[] args) throws IOException, InterruptedException {
		String finStr="Fin de la Connexion";
	    InetAddress inetAddress= null;
	    try {
		 inetAddress= InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (inetAddress== null){
	    	System.out.println("main InetAddress error");
	    }
	    int port= 50000;
	    InetSocketAddress endPoint= new InetSocketAddress(inetAddress,port);
	    
		File file= new File("MangerFois.txt");
		boolean outPutStyle= true;// false vers console; true vers fichier.
		if (!file.exists()){
			file.createNewFile();
		}	
		int foisMangerTotal= 12; // environ 12 fois!
		FileOutputStream fileOut=null;
		fileOut= new FileOutputStream(file);
		
		PhiloClients.setFileOut(fileOut);
		PhiloClients.setOutPutStyle(outPutStyle);
		PhiloClients.setFoisMangerTotal(foisMangerTotal);			
		
		PhiloClients philo5= new PhiloClients(4, endPoint);
		PhiloClients philo4= new PhiloClients(3, endPoint);
		PhiloClients philo3= new PhiloClients(2, endPoint);
		PhiloClients philo2= new PhiloClients(1, endPoint);
		PhiloClients philo1= new PhiloClients(0, endPoint);
		PhiloClients.setFinStr(finStr);
		
		FourchetteServer fourServer= new FourchetteServer(port);
						
		String[] outStrInfo= new String[PhiloClients.getPHILOMAX()];
		PhiloClients.setOutStrInfo(outStrInfo);
		
		fourServer.start();
		
		philo1.start();
		philo2.start();
		philo3.start();
		philo4.start();
		philo5.start();
		
		philo1.join();
		philo2.join();
		philo3.join();
		philo4.join();
		philo5.join();		
		
		PhiloClients.outPutInfo();
		// TODO Auto-generated method stub
		System.out.println("philoManger APP: Les philosophes ont mang¨¦ totalement "+PhiloClients.getFoisMangerTotal()+ " fois! Et on peux aussi le changer sans effort!");
	}
	
}

package allerManger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class allerManger {	
	public static  void main(String[] args) throws IOException, InterruptedException {
		
		File file= new File("MangerFois.txt");
		boolean outPutStyle= true;// false vers console; true vers fichier.
		if (!file.exists()){
			file.createNewFile();
		}	
		int foisMangerTotal= 12; // environ 12 fois!
		FileOutputStream fileOut=null;
		fileOut= new FileOutputStream(file);
		Philosophes.setFileOut(fileOut);
		Philosophes.setOutPutStyle(outPutStyle);
		Philosophes.setFoisMangerTotal(foisMangerTotal);			
		
		Philosophes philo5= new Philosophes(4);
		Philosophes philo4= new Philosophes(3);
		Philosophes philo3= new Philosophes(2);
		Philosophes philo2= new Philosophes(1);
		Philosophes philo1= new Philosophes(0);
		
		String[] outStrInfo= new String[Philosophes.getPHILOMAX()];
		Philosophes.setOutStrInfo(outStrInfo);
		
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
		
		Philosophes.outPutInfo();
		// TODO Auto-generated method stub
		System.out.println("philoManger APP: Les philosophes ont mang¨¦ totalement "+Philosophes.getFoisManger()+ " fois! Et on peux aussi le changer sans effort!");
	}
	
}

package allerManger;


public class AllerManger {	
	public static  void main(String[] args) {
/*		boolean[] fourchettes={true,true,true,true,true};
		philosophes philo1= new philosophes(0,fourchettes);
		philosophes philo2= new philosophes(1,fourchettes);
		philosophes philo3= new philosophes(2,fourchettes);
		philosophes philo4= new philosophes(3,fourchettes);
		philosophes philo5= new philosophes(4,fourchettes);*/
		Philosophes philo5= new Philosophes(4);
		Philosophes philo4= new Philosophes(3);
		Philosophes philo3= new Philosophes(2);
		Philosophes philo2= new Philosophes(1);
		Philosophes philo1= new Philosophes(0);

		philo1.start();
		philo2.start();
		philo3.start();
		philo4.start();
		philo5.start();
		// TODO Auto-generated method stub
		System.out.println("philoManger APP: chaque philosophe mange pour 5 fois! Et on peux aussi le changer sans effort!");
	}
	
}

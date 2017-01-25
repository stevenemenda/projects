package hello;

import java.rmi.RemoteException;

public class SayHello implements Hello {
	String message= null;
	public SayHello(String message){
		this.message= message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String readMessage() throws RemoteException {
		// TODO Auto-generated method stub
		return message;
	}

}

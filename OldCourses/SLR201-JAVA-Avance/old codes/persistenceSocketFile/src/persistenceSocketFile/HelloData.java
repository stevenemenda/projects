package persistenceSocketFile;

import java.io.Serializable;

public class HelloData implements Serializable{
	transient String transientMessage= ""; 
	
	private String message = "";

	public String getTransientMessage() {
		return transientMessage;
	}

	public void setTransientMessage(String transientMessage) {
		this.transientMessage = transientMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

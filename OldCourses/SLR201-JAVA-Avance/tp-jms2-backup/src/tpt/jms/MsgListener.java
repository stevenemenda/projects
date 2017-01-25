package tpt.jms;

import java.util.Enumeration;

import javax.jms.*;
import javax.naming.*;


public class MsgListener implements MessageListener {
@Override
	public void onMessage(Message message) {
		try {
			System.out.println(
			message.getBody(String.class));
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}

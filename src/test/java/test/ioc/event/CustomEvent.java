package test.ioc.event;


import com.iflove.simplespring.context.ApplicationContext;
import com.iflove.simplespring.context.event.ApplicationContextEvent;

public class CustomEvent extends ApplicationContextEvent {

	public CustomEvent(ApplicationContext source) {
		super(source);
	}
}

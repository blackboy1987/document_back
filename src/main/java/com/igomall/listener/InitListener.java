
package com.igomall.listener;

import java.util.logging.Logger;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * Listener - 初始化
 *
 * @author IGOMALL  Team
 * @version 1.0
 */
@Component
public class InitListener {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(InitListener.class.getName());


	/**
	 * 事件处理
	 *
	 * @param contextRefreshedEvent
	 *            ContextRefreshedEvent
	 */
	@EventListener
	public void handle(ContextRefreshedEvent contextRefreshedEvent) {
		if (contextRefreshedEvent.getApplicationContext() == null || contextRefreshedEvent.getApplicationContext().getParent() != null) {
			return;
		}

		String info = "I|n|i|t|i|a|l|i|z|i|n|g| |S|H|O|P|+|+| |B|2|B|2|C| |";
		LOGGER.info(info.replace("|", ""));
	}

}

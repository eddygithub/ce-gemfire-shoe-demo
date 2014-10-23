package com.gopivotal.ce.gemfire.events;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.gemstone.gemfire.cache.query.CqEvent;

@Component("cqListener")
public class CQListener {
	
	private Map<String, Object> stringObjectMap = new ConcurrentHashMap<>();
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    SimpMessageSendingOperations sender;
    

    public void handleEvent(CqEvent event) {
        stringObjectMap.put("key", event.getKey());
        stringObjectMap.put("value", event.getNewValue());
        stringObjectMap.put("date", dateFormat.format(new Date()));
		sender.convertAndSend("/topic/alarms", new StockAlert(event));
    }
    
    static class StockAlert {
		public final String key;
    	public final Object value;
    	public final String date;
    	
    	public StockAlert(CqEvent event) {
    		   this.key = (String) event.getKey();
    		   this.value = event.getNewValue();
    		   this.date = dateFormat.format(new Date());

		}

    	
    	
    }
}

/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.serverside.listeners;

import io.pivotal.ce.gemfire.fastfootshoes.model.Alert;

import java.util.List;

import com.gemstone.gemfire.cache.asyncqueue.AsyncEvent;
import com.gemstone.gemfire.cache.asyncqueue.AsyncEventListener;

/**
 * @author lshannon
 *
 */
public class AsyncAlertWriter implements AsyncEventListener {
	
	private AlertsDAO alertsDAO;

	public void close() {
	}

	@SuppressWarnings("rawtypes")
	public boolean processEvents(List<AsyncEvent> events) {
		for (AsyncEvent asyncEvent : events) {
			Alert alert = (Alert) asyncEvent.getDeserializedValue();
			if (asyncEvent.getOperation().isCreate()) {
				alertsDAO.insert(alert);
			}
		}
		return true;
	}

	public AlertsDAO getAlertsDAO() {
		return alertsDAO;
	}

	public void setAlertsDAO(AlertsDAO alertsDAO) {
		this.alertsDAO = alertsDAO;
	}
	
	

}

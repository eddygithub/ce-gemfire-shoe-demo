/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

/**
 * @author lshannon
 *
 */
@Region("Alert")
public class Alert {
	
	private String message;
	private Date messageDate;
	@Id
	private String id;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Alert [message=" + message + ", messageDate=" + messageDate
				+ ", id=" + id + "]";
	}

}

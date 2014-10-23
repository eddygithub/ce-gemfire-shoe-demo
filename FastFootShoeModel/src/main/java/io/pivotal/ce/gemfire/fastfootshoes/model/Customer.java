/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

/**
 * Contains all the data relevant to a individual customer
 * @author lshannon
 */
@Region("Customer")
public class Customer {
	
	private String name;
	private String emailAddress;
	private String city;
	private Date birthday;
	@Id
	private String id;
	
	public Customer() {
		
	}
	
	
	
	public Customer(String name, String emailAddress, String city,
			Date birthday, String id) {
		super();
		this.name = name;
		this.emailAddress = emailAddress;
		this.city = city;
		this.birthday = birthday;
		this.id = id;
	}



	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", emailAddress=" + emailAddress
				+ ", city=" + city + ", birthday=" + birthday + ", id=" + id
				+ "]";
	}

}


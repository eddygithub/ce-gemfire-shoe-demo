/**
 * 
 */
package io.pivotal.ce.gemfire.fastfootshoes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.Region;

/**
 * This object stores the details on how mark up the cost of a product
 * @author lshannon
 */
@Region("MarkUp")
public class MarkUp {

	private double rate;
	private String levelName;
	private int qualifyingTransactionCountFloor;
	private int qualifyingTransactionCountCeiling;
	@Id
	private String id;
	
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public int getQualifyingTransactionCountFloor() {
		return qualifyingTransactionCountFloor;
	}
	public void setQualifyingTransactionCountFloor(
			int qualifyingTransactionCountFloor) {
		this.qualifyingTransactionCountFloor = qualifyingTransactionCountFloor;
	}
	public int getQualifyingTransactionCountCeiling() {
		return qualifyingTransactionCountCeiling;
	}
	public void setQualifyingTransactionCountCeiling(
			int qualifyingTransactionCountCeiling) {
		this.qualifyingTransactionCountCeiling = qualifyingTransactionCountCeiling;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "MarkUp [rate=" + rate + ", levelName=" + levelName
				+ ", qualifyingTransactionCountFloor="
				+ qualifyingTransactionCountFloor
				+ ", qualifyingTransactionCountCeiling="
				+ qualifyingTransactionCountCeiling + ", id=" + id + "]";
	}
	
}

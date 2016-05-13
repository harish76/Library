/**
 * 
 */
package se.contribe;

/**
 * @author harish
 *
 */
public enum Status {

	OK(0),
	NOT_IN_STOCK(1),
	DOES_NOT_EXIST(2);

	private Status(Integer status) {
		this.status = status;
	}

	private Integer status;
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

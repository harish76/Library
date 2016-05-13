/**
 * 
 */
package se.contribe;

/**
 * @author harish
 *
 */
public enum Constants {

	COLUMN_COUNTS(4, "COLUMN_COUNTS"),
	DOWNLOAD_PATH(0, "library.data.downloadPath");

	private Constants(int value, String name) {
		this.value = value;
		this.name = name;
	}
	private int value;
	private String name;

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}

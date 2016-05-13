/**
 * 
 */
package se.contribe.bo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author harish
 *
 */
public class Book implements Serializable {

	private static final long serialVersionUID = 5980994637245791311L;

	private String title;
	private String author;
	private BigDecimal price;
	private Long noOfStocks;

	public Book(String title, String author, BigDecimal price, Long noOfStocks) {
		this.title = title;
		this.author = author;
		this.price = price;
		this.noOfStocks = noOfStocks;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Long getNoOfStocks() {
		return noOfStocks;
	}
	public void setNoOfStocks(Long noOfStocks) {
		this.noOfStocks = noOfStocks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", price=" + price + ", noOfStocks=" + noOfStocks + "]";
	}
}

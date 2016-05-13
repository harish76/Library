/**
 * 
 */
package se.contribe.service;

import se.contribe.bo.Book;

/**
 * @author harish
 *
 */
public interface BookList {

	public Book[] list(String searchString);
	public boolean add(Book book, double amount);
	public int[] buy(Book... books);
}

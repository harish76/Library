/**
 * 
 */
package se.contribe.service;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import se.contribe.Status;
import se.contribe.bo.Book;
import se.contribe.exception.BookStatusException;
import se.contribe.load.BookDetailsDownloader;

/**
 * @author harish
 *
 */
public class BookListImpl implements BookList {

	private static final Logger logger = LoggerFactory.getLogger(BookListImpl.class);
	/* (non-Javadoc)
	 * @see se.contribe.service.BookList#list(java.lang.String)
	 */
	public Book[] list(String searchString) {
		//Use able to search by title and author. 
		CacheManager bookDetails;
		try {
			bookDetails = BookDetailsDownloader.getAllBooksDetails();
		} catch (Exception e) {
			logger.error("Exception in list method of BookListImpl : ", e);
			return null;
		}
		HashSet<Book> books = null;
		String key = (searchString.trim().charAt(0) + "_title").toLowerCase();
		Cache titleCache = bookDetails.getCache(key);
		if(titleCache != null) {
			//Declare initial size of array list, otherwise it will more costly while copying.
			books = new HashSet<>(titleCache.getSize() * (3/ 2));
			addSearchedBook(books, titleCache, searchString);
		} 
		key = (searchString.trim().charAt(0) + "_auther").toLowerCase();
		Cache autherCache = bookDetails.getCache(key);
		if(autherCache != null) {
			if(books == null) {
				books = new HashSet<>(autherCache.getSize() * (3/ 2));
			}
			addSearchedBook(books, autherCache, searchString);
		}

		if(books.isEmpty()) {
			throw new BookStatusException(Status.DOES_NOT_EXIST);
		}
		else {
			Book[] bookArray = new Book[books.size()];
			return books.toArray(bookArray);

		}
	}

	public boolean add(Book book, double amount) {
		CacheManager bookDetails;
		try {
			bookDetails = BookDetailsDownloader.getAllBooksDetails();
		} catch (Exception e) {
			return false;
		}
		String key = (book.getTitle().charAt(0) + "_title").toLowerCase();
		Cache cache = bookDetails.getCache(key);
		if(cache != null) {
			Book bookObj;
			for (Object eachKey : cache.getKeys()) {
				bookObj = (Book)cache.get(eachKey).getValue();
				if(book.getTitle().equalsIgnoreCase(bookObj.getTitle()) && book.getAuthor().equalsIgnoreCase(bookObj.getAuthor())) {
					if(amount == bookObj.getPrice().doubleValue()) {
						return true;
					} 
				}
			}
		}
		return false;
	}

	public int[] buy(Book... books) {
		int[] cost = new int[books.length];
		int count = 0 ;
		for(Book book : books) {
			cost[count] = book.getPrice().intValue();
		}
		return cost;
	}

	/**
	 *  This method prepares searched book list.
	 * @param books
	 * @param cache
	 * @param searchString
	 */
	private void addSearchedBook(HashSet<Book> books, Cache cache, String searchString) {
		Book book = null;
		boolean notInStock = false;
		for (Object key  : cache.getKeys()) {
			book = (Book)cache.get(key).getValue();
			if(book.getTitle().equalsIgnoreCase(searchString)  || book.getAuthor().equalsIgnoreCase(searchString)) {
				if(book.getNoOfStocks() > 0) {
					books.add(book);
				} else {
					notInStock = true;
				}
			}
		}
		if(notInStock && books.isEmpty()) {
			throw new BookStatusException(Status.NOT_IN_STOCK);
		}
	}
}

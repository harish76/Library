package se.contribe.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import se.contribe.bo.Book;

public class BookListImplTest {

	@Test
	public void searchBooksByAuther() {
		BookList bookList = new BookListImpl();
		String searchString = "Rich Bloke";
		Book[] actuals = bookList.list(searchString);
		Book[] expected = {new Book("How To Spend Money", "Rich Bloke", new BigDecimal ("1000000.00"), 1l)};
		assertArrayEquals(expected, actuals);
	}

	@Test
	public void searchBooksByTitle() {
		BookList bookList = new BookListImpl();
		String searchString = "Random Sales";
		Book[] actuals = bookList.list(searchString);
		Book[] expected = {new Book("Random Sales", "Cunning Bastard", new BigDecimal ("499.50"), 3l),
				new Book("Random Sales", "Cunning Bastard", new BigDecimal ("999.00"), 20l)};
		assertArrayEquals(expected, actuals);
	}

	@Test
	public void addValidBookCost() {
		BookList bookList = new BookListImpl();
		Book book = new Book("Random Sales", "Cunning Bastard", null, 3l);
		assertTrue( bookList.add(book, 499.50));
	}

	@Test
	public void addInvalidBookCost() {
		BookList bookList = new BookListImpl();
		Book book = new Book("Random Sales", "Cunning Bastard", null, 3l);
		assertFalse( bookList.add(book, 666.56));
	}


}

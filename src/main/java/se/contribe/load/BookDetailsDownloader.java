/**
 * 
 */
package se.contribe.load;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import se.contribe.Constants;
import se.contribe.bo.Book;
import se.contribe.config.ReadPropertiesFile;

/**
 * @author harish
 *
 */
public class BookDetailsDownloader {

	private static final Logger logger = LoggerFactory.getLogger(BookDetailsDownloader.class);
	private static CacheManager bookDetails = null;

	public static CacheManager getAllBooksDetails()  throws Exception {
		if(bookDetails == null) {
			synchronized (BookDetailsDownloader.class) {
				loadAllBooks();
			}
		}
		return bookDetails;
	}

	/**
	 * 
	 * @param downloadPath
	 */

	public static  void loadAllBooks() throws Exception {
		BufferedReader br = null;
		try {
			Properties properties = ReadPropertiesFile.getProperties();
			String downloadPath = properties.getProperty(Constants.DOWNLOAD_PATH.getName());
			URL recordsURL = new URL(downloadPath);
			URLConnection connection = recordsURL.openConnection();
			br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//1. Create a cache manager
			bookDetails = CacheManager.getInstance();
			String inputLine;
			String key;
			Book book;
			while ((inputLine = br.readLine()) != null) {
				inputLine = inputLine.trim();
				//Mastering åäö;Average Swede;762.00;15
				String[] dataArray  = inputLine.split(";");
				if(dataArray.length == Constants.COLUMN_COUNTS.getValue()) {
					try {
						BigDecimal price = new BigDecimal(dataArray[2].trim().replaceAll(",", ""));
						book = new Book(dataArray[0].trim(), dataArray[1].trim(), price, Long.parseLong(dataArray[3].trim()));
					} catch(Exception e) {
						logger.warn("Invalid record and ignoring : ", inputLine);
						continue;
					}
				} else {
					logger.warn("Column length mismatch and ignoring the record : ", inputLine);
					continue;
				}
				//TODO When number of books increases below 'key' logic needs to upgrade. We need to take more characters instead of one character.
				key = (dataArray[0].charAt(0) + "_title").toLowerCase();
				addBookInCache(key, book);
				key = (dataArray[1].trim().charAt(0) + "_auther").toLowerCase();
				addBookInCache(key, book);
			}
			logger.info("All book details loaded successfully.");
		} catch (Exception e) {
			logger.error("Exception while fetching records from URL: ", e);
			throw e;
		}
		finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Exception while closing BufferedReader object: ", e);
				}
		}
	}

	private static void addBookInCache(String key, Book book) {
		Cache cache = bookDetails.getCache(key);
		if(cache == null) {
			//It is the first book list for this character.
			bookDetails.addCache(key);
			cache = bookDetails.getCache(key);
		}
		int count =  cache.getSize() + 1;
		cache.put(new Element(count, book));
	}

	public static void shutdownCache() {
		if(bookDetails != null) {
			bookDetails.shutdown();
		}
	}
}

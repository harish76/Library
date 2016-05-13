/**
 * 
 */
package se.contribe.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author harish
 *
 */
public class ReadPropertiesFile {
	private static final Logger logger = LoggerFactory.getLogger(ReadPropertiesFile.class);
	private ReadPropertiesFile() {
	}

	public static  Properties getProperties() {
		return Inner.PROPERTIES;
	}

	private static class  Inner {
		private static  Properties PROPERTIES = new Properties();
		static {
			InputStream inputStream = null;
			try {
				String propFileName = "config.properties";
				inputStream = ReadPropertiesFile.class.getClassLoader().getResourceAsStream(propFileName);
				if (inputStream != null) {
					PROPERTIES.load(inputStream);
					logger.info("Properties file loaded successfully.");
				} else {
					throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath.");
				}
			} catch (IOException e) {
				logger.error("Exception while reading InputStream:", e);
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Exception while closing InputStream:", e);
				}
			}
		}
	}
}

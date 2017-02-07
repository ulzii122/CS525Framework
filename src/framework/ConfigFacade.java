package framework;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class ConfigFacade {
	// mongo db
	public static String MONGO_ADDR = "localhost";
	public static int MONGO_PORT = 27017;
	public static String DB_NAME = "";

	public static String MAIL_SERVICE_HOST = "";
	public static int MAIL_SERVICE_PORT = 0;
	public static String MAIL_USER_NAME = "";
	public static String MAIL_PASSWORD = "";
	public static String MAIL_FROM_ADDRESS = "";

	public ConfigFacade() {
		final Properties props = new Properties();

		try {
			props.load(new FileInputStream("conf/framework.config"));

			for (Field f : ConfigFacade.class.getDeclaredFields()) {
				if (props.containsKey(f.getName())) {
					// System.out.println(f.getName() + ": " + f.get(null));
					try {
						f.setAccessible(true);
						if (f.getType().equals(int.class)) {
							f.setInt(null, Integer.parseInt(props.get(f.getName()).toString()));
						} else if (f.getType().equals(boolean.class)) {
							f.setBoolean(null, Boolean.parseBoolean(props.get(f.getName()).toString()));
						} else if (f.getType().isArray()) {
						} else {
							f.set(null, props.get(f.getName()));
						}
					} catch (Exception ex) {
						System.out.println("Field " + f.getName() + " has error!!! " + ex);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
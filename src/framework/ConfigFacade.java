package framework;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class ConfigFacade {

	public final String configFile = "conf/framework.config";

	// mongo db
	public static String MONGO_ADDR = "127.0.0.1";
	public static int MONGO_PORT = 27017;

	public String MAIL_SERVICE_HOST = "";
	public int MAIL_SERVICE_PORT = 0;
	public String MAIL_USER_NAME = "";
	public String MAIL_PASSWORD = "";
	public String MAIL_FROM_ADDRESS = "";

	public ConfigFacade() {
		final Properties props = new Properties();

		try {
			props.load(new FileInputStream(configFile));

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
							if (f.getName().equals("GRAPE_PORTS")) {
								String[] tmpStr = props.get(f.getName()).toString().split(",");
								int[] tmp = new int[tmpStr.length];

								int i = 0;
								for (String str : tmpStr) {
									tmp[i] = Integer.parseInt(str);
									i++;
								}

								f.set(null, tmp);
							}
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
package framework;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DAOFacade {
	private MongoClient db = null;
	private Datastore db_common_Datastore;
	private MongoDatabase db_common;
	private Morphia morphia;
	private final String DB_COMMON = "framework_db";
	private static DAOFacade instance = null;

	private DAOFacade() {
		db = new MongoClient(ConfigFacade.MONGO_ADDR, ConfigFacade.MONGO_PORT);
		morphia = getMorphia();
	}

	public static DAOFacade getInstance() {
		if (instance == null) {
			synchronized (DAOFacade.class) {
				if (instance == null)
					instance = new DAOFacade();
			}
		}
		return instance;
	}

	// --------------------------------
	public void closeDB() {
		if (db != null)
			db.close();
	}

	public Datastore getCommonDb_Datastore() {
		if (db_common_Datastore == null)
			db_common_Datastore = getMorphia().createDatastore(db, DB_COMMON);

		return db_common_Datastore;
	}

	public MongoDatabase getCommonDb() {
		if (db_common == null)
			db_common = db.getDatabase(DB_COMMON);

		return db_common;
	}

	private Morphia getMorphia() {
		if (morphia == null) {
			morphia = new Morphia();
			morphia.mapPackage("framework.model");
		}
		return morphia;
	}
}

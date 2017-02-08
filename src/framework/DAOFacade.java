package framework;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DAOFacade {
	private MongoClient db = null;
	private Datastore datastore;
	private Morphia morphia;
	private static DAOFacade instance = null;

	private DAOFacade() {
		new ConfigFacade();

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

	public Datastore getDatastore() {
		if (datastore == null)
			datastore = getMorphia().createDatastore(db, ConfigFacade.DB_NAME);

		return datastore;
	}

	private Morphia getMorphia() {
		if (morphia == null) {
			morphia = new Morphia();
			morphia.mapPackage("framework.model");
			morphia.mapPackage("framework.model.impl");
			morphia.mapPackage("bank.model");
		}
		return morphia;
	}
}

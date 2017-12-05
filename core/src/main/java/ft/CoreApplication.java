package ft;

import ft.spec.model.DepositAddon;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class CoreApplication implements CommandLineRunner {
	@Autowired
	private Datastore datastore;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongodb();
	}

	// TODO MongoDB.Morphia
	private void mongodb(){

		/*Morphia morphia = new Morphia();

		final Mapper mapper = morphia.getMapper();
		final CloneMapper helper = new CloneMapper(mapper);

		// Metadata
		helper.map(MetadataSkeleton.class, Metadata.class);
		mapper.getMappedClass(Metadata.class).update();

		// DepositAddon
		helper.map(MetadataSkeleton.class, DepositAddon.class);
		mapper.getMappedClass(DepositAddon.class).update();

		// Common mapper setting
		mapper.getOptions().setStoreNulls(true);

		Datastore datastore = morphia.createDatastore(new MongoClient(), "outwit-FT");
		datastore.ensureIndexes();*/

		/*DepositAddon hnapay = new DepositAddon();
		hnapay.setName("Hnapay");
		hnapay.setMode(DepositAddon.Mode.INTERMEDIARY);
		hnapay.setType(DepositAddon.Type.PYTHON);
		datastore.save(hnapay);
		DepositAddon simple = new DepositAddon();
		simple.setName("Simple");
		simple.setMode(DepositAddon.Mode.BANK);
		simple.setType(DepositAddon.Type.JAVA);
		datastore.save(simple);*/

		Query<?> query = datastore.createQuery(DepositAddon.class);
		System.out.println(query.asList());
	}
}

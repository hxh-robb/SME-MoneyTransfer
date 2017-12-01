package ft;

import com.mongodb.MongoClient;
import ft.repo.mongodb.skeleton.CloneMapper;
import ft.repo.mongodb.skeleton.MetadataSkeleton;
import ft.spec.model.DepositAddon;
import ft.spec.model.Metadata;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class CoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongodb();
	}

	// TODO MongoDB.Morphia
	private void mongodb(){
		Morphia morphia = new Morphia();

		final Mapper mapper = morphia.getMapper();
		final CloneMapper helper = new CloneMapper(mapper);
		helper.map(MetadataSkeleton.class, Metadata.class);
		helper.map(MetadataSkeleton.class, DepositAddon.class);
		mapper.getMappedClass(Metadata.class).update();
		mapper.getMappedClass(DepositAddon.class).update();

		Datastore datastore = morphia.createDatastore(new MongoClient(), "outwit-FT");
		datastore.ensureIndexes();

//		DepositAddon hnapay = new DepositAddon();
//		hnapay.setName("Hnapay");
//		datastore.save(hnapay);
//
//		DepositAddon simple = new DepositAddon();
//		simple.setName("Simple");
//		datastore.save(simple);

		Query<Metadata> query = datastore.createQuery(Metadata.class);
		System.out.println(query.asList());
	}
}

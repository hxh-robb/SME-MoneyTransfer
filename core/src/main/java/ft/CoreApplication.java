package ft;

import ft.repo.FundAccountDAO;
import ft.repo.MetadataDAO;
import ft.spec.model.DepositAddon;
import ft.spec.model.FundAccount;
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
//	@Autowired
//	private Datastore datastore;

	@Autowired
	private MetadataDAO metadataDao;

	@Autowired
	private FundAccountDAO fundAccountDao;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// mongodb_metadata();
		mongodb_fund_account();
	}

	private void mongodb_fund_account(){
		FundAccount account = new FundAccount();
		account.setName("Hnapay");
		account.setType("1");
		account.set("partnerID", "Outwit");

		fundAccountDao.create(account);
	}

	// TODO MongoDB.Morphia
	private void mongodb_metadata(){

		/*Morphia morphia = new Morphia();
;
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

		/*Query<?> query = datastore.createQuery(DepositAddon.class);
		System.out.println(query.asList());*/

		// MetadataDAO.Filter f = new MetadataDAO.Filter();
		MetadataDAO.DepositAddonFilter f = new MetadataDAO.DepositAddonFilter();

		DepositAddon addon = new DepositAddon();
		addon.setName("Test");
		addon.setMode(DepositAddon.Mode.INTERMEDIARY);
		addon.setType(DepositAddon.Type.JAVA);
		addon.setDescription("Pseudo addon");
		addon.setValue("-1");
		addon.setContent("ft.addon.PseudoAddon");
		metadataDao.create(addon);

		// f.name = addon.getName();
		// f.catalog = addon.getCatalog();
		// f.mode = DepositAddon.Mode.BANK;
		// f.type = DepositAddon.Type.JAVA;
		/*
		DepositAddon update = new DepositAddon();
		update.setDescription("Fake addon");
		update.setContent("ft.addon.FakeAddon");
		update.setValue("-99");
		update.setEnabled(false);
		// dao.update(f, update);
		*/
		// dao.delete(f);
		System.out.println(metadataDao.list(f));
	}
}

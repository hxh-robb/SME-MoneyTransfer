package ft;

import ft.repo.FundAccountDAO;
import ft.repo.MetadataDAO;
import ft.spec.model.TransferAddon;
import ft.spec.model.FundAccount;
import ft.spec.service.MetadataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

//	@Autowired
//	private MetadataDAO metadataDao;

//	@Autowired
//	private FundAccountDAO fundAccountDao;

	@Autowired
	private MetadataService metadataService;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongodb_metadata();
		// mongodb_fund_account();
	}

	private void mongodb_fund_account(){
		FundAccount account = new FundAccount();
		account.setName("Hnapay");
		account.setType("1");
		account.set("partnerID", "Outwit");

		// fundAccountDao.create(account);
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

		// TransferAddon
		helper.map(MetadataSkeleton.class, TransferAddon.class);
		mapper.getMappedClass(TransferAddon.class).update();

		// Common mapper setting
		mapper.getOptions().setStoreNulls(true);

		Datastore datastore = morphia.createDatastore(new MongoClient(), "outwit-FT");
		datastore.ensureIndexes();*/

		/*TransferAddon hnapay = new TransferAddon();
		hnapay.setName("Hnapay");
		hnapay.setMode(TransferAddon.Mode.INTERMEDIARY_DEPOSIT);
		hnapay.setType(TransferAddon.Type.PYTHON);
		LoggerFactory.getLogger(CoreApplication.class).info("Add Hnapay:{}", metadataService.create("robb",hnapay));

		TransferAddon simple = new TransferAddon();
		simple.setName("Simple");
		simple.setMode(TransferAddon.Mode.BANK_DEPOSIT);
		simple.setType(TransferAddon.Type.JAVA);
		LoggerFactory.getLogger(CoreApplication.class).info("Add Simple:{}", metadataService.create("robb",simple));*/

//		MetadataDAO.Filter filter = new MetadataDAO.Filter();
//		filter.id = simple.getId();
		String simple = "c75307ab-eaad-4756-bc04-562be0e4d125";
		LoggerFactory.getLogger(CoreApplication.class).info("Delete Simple:{}",metadataService.delete("robb",simple));

		/*Query<?> query = datastore.createQuery(TransferAddon.class);
		System.out.println(query.asList());*/

//		// MetadataDAO.Filter f = new MetadataDAO.Filter();
//		MetadataDAO.DepositAddonFilter f = new MetadataDAO.DepositAddonFilter();
//
//		TransferAddon addon = new TransferAddon();
//		addon.setName("Test");
//		addon.setMode(TransferAddon.Mode.INTERMEDIARY);
//		addon.setType(TransferAddon.Type.JAVA);
//		addon.setDescription("Pseudo addon");
//		addon.setValue("-1");
//		addon.setContent("ft.addon.PseudoAddon");
//		metadataDao.create(addon);

		// f.name = addon.getName();
		// f.catalog = addon.getCatalog();
		// f.mode = TransferAddon.Mode.BANK;
		// f.type = TransferAddon.Type.JAVA;
		/*
		TransferAddon update = new TransferAddon();
		update.setDescription("Fake addon");
		update.setContent("ft.addon.FakeAddon");
		update.setValue("-99");
		update.setEnabled(false);
		// dao.update(f, update);
		*/
		// dao.delete(f);
		// System.out.println(metadataDao.list(f));
	}
}

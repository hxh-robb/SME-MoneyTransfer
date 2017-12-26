package ft;

import ft.repo.DAO;
import ft.repo.FundAccountDAO;
import ft.repo.MetadataDAO;
import ft.repo.mariadb.MybatisMetadataDAO;
import ft.spec.model.Metadata;
import ft.spec.model.SemiStructuredEntity;
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

import java.util.List;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class CoreApplication implements CommandLineRunner {
//	@Autowired
//	private Datastore datastore;

	@Autowired
	private MetadataDAO metadataDao;

	@Autowired
	private FundAccountDAO fundAccountDao;

	@Autowired
	private MetadataService metadataService;

//	@Autowired
//	private MybatisMetadataDAO test;

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// mongodb_metadata();
		// mongodb_fund_account();
		mybatis_test();
    }

	private void mybatis_test() {
	    /*List<TransferAddon> result = metadataService.supportedTransferAddons(null);
        System.out.println(result.size());
        System.out.println(result);*/

		/*MetadataDAO.Filter filter = null;
		filter = new MetadataDAO.Filter();
		filter.name = "Hnapay";

		// filter = new MetadataDAO.TransferAddonFilter();
		// filter.mode = TransferAddon.Mode.BANK_DEPOSIT;
		// filter.value = "0";
        System.out.println(metadataDao.list(filter));*/


		/*if( !result.isEmpty() ) {
            TransferAddon addon = null;
//            addon = result.get(0);
//            addon = new TransferAddon();

//            addon.setName("新生");
//            addon.setValue("1");
//            addon.setMode(TransferAddon.Mode.INTERMEDIARY_DEPOSIT);
//            addon.setType(TransferAddon.Type.PYTHON);
//            addon.setDescription(addon.getDescription() + "|修改测试 - Test MetadataService");
//            addon.setContent("Some script will be written");
//            addon.setSpec("Wait for me");

//            System.out.println(metadataService.update(null, addon));
//            System.out.println(metadataService.delete(null, null));
//            System.out.println(metadataService.create(null, addon));
        }*/


		/*filter.id = "077f4dbf-51b7-41a4-b3a3-fd96cf61ba24";
		// TransferAddon addon = new TransferAddon();
		Metadata addon = new Metadata();
		addon.setDescription("Test - 测试修改");
		System.out.println(test.update(filter,addon));
		System.out.println(test.delete(filter));*/
//
//		TransferAddon bank = new TransferAddon();
//        bank.setName("Bank");
//        bank.setDescription("银行卡支付");
//        bank.setValue("0");
//        bank.setMode(TransferAddon.Mode.BANK_DEPOSIT);
//        bank.setType(TransferAddon.Type.JAVA);
//        bank.setSpec("TODO");
//        bank.setContent("TODO");
//        System.out.println(metadataDao.create(bank));
//        TransferAddon hnapay = new TransferAddon();
//        hnapay.setName("Hnapay");
//        hnapay.setDescription("新生支付");
//        hnapay.setValue("1");
//        hnapay.setMode(TransferAddon.Mode.INTERMEDIARY_DEPOSIT);
//        hnapay.setType(TransferAddon.Type.PYTHON);
//        hnapay.setSpec("TODO");
//        hnapay.setContent("TODO");
//        System.out.println(metadataDao.create(hnapay));

		/*MetadataDAO.TransferAddonFilter filter = new MetadataDAO.TransferAddonFilter();
		filter.name = "Simple";
		TransferAddon addon = new TransferAddon();
		addon.setDescription("Test");
		addon.setContent("Some script here");
        System.out.println(metadataDao.update(filter,addon));*/

        /*FundAccount account = new FundAccount();
        account.getInfo().setName("新生7-15");
        account.getInfo().setType("1");
        account.set("Godnn", "NNII");
        account.set("GodAnn", "NN中文SII");
        fundAccountDao.create(account);*/

        /*FundAccountDAO.Filter filter = null;
        filter = new FundAccountDAO.Filter();
        filter.name = "新生7-15";
        System.out.println(fundAccountDao.list(filter));
//        System.out.println(fundAccountDao.listDepositOptions(filter));
        // fundAccountDao.delete(filter);
//        FundAccount data = new FundAccount();
//        data.set("MerchantPass", "OutwitPass");
//        data.set("MerchantID","DDU");
//        data.setDe(true);
//        fundAccountDao.update(filter, data);*/
	}

//	private void mongodb_fund_account(){
//		FundAccount account = new FundAccount();
//		account.setName("Hnapay");
//		account.setType("1");
//		account.set("partnerID", "Outwit");
//
//		// fundAccountDao.create(account);
//	}

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
		hnapay.setValue("01");
		LoggerFactory.getLogger(CoreApplication.class).info("Add Hnapay:{}", metadataService.create("robb",hnapay));

		TransferAddon simple = new TransferAddon();
		simple.setName("Simple");
		simple.setMode(TransferAddon.Mode.BANK_DEPOSIT);
		simple.setType(TransferAddon.Type.JAVA);
        simple.setValue("00");
		LoggerFactory.getLogger(CoreApplication.class).info("Add Simple:{}", metadataService.create("robb",simple));*/

//		MetadataDAO.Filter filter = new MetadataDAO.Filter();
//		filter.id = simple.getId();
//		String simple = "c75307ab-eaad-4756-bc04-562be0e4d125";
//		LoggerFactory.getLogger(CoreApplication.class).info("Delete Simple:{}",metadataService.delete("robb",simple));

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

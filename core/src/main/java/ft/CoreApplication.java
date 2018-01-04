package ft;

import ft.repo.FundAccountDAO;
import ft.spec.model.DepositOption;
import ft.spec.model.DepositSlip;
import ft.spec.model.FundAccount;
import ft.spec.model.TransferAddon;
import ft.spec.service.FundAccountService;
import ft.spec.service.MetadataService;
import ft.spec.service.TransferService;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class CoreApplication implements CommandLineRunner {
//	@Autowired
//	private Datastore datastore;

//	@Autowired
//	private MetadataDAO metadataDao;

	@Autowired
	private FundAccountDAO fundAccountDao;

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private FundAccountService fundAccountService;

    @Autowired
    private TransferService transferService;

//	@Autowired
//	private MybatisMetadataDAO test;


	public static void main(String[] args) throws IOException {
//        PythonInterpreter interpreter = new PythonInterpreter();

//        DepositSlip slip = new DepositSlip();
//        slip.content = "test"; slip.mime = "text/plain"; slip.t = 10.0;
//        interpreter.exec("def test(param):\n  print param\n  param.t=15.0");
//        PyObject func = interpreter.eval("test");
//        func.__call__(Py.java2py(slip));
//        System.out.println(slip);

//	    String code = read("script/test.py");
//        interpreter.exec("import types");
//        interpreter.exec("addon = types.ModuleType('addon')");
//        interpreter.exec(MessageFormat.format("exec ''''''\n{0}\n'''''' in addon.__dict__",code));
//
//        PyObject func = interpreter.eval("addon.test");
//        Map<String, Number> params = new HashMap<>();
//        params.put("count",0);
//
//        Object i = 0;
//        long begin = System.currentTimeMillis();
//        while( System.currentTimeMillis() - begin < 1000) {
//            // i = interpreter.eval("addon.test(" + i + ")").__tojava__(Integer.class);
//            // i = Py.py2int(func.__call__(Py.java2py(i)));
//            // func.__call__(Py.java2py(params));
//            count(params);
//        }
//        System.out.println(params);

         SpringApplication.run(CoreApplication.class, args);

//        String s1 = read("script/test.py");
//        String s2 = read("script/test2.py");
//        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
//        PyHelper helper1 = new PyHelper(s1);
//        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
//        PyHelper helper2 = new PyHelper(s2);
//        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
//        helper1.run("test()");
//        helper2.run("test()");
//        helper1.run("test()");
//        helper2.run("test()");
//        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
//        helper1 = null;
//        helper2 = null;
//        System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
	}
/*
	private static void count(Map<String,Number> params){
	    params.put("count", params.get("count").longValue() + 1);
    }

    private static final class PyHelper{
	    private PythonInterpreter pi;

	    public PyHelper(String script){
	        this.pi = new PythonInterpreter();
            pi.exec(script);
        }

        public void run(String expression){
	        pi.exec(expression);
        }
        public void close(){ pi.close(); }
    }
*/

    @Override
    public void run(String... args) throws Exception {
        // mongodb_metadata();
        // mongodb_fund_account();
        mybatis_test();

//        String s1 = read("script/test.py");
//        String s2 = read("script/test2.py");
//
//        PyHelper helper1 = new PyHelper(s1);
//        PyHelper helper2 = new PyHelper(s2);
//
//        helper1.run("test()");
//        helper2.run("test()");
//        helper1.run("test()");
//        helper2.run("test()");
//
//        helper1.close();
//        helper2.close();
//
//        helper1 = null;
//        helper2 = null;
    }

	private void mybatis_test() {
        List<TransferAddon> addons = metadataService.supportedTransferAddons(null);
        for (TransferAddon addon : addons) {
            if(TransferAddon.Mode.INTERMEDIARY_DEPOSIT.equals(addon.getMode()) && TransferAddon.Type.PYTHON.equals(addon.getType())){
                addon.setContent(read("script/hnapay2.6.py"));
                metadataService.update(null, addon);
            }
        }

        List<DepositOption> list = fundAccountService.supportedDepositOption(null);
        for (DepositOption option:list) {
            System.out.println(transferService.generateDepositSlip(null, option.getId(),  100.0));
        }

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
//            addon.setDescription(addon.getDescription() + "|修改测试 - Test MetadataEntityService");
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

		/*TransferAddon bank = new TransferAddon();
        bank.setName("Bank");
        bank.setDescription("银行卡支付");
        bank.setValue("0");
        bank.setMode(TransferAddon.Mode.BANK_DEPOSIT);
        bank.setType(TransferAddon.Type.JAVA);
        bank.setSpec(read("addon-spec/bank-spec.json"));
        bank.setContent("ft.addon.BankDepositSlipGenerator");
        System.out.println(metadataService.create(null, bank));

        TransferAddon hnapay = new TransferAddon();
        hnapay.setName("Hnapay");
        hnapay.setDescription("新生支付");
        hnapay.setValue("1");
        hnapay.setMode(TransferAddon.Mode.INTERMEDIARY_DEPOSIT);
        hnapay.setType(TransferAddon.Type.PYTHON);
        hnapay.setSpec("TODO");
        hnapay.setContent(read("script/eco.py"));
        System.out.println(metadataService.create(null,hnapay));

        FundAccount hnapayAccount = new FundAccount();
        hnapayAccount.getInfo().setName("新生商户");
        hnapayAccount.setType(hnapay.getValue());
        hnapayAccount.set("Field_0", "ABC");
        hnapayAccount.set("Field_2", "123");
        System.out.println(fundAccountService.create(null, hnapayAccount));

        FundAccount bankAccount = new FundAccount();
        bankAccount.getInfo().setName("银行账号");
        bankAccount.setType(bank.getValue());
        bankAccount.set("account", "6888888888888881");
        bankAccount.set("holder", "江泽民");
        bankAccount.set("bank", "中国中央银行");
        System.out.println(fundAccountService.create(null, bankAccount));*/

		/*List<DepositOption> options = fundAccountService.supportedDepositOption(null);
		DepositOption option = null;
        for (DepositOption o:options) {
            if("银行账号".equals(o.getName()))
                option = o;
        }

        if( null != option ) {
            System.out.println(
                transferService.generateDepositSlip(null, option.getId(), 300.0)
            );
        }*/

		/*MetadataDAO.TransferAddonFilter filter = new MetadataDAO.TransferAddonFilter();
		filter.name = "Simple";
		TransferAddon addon = new TransferAddon();
		addon.setDescription("Test");
		addon.setContent("Some script here");
        System.out.println(metadataDao.update(filter,addon));*/

        /*FundAccountDAO.Filter filter = null;
        filter = new FundAccountDAO.Filter();
        // filter.name = "新生7-15";
        // filter.addon = true;
//        System.out.println(fundAccountDao.list(filter));
//        System.out.println(fundAccountDao.listDepositOptions(filter));
        System.out.println(fundAccountService.supportedDepositOption(null));
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

	private static String read(String file) {
        try {
            FileReader reader = new FileReader(CoreApplication.class.getClassLoader().getResource(file).getFile());
            BufferedReader buffer = new BufferedReader(reader);
            String lines = null, line;
            while( null != (line = buffer.readLine())) {
                if( null == lines)
                    lines = line;
                else
                    lines += "\n" + line;
            }
            return lines;
        } catch (Throwable throwable) {
            return null;
        }
    }
}

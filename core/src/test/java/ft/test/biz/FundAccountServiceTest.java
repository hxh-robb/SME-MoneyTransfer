package ft.test.biz;

import ft.files.FileManager;
import ft.spec.model.FundAccount;
import ft.spec.service.FundAccountService;
import ft.spec.service.Result;
import ft.test.FileHelper;
import ft.test.Testcase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class FundAccountServiceTest extends Testcase implements FileHelper {
    private static final AtomicBoolean resetDone = new AtomicBoolean(false);

    @Autowired @Qualifier("ActualFundAccountService")
    private FundAccountService service;

    @Autowired
    private FileManager fm;

    private String bankIconRawPath, hnapayIconRawPath, ktoolsIconRawPath, hnapayKeystoreRawPath;

    @Before
    public synchronized void reset() throws SQLException {
        reset("fund_account", resetDone);

        // Remote raw files
        bankIconRawPath = fm.sendRemoteRaw(get("files/cmb-cn.png").getPath());
        Assert.assertNotNull(bankIconRawPath); // CMB icon

        hnapayIconRawPath = fm.sendRemoteRaw(get("files/hnapay.png").getPath());
        Assert.assertNotNull(hnapayIconRawPath); // Hnapay icon

        ktoolsIconRawPath = fm.sendRemoteRaw(get("files/ktools.png").getPath());
        Assert.assertNotNull(ktoolsIconRawPath); // KTools icon

        hnapayKeystoreRawPath = fm.sendRemoteRaw(get("files/rsa_11000002915_1503020018091_0.jks").getPath());
        Assert.assertNotNull(hnapayKeystoreRawPath); // Hnapay keystore file
    }


    /**
     * data init - create cmb account
     * @see MetadataServiceTest#t0_BankDepositAddon()
     */
    @Test
    public void t0_BankDepositAccount(){
        FundAccount account = new FundAccount();
        account.getInfo().setName("招商银行");
        account.getInfo().setIcon(bankIconRawPath);
        account.setType("0"); // AddOn value
        account.set("account", "6888888888888881");
        account.set("holder", "迟佧仁");
        account.set("bank", "招商银行");
        account.set("branch", "某省某市某支行");

        Result result = service.create("JUnit", account);
        Assert.assertNotNull(result);
        Assert.assertEquals(FundAccountService.Code.SUCCESS, result.getCode());
    }

    /**
     * data init - create HnaPay account
     * @see MetadataServiceTest#t0_HnaPayDepositAddon()
     */
    @Test
    public void t0_HnaPayDepositAccount(){
        FundAccount account = new FundAccount();
        account.getInfo().setName("新生支付");
        account.getInfo().setIcon(hnapayIconRawPath);
        account.setType("1"); // AddOn value

        // TODO fields
        account.set("__form_action__", "https://gateway.hnapay.com/website/pay.htm");
        account.set("jks", hnapayKeystoreRawPath);
        account.set("jksPassword", "UZMxNb");
        account.set("keyAlias", "hnapaysh");
        account.set("keyPassword", "loUbQB");

        Result result = service.create("JUnit", account);
        Assert.assertNotNull(result);
        Assert.assertEquals(FundAccountService.Code.SUCCESS, result.getCode());
    }

    /**
     * data init - create KTools account
     * @see MetadataServiceTest#t0_KToolsDepositAddon()
     */
    @Test
    public void t0_KToolsDepositAccount(){
        FundAccount account = new FundAccount();
        account.getInfo().setName("KTools支付");
        account.getInfo().setIcon(ktoolsIconRawPath);
        account.setType("2"); // AddOn value
        // TODO

        Result result = service.create("JUnit", account);
        Assert.assertNotNull(result);
        Assert.assertEquals(FundAccountService.Code.SUCCESS, result.getCode());
    }

//    @Test
//    public void showScheme(){
//        System.out.println(service.jsonFormSchema(null));
//    }
}
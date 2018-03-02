package ft.test.biz;

import ft.spec.model.TransferAddon;
import ft.spec.service.MetadataService;
import ft.spec.service.Result;
import ft.test.ReadFileHelper;
import ft.test.Testcase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MetadataServiceTest extends Testcase implements ReadFileHelper {
    @Autowired @Qualifier("ActualMetadataService")
    private MetadataService service;

    @Autowired
    private DataSource ds;
    private static boolean resetDone = false;

    @Before
    public synchronized void reset() throws SQLException {
        if(resetDone)
            return;

        // Clean table `metadata`;
        System.out.println(ds);
        try( Connection c = ds.getConnection() ) {
            try (PreparedStatement ps = c.prepareStatement("delete from metadata") ){
                ps.executeUpdate();
            }
        }
        resetDone = true;
    }

    /**
     * data init - create bank account deposit addon
     */
    @Test
    public void t0_BankDepositAddon(){
        TransferAddon addon = new TransferAddon();
        addon.setName("银行卡直充");
        addon.setDescription("银行卡直充收款渠道");
        addon.setValue("0");
        addon.setMode(TransferAddon.Mode.BANK_DEPOSIT);
        addon.setType(TransferAddon.Type.JAVA);
        addon.setSpec(read("addon/spec/bank-deposit.json"));

        // 银行卡直充存款单生成器
        addon.setContent("ft.addon.BankDepositSlipGenerator");

        Result result = service.create("JUnit", addon);
        Assert.assertNotNull(result);
        Assert.assertEquals(MetadataService.Code.SUCCESS, result.getCode());

        // TODO - assert data inserted to datastore(Use JDBC to get specified record)
    }

    /**
     * data init - create Hnapay deposit addon
     */
    @Test
    public void t0_HnapayDepositAddon(){
        TransferAddon addon = new TransferAddon();
        addon.setName("新生支付");
        addon.setDescription("新生支付收款渠道");
        addon.setValue("1");
        addon.setMode(TransferAddon.Mode.INTERMEDIARY_DEPOSIT);
        addon.setType(TransferAddon.Type.PYTHON);
        addon.setSpec(read("addon/spec/hnapay-deposit.json"));

        // 第三方充值渠道 参数加签/参数验签/复验收款 脚本, 以供生成自动跳转第三方支付网关HTML表单
        addon.setContent(read("addon/script/hnapay_2_6.py"));

        Result result = service.create("JUnit", addon);
        Assert.assertNotNull(result);
        Assert.assertEquals(MetadataService.Code.SUCCESS, result.getCode());

        // TODO - assert data inserted to datastore(Use JDBC to get specified record)
    }

    /**
     * data init - create KTools deposit addon
     */
    @Test
    public void t0_KToolsDepositAddon(){
        TransferAddon addon = new TransferAddon();
        addon.setName("KTools支付");
        addon.setDescription("KTools支付收款渠道");
        addon.setValue("2");
        addon.setMode(TransferAddon.Mode.INTERMEDIARY_DEPOSIT);
        addon.setType(TransferAddon.Type.PYTHON);

        addon.setSpec("TODO");
        addon.setContent("TODO");

        Result result = service.create("JUnit", addon);
        Assert.assertNotNull(result);
        Assert.assertEquals(MetadataService.Code.SUCCESS, result.getCode());

        // TODO - assert data inserted to datastore(Use JDBC to get specified record)
    }
}
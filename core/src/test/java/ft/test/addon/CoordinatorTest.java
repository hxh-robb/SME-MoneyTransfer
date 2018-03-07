package ft.test.addon;

import ft.addon.Coordinator;
import ft.addon.TransferAddonConstant;
import ft.repo.FundAccountDAO;
import ft.spec.model.DepositSlip;
import ft.spec.model.FundAccount;
import ft.spec.model.TransferTask;
import ft.spec.service.FundAccountService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorTest extends ft.test.Testcase {
    @Autowired
    private Coordinator coordinator;

    @Autowired
    private FundAccountDAO dao;

    @Autowired @Qualifier("ActualFundAccountService")
    private FundAccountService service;

    @Test
    public void testHnapayDepositSign(){
        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.name = "新生支付";
        filter.addon = true;
        List<FundAccount> list = dao.list(filter);
        Assert.assertFalse(list.isEmpty());
        FundAccount account = list.get(0);

        TransferTask task = new TransferTask(TransferTask.Type.DEPOSIT);
        task.setAmount(10.0);
        task.setBeneficiary("JUnit");
        task.setPlatform(null);

        Map<String,Object> param = new HashMap<>();
        param.put(TransferAddonConstant.ParamKeys.ACCOUNT, account);
        param.put(TransferAddonConstant.ParamKeys.TASK, task);
        DepositSlip slip = coordinator.execute(account.getAddon().getId(), param);

        // TODO:verify slip(signMsg)
        Assert.assertNotNull(slip);
        System.out.println(slip);
    }
}
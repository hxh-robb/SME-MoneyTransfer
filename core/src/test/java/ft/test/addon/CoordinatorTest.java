package ft.test.addon;

import ft.addon.Coordinator;
import ft.addon.JythonFunctionalityTest;
import ft.addon.TransferAddonConstant;
import ft.repo.FundAccountDAO;
import ft.spec.model.DepositSlip;
import ft.spec.model.FundAccount;
import ft.spec.model.TransferTask;
import ft.spec.service.FundAccountService;
import ft.test.FileHelper;
import ft.test.Testcase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoordinatorTest extends Testcase implements FileHelper {
    @Autowired
    private Coordinator coordinator;

    @Autowired
    private FundAccountDAO dao;

    @Autowired @Qualifier("ActualFundAccountService")
    private FundAccountService service;

//    @Test
//    public void testHnapayDepositSign(){
//        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
//        filter.name = "新生支付";
//        filter.addon = true;
//        filter.addon = true;
//        List<FundAccount> list = dao.list(filter);
//        Assert.assertFalse(list.isEmpty());
//        FundAccount account = list.get(0);
//
//        TransferTask task = new TransferTask(TransferTask.Type.DEPOSIT);
//        task.setAmount(10.0);
//        task.setBeneficiary("JUnit");
//        task.setPlatform(null);
//
//        Map<String,Object> param = new HashMap<>();
//        param.put(TransferAddonConstant.ParamKeys.ACCOUNT, account);
//        param.put(TransferAddonConstant.ParamKeys.TASK, task);
//        DepositSlip slip = coordinator.execute(account.getAddon().getId(), param);
//
//        // TODO:verify slip(signMsg)
//        Assert.assertNotNull(slip);
//        System.out.println(slip);
//    }

    @Test
    public void testPythonHnapaySignatureGenerating(){
        String originalData = "version=2.6&serialID=1520578101631&submitTime=20180309144821&failureTime=20190309144821&customerIP=www.hnapay.com[223.203.208.101]&orderDetails=974663962,100,上海易生,测试机票A,1&totalAmount=100&type=1000&buyerMarked=&payType=ALL&orgCode=&currencyCode=1&directFlag=0&borrowingMarked=0&couponFlag=1&platformID=&returnUrl=https://webtestdev1.hnapay.com/webtest/result/webclient/front/recv.do&noticeUrl=http://10.10.8.70:8082/recepit/notify.do&partnerID=11000002915&remark=测试&charset=1&signType=1";
        String expectedSignature = "4cdfe99e63d59d2b02b8466c8e419d0e7ec96a32f3499ddd1d939b894253c11a83b4261d13152400928c84520c316a30eb7f6f9e859b8e7e513e15acd1d1973fc843caca9e218460edcd056e2837e7651015473de655e42977875abcd4010e1488bf19d020341efd66eb6894016de4b047649e0b009e8b7273759297e94b15c8";

        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.name = "新生支付";
        List<FundAccount> list = dao.list(filter);
        Assert.assertFalse(list.isEmpty());
        FundAccount account = list.get(0);

        Map<String,Object> param = account.getFields();
        param.put("data",originalData);
        JythonFunctionalityTest test = new JythonFunctionalityTest(read("addon/script/test_hnapay_sign.py"));
        Map rt = test.execute(param);
        Assert.assertEquals(originalData, rt.get("data"));
        Assert.assertEquals(expectedSignature, rt.get("signature"));
    }
}
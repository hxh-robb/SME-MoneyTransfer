package com.sme.mts.data.repository;

import com.sme.mts.Testcase;
import com.sme.mts.data.entity.FundAccount;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FundAccountDAOTests extends Testcase {
    private static boolean init = false;
    private static final Map<String, FundAccount> expected = new ConcurrentHashMap<>();

    @Autowired
    private FundAccountDAO fundAccountDAO;

    @Before
    public void init() {
        if(!init){
            fundAccountDAO.delete(null);
            Assert.assertTrue("Cannot clear table data",fundAccountDAO.list(null).isEmpty());
            init = true;
        }
    }

    @Test
    public void t0_createBankAccount(){
        FundAccount account = new FundAccount();
        init(account);
        account.setType(FundAccount.Type.DEPOSIT_ACCOUNT);
        account.setName("dummy-bank-account");
        account.setTitle(bankTitles[RandomUtils.nextInt(0,bankTitles.length)]);
        // account.setIcon("");

        Assert.assertTrue(fundAccountDAO.create(account));
        expected.put(account.getId(), account);
    }

    @Test
    public void t1_listBankAccount(){
        Assert.assertFalse(expected.isEmpty());

        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.id = expected.keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected.size())];
        List<FundAccount> accounts = fundAccountDAO.list(filter);
        Assert.assertEquals(1, accounts.size());
        Assert.assertEquals(expected.get(filter.id), accounts.get(0));
    }

    @Test
    public void t2_modifyBankAccount(){
        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.id = expected.keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected.size())];
        FundAccount data = new FundAccount();
        data.setUo("Robb");
        Assert.assertNotEquals(data.getUo(), expected.get(filter.id).getUo());
        fundAccountDAO.update(filter, data);


        List<FundAccount> accounts = fundAccountDAO.list(filter);
        Assert.assertEquals(1, accounts.size());
        expected.get(filter.id).setUo(data.getUo());
        Assert.assertEquals(expected.get(filter.id), accounts.get(0));
    }

    @Test
    public void t3_deleteBankAccount(){
        int total = fundAccountDAO.list(null).size();

        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.id = expected.keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected.size())];
        List<FundAccount> accounts = fundAccountDAO.list(filter);
        Assert.assertEquals(1, accounts.size());

        Assert.assertEquals(1, fundAccountDAO.delete(filter));
        Assert.assertEquals(total - 1, fundAccountDAO.list(null).size());
    }
}

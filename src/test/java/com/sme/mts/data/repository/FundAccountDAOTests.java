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
import java.util.UUID;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FundAccountDAOTests extends Testcase {
    @Autowired
    private FundAccountDAO fundAccountDAO;
    private static boolean cleared = false;
    private static FundAccount expected;

    @Before
    public void clearTables() {
        if(!cleared){
            fundAccountDAO.delete(null);
            Assert.assertTrue("Cannot clear table data",fundAccountDAO.list(null).isEmpty());
            cleared = true;
        }
    }

    @Test
    public void t0_createBankAccount(){
        FundAccount account = new FundAccount();
        account.setId(UUID.randomUUID().toString());
        account.setDe(false);
        account.setCo(operator);
        account.setUo(operator);
        account.setPlatform(platform);
        account.setType(FundAccount.Type.DEPOSIT_ACCOUNT);
        account.setName("dummy-bank-account");
        account.setTitle(bankTitles[RandomUtils.nextInt(0,bankTitles.length)]);
        // account.setIcon("");

        Assert.assertTrue("Cannot create fund account", fundAccountDAO.create(account));
        expected = account;
    }

    @Test
    public void t1_listBankAccount(){
        List<FundAccount> accounts = fundAccountDAO.list(null);
        Assert.assertTrue("Expected 1 record but " + accounts.size(),accounts.size() == 1);
        Assert.assertEquals(expected, accounts.get(0));
    }
}

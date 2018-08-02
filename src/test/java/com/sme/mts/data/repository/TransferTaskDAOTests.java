package com.sme.mts.data.repository;

import com.sme.mts.Testcase;
import com.sme.mts.data.entity.FundAccount;
import com.sme.mts.data.entity.TransferTask;
import org.apache.commons.lang3.RandomUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransferTaskDAOTests extends Testcase {
    private static boolean init = false;
    private static final Map<String, TransferTask> expected = new ConcurrentHashMap<>();

    @Autowired
    private TransferTaskDAO transferTaskDAO;

    @Before
    public void init() {
        if(!init){
            transferTaskDAO.delete(null);
            Assert.assertTrue("Cannot clear table data",transferTaskDAO.list(null).isEmpty());
            init = true;
        }
    }

    @Test
    public void t0_createBankDepositTask(){
        TransferTask task = new TransferTask();
        init(task);
        task.setType(TransferTask.Type.DEPOSIT);
        task.setBeneficiary("Robb");
        task.setAmount(100.0);
        task.setStatus("New");
        task.setState("Assigning");
        task.setRetry(false);
        // task.setFallback(null);
        task.setRef(UUID.randomUUID().toString());
        task.setFundAccount(UUID.randomUUID().toString());

        Assert.assertTrue(transferTaskDAO.create(task));
        expected.put(task.getId(), task);
    }

    @Test
    public void t1_listBankDepositTask(){
        Assert.assertFalse(expected.isEmpty());

        TransferTaskDAO.Filter filter = new TransferTaskDAO.Filter();
        filter.id = expected.keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected.size())];
        List<TransferTask> tasks = transferTaskDAO.list(filter);
        Assert.assertEquals(1, tasks.size());
        Assert.assertEquals(expected.get(filter.id), tasks.get(0));
    }

    @Test
    public void t2_modifyBankDepositTask(){
        TransferTaskDAO.Filter filter = new TransferTaskDAO.Filter();
        filter.id = expected.keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected.size())];
        TransferTask data = new TransferTask();
        data.setUo("Robb");
        data.setStatus("Pending");
        data.setState("Joker");
        Assert.assertNotEquals(data.getUo(), expected.get(filter.id).getUo());
        Assert.assertNotEquals(data.getStatus(), expected.get(filter.id).getStatus());
        Assert.assertNotEquals(data.getState(), expected.get(filter.id).getState());
        transferTaskDAO.update(filter, data);


        List<TransferTask> tasks = transferTaskDAO.list(filter);
        Assert.assertEquals(1, tasks.size());
        expected.get(filter.id).setUo(data.getUo());
        expected.get(filter.id).setStatus(data.getStatus());
        expected.get(filter.id).setState(data.getState());
        Assert.assertEquals(expected.get(filter.id), tasks.get(0));
    }

    @Test
    public void t3_deleteBankDepositTask(){
        int total = transferTaskDAO.list(null).size();

        TransferTaskDAO.Filter filter = new TransferTaskDAO.Filter();
        filter.id = expected.keySet().toArray(new String[]{})[RandomUtils.nextInt(0,expected.size())];
        List<TransferTask> tasks = transferTaskDAO.list(filter);
        Assert.assertEquals(1, tasks.size());

        Assert.assertEquals(1, transferTaskDAO.delete(filter));
        Assert.assertEquals(total - 1, transferTaskDAO.list(null).size());
    }
}

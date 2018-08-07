package com.sme.mts.service.impl;

import com.sme.mts.data.entity.FundAccount;
import com.sme.mts.data.entity.TransferTask;
import com.sme.mts.data.repository.FundAccountDAO;
import com.sme.mts.data.repository.TransferTaskDAO;
import com.sme.mts.service.TransactionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @Qualifier("transfer-task")
public class TransferTaskTransactionTestBiz extends TransactionTestBiz implements TransactionTestService {
    @Autowired
    private TransferTaskDAO transferTaskDAO;

    @Autowired
    private TransactionTestService dependency;

    @Override /*@Transactional*/
    public void clear() {
        TransferTaskDAO.Filter filter = new TransferTaskDAO.Filter();
        filter.type = TransferTask.Type.WITHDRWAL;
        transferTaskDAO.delete(filter);
    }

    @Override /*@Transactional*/
    public void doFoobar() {
        TransferTask task = new TransferTask();

        task.setId(UUID.randomUUID().toString());
        task.setDe(true);
        task.setBeneficiary("transaction-test");
        task.setStatus("New");
        task.setState("need-to-rollback");
        task.setType(TransferTask.Type.WITHDRWAL);
        task.setAmount(99.0);
        task.setFundAccount("dummy-account");
        transferTaskDAO.create(task);

//        dependency.clear();
//        dependency.foobar();

//        task.setId(UUID.randomUUID().toString());
//        task.setDe(true);
//        task.setType(Integer.MAX_VALUE);
//        task.setAmount(199.0);
//        transferTaskDAO.create(task);
    }

    @Override
    public void list() {
        TransferTaskDAO.Filter filter = new TransferTaskDAO.Filter();
        filter.type = TransferTask.Type.WITHDRWAL;
        System.out.println(transferTaskDAO.list(filter).size());
    }
}

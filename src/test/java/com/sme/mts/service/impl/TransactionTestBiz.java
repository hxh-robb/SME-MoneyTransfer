package com.sme.mts.service.impl;

import com.sme.mts.test.data.entity.FundAccount;
import com.sme.mts.test.data.repository.FundAccountDAO;
import com.sme.mts.service.TransactionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionTestBiz implements TransactionTestService {
    @Autowired
    private FundAccountDAO fundAccountDAO;

    @Override /*@Transactional*/
    public void clear() {
        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.type = FundAccount.Type.WITHDRWAL_ACCOUNT;
        fundAccountDAO.delete(filter);
    }

    @Override /*@Transactional*/
    public void foobar() {
        FundAccount account = new FundAccount();

        account.setId(UUID.randomUUID().toString());
        account.setDe(true);
        account.setType(FundAccount.Type.WITHDRWAL_ACCOUNT);
        account.setName("TransactionTest-Success-" + account.getId().split("-")[1]);
        fundAccountDAO.create(account);


        account.setId(UUID.randomUUID().toString());
        account.setDe(true);
        account.setType(Integer.MAX_VALUE);
        account.setName("TransactionTest-Failure-" + account.getId().split("-")[1]);
        fundAccountDAO.create(account);
    }
}

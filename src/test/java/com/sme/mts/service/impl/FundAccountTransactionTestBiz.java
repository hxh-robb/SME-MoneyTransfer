package com.sme.mts.service.impl;

import com.sme.mts.data.entity.FundAccount;
import com.sme.mts.data.repository.FundAccountDAO;
import com.sme.mts.service.TransactionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @Qualifier("fund-account")
public class FundAccountTransactionTestBiz  extends TransactionTestBiz implements TransactionTestService {
    @Autowired
    private FundAccountDAO fundAccountDAO;

    @Override /*@Transactional*/
    public void clear() {
        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.type = FundAccount.Type.WITHDRWAL_ACCOUNT;
        fundAccountDAO.delete(filter);
    }

    @Override /*@Transactional*/
    public void doFoobar() {
//        try {
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

//        } catch (Throwable th) {
//            LogFactory.getLog("JUnit").error("test transactions",th);
//        }
    }

    @Override
    public void list() {
        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.type = FundAccount.Type.WITHDRWAL_ACCOUNT;
        System.out.println(fundAccountDAO.list(filter).size());
    }
}

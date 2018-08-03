package com.sme.mts.data.repository;

import com.sme.mts.RepositoryTestcase;
import com.sme.mts.data.document.FundAccount;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FundAccountDocDAOTests extends RepositoryTestcase<FundAccount, DocDAO.Filter> {
    private static boolean init = false;
    private static final Map<String, FundAccount> expected = new ConcurrentHashMap<>();

    @Autowired
    private FundAccountDocDAO fundAccountDocDAO;


    @Override
    protected Map<String, FundAccount> expected() {
        return expected;
    }

    @Override
    protected boolean isInitialized() {
        return init;
    }

    @Override
    protected void setInitialized(boolean bool) {
        init = bool;
    }

    @Override
    protected FundAccountDocDAO dao() {
        return fundAccountDocDAO;
    }

    @Override
    protected FundAccount newData() {
        return new FundAccount();
    }

    @Override
    protected DocDAO.Filter newFilter() {
        return new DocDAO.Filter();
    }

    @Override
    protected void setupData(FundAccount data) {
        // dummy bank account
    }

    @Override
    protected void setupModify(FundAccount data) {
        // TODO
    }
}

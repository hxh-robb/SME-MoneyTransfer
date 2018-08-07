package com.sme.mts.test.data.repository.mongodb;

import com.sme.mts.test.data.document.FundAccount;
import com.sme.mts.test.data.repository.DocDAO;
import com.sme.mts.test.data.repository.FundAccountDocDAO;
import org.springframework.stereotype.Repository;

@Repository
public class MorphiaFundAccountDAO extends MorphiaDAO<FundAccount,DocDAO.Filter> implements FundAccountDocDAO {

    @Override
    protected Class<FundAccount> entityClass(DocDAO.Filter filter) {
        return FundAccount.class;
    }
}

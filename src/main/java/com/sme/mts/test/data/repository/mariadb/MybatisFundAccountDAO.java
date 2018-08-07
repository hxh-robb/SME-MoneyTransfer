package com.sme.mts.test.data.repository.mariadb;

import com.sme.mts.test.data.entity.FundAccount;
import com.sme.mts.test.data.repository.FundAccountDAO;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisFundAccountDAO extends MybatisDAO<FundAccount,FundAccountDAO.Filter> implements FundAccountDAO {
    public MybatisFundAccountDAO() {
        super("FundAccountMapper");
    }
}

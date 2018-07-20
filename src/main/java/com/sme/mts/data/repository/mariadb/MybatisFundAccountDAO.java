package com.sme.mts.data.repository.mariadb;

import com.sme.mts.data.entity.FundAccount;
import com.sme.mts.data.repository.FundAccountDAO;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisFundAccountDAO extends MybatisDAO<FundAccount,FundAccountDAO.Filter> implements FundAccountDAO {
    public MybatisFundAccountDAO() {
        super("FundAccountMapper");
    }
}

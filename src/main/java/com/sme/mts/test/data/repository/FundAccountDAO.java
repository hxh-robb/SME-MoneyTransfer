package com.sme.mts.test.data.repository;

import com.sme.mts.test.data.entity.FundAccount;

public interface FundAccountDAO extends PlatformRelatedDAO<FundAccount, FundAccountDAO.Filter> {
    class Filter extends PlatformRelatedDAO.Filter {
        public Integer type;
        public String name;
        public String title;
    }
}

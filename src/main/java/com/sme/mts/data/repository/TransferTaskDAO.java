package com.sme.mts.data.repository;

import com.sme.mts.data.entity.TransferTask;

public interface TransferTaskDAO extends PlatformRelatedDAO<TransferTask, TransferTaskDAO.Filter> {
    class Filter extends  PlatformRelatedDAO.Filter {
        public String type;
        public String beneficiary;
        public String status;
        public String state;
        public Boolean retry;
        public String fallback;
        public String ref;
        public String fundAccount;
    }
}

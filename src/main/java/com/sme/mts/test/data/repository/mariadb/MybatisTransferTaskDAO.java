package com.sme.mts.test.data.repository.mariadb;

import com.sme.mts.test.data.entity.TransferTask;
import com.sme.mts.test.data.repository.TransferTaskDAO;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisTransferTaskDAO extends MybatisDAO<TransferTask,TransferTaskDAO.Filter> implements TransferTaskDAO {
    protected MybatisTransferTaskDAO() {
        super("TransferTaskMapper");
    }
}

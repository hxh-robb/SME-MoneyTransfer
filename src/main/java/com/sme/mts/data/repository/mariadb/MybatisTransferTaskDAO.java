package com.sme.mts.data.repository.mariadb;

import com.sme.mts.data.entity.TransferTask;
import com.sme.mts.data.repository.TransferTaskDAO;
import org.springframework.stereotype.Repository;

@Repository
public class MybatisTransferTaskDAO extends MybatisDAO<TransferTask,TransferTaskDAO.Filter> implements TransferTaskDAO {
    protected MybatisTransferTaskDAO() {
        super("TransferTaskMapper");
    }
}

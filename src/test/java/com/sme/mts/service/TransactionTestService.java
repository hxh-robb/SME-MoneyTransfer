package com.sme.mts.service;

import com.sme.mts.test.service.TransactionalService;

public interface TransactionTestService extends TransactionalService {
    void clear();
    void foobar();
}

package com.sme.mts.service;

public interface TransactionTestService extends TransactionalService {
    void clear();
    void foobar(Runnable ... runs);
    void list();
}

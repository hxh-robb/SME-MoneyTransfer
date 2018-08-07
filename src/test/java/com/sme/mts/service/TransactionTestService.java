package com.sme.mts.service;

public interface TransactionTestService extends Service {
    void clear();
    void foobar(Runnable ... runs);
    void list();
}

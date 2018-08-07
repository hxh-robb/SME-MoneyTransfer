package com.sme.mts.test.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = 30) // 30 seconds timeout is pretty long for a service operation
public interface TransactionalService {
}

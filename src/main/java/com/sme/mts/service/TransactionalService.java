package com.sme.mts.service;

import org.springframework.transaction.annotation.Transactional;

// 30 seconds timeout is pretty long for a service operation
@Transactional(timeout = 30)
public interface TransactionalService {
}

package com.sme.mts.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional(timeout = 30) // 30 seconds timeout is pretty long for a service operation
public interface TransactionalService {
}

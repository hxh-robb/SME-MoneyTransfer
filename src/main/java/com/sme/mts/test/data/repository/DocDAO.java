package com.sme.mts.test.data.repository;

import com.sme.mts.test.data.Data;
import com.sme.mts.test.data.DataAccessObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface DocDAO<D extends Data,F extends DocDAO.Filter> extends DataAccessObject<D, F> {
    class Filter extends DataAccessObject.Filter {
        public final Map<String, Object> matches = new ConcurrentHashMap<>();
    }
}

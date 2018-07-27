package com.sme.mts.data.repository;

import com.sme.mts.data.Data;
import com.sme.mts.data.DataAccessObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface DocDAO<T extends Data> extends DataAccessObject<T, DocDAO.Filter> {
    class Filter extends DataAccessObject.Filter {
        public final Map<String, Object> matches = new ConcurrentHashMap<>();
    }
}

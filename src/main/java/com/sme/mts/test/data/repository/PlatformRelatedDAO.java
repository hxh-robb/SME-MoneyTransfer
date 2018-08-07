package com.sme.mts.test.data.repository;

import com.sme.mts.test.data.Data;
import com.sme.mts.test.data.DataAccessObject;

public interface PlatformRelatedDAO<D extends Data, F extends DataAccessObject.Filter> extends DataAccessObject<D, F> {
    class Filter extends DataAccessObject.Filter {
        public String platform;
    }
}

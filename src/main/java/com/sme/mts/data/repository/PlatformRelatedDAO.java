package com.sme.mts.data.repository;

import com.sme.mts.data.Data;
import com.sme.mts.data.DataAccessObject;

public interface PlatformRelatedDAO<D extends Data, F extends DataAccessObject.Filter> extends DataAccessObject<D, F> {
    class Filter extends DataAccessObject.Filter {
        public String platform;
    }
}

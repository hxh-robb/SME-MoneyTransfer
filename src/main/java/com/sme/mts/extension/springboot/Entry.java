package com.sme.mts.extension.springboot;

import java.util.Map;

class Entry<T> {
    T source;
    String path;
    Map<String, String> initParams;

    Entry(T source, String path, Map<String, String> initParams) {
        this.source = source;
        this.path = path == null ? "/*" : path;
        this.initParams = initParams;
    }
}

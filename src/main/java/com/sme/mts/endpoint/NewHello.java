package com.sme.mts.endpoint;

public class NewHello extends Hello {
    @Override
    public String test() {
        return "New version:" + super.test();
    }
}

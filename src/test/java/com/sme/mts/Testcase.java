package com.sme.mts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest @ActiveProfiles("test")
public class Testcase {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected static final String operator = "junit";
}

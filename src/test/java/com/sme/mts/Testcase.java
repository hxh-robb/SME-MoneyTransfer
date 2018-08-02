package com.sme.mts;

import com.sme.mts.data.entity.PlatformRelatedEntity;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class Testcase {
    protected static final String operator = "junit";
    protected static final String platform = "dummy-platform";
    protected static final String [] bankTitles = new String[]{
        "中国银行", "中国农业银行", "中国工商银行", "中国建设银行"
    };

    protected void init(PlatformRelatedEntity data) {
        if( null == data ) return;

        data.setId(UUID.randomUUID().toString());
        data.setDe(false);
        data.setCo(operator);
        data.setUo(operator);
        data.setPlatform(platform);
    }
}

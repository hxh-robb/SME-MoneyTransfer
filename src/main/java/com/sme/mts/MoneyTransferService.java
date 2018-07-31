package com.sme.mts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyTransferService /*implements CommandLineRunner*/ {
//    private static final Log logger = LogFactory.getLog(MoneyTransferService.class);
	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferService.class, args);
	}

//	@Value("${spring.datasource.url}")
//	String uri;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        logger.info(uri);
//    }
}

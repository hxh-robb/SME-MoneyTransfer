package com.sme.mts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneyTransferService
//    implements org.springframework.boot.CommandLineRunner
{
	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferService.class, args);
	}

//    private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(MoneyTransferService.class);
//	private static final String KEY = "robb.test";
//    @org.springframework.beans.factory.annotation.Value("${" + KEY + "}")
//	private String value;
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        logger.info(KEY + ":" + value);
//    }
}

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
//    private static final String [] ENV_VARS = {
//        "JDBC_URL","JDBC_USER","JDBC_PASS",
//    };
//    private static final String [] PROP = {
//        "spring.datasource.url",
//        "spring.datasource.username",
//        "spring.datasource.password",
//    };
//
//    @org.springframework.beans.factory.annotation.Autowired
//    private org.springframework.core.env.Environment env;
//
//    @Override
//    public void run(String... args) throws Exception {
//        for (String key: ENV_VARS) {
//            logger.info("[ENV]" + key + ":" + System.getenv(key));
//        }
//
//        for (String key: PROP) {
//            logger.info("[PROP]" + key + ":" + env.getProperty(key));
//        }
//
//        try {
//            java.net.InetAddress addr = java.net.InetAddress.getByName("mariadb");
//            logger.info("IP(\"mariadb\"):" + addr.getHostAddress());
//        } catch (Throwable th) {
//            logger.info("IP(\"mariadb\") not found");
//        }
//    }
}

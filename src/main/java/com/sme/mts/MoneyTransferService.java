package com.sme.mts;

import com.sme.mts.addon.PythonAddon;
import com.sme.mts.data.Data;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MoneyTransferService {
	public static void main(String[] args) {
		SpringApplication.run(MoneyTransferService.class, args);
	}
}

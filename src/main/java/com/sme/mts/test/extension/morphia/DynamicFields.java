package com.sme.mts.test.extension.morphia;

import org.mongodb.morphia.annotations.Property;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DynamicFields {
}

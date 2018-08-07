package com.sme.mts.extension.morphia;

import org.mongodb.morphia.annotations.Property;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DynamicFields {
}

package com.udacity.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by suyashg on 06/07/18
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiInfo {

}

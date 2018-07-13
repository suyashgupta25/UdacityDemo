package com.udacity.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by suyashg on 05/07/18.
 */

public interface SchedulerProvider {

    Scheduler io();

    Scheduler ui();
}

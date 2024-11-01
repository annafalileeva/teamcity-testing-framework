package com.example.teamcity.api.requests;

import java.util.concurrent.TimeUnit;

public interface CrudWithTimeoutInterface {
    Object read(String locator, long timeout, TimeUnit timeoutUnit, long pollInterval, TimeUnit pollIntervalUnit);
}

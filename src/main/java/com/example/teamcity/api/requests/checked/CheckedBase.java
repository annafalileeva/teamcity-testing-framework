package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.*;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SuppressWarnings("unchecked")
public final class CheckedBase<T extends BaseModel> extends Request implements CrudInterface, CrudWithTimeoutInterface, SearchInterface, ServerSettingsInterface {
    private final UncheckedBase uncheckedBase;

    public CheckedBase(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
        this.uncheckedBase = new UncheckedBase(spec, endpoint);
    }

    @Override
    public T create(BaseModel model) {
        var createdModel = (T) uncheckedBase
                .create(model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
        TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
        return createdModel;
    }

    @Override
    public T read(String locator) {
        return (T) uncheckedBase
                .read(locator)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T update(String locator, BaseModel model) {
        return (T) uncheckedBase
                .update(locator, model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public Object delete(String locator) {
        return uncheckedBase
                .delete(locator)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }

    @Override
    public T search(String locator) {
        return (T) uncheckedBase
                .search(locator)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getSearchResultsModelClass());
    }

    @Override
    public T readSettings() {
        return (T) uncheckedBase
                .readSettings()
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T updateSettings(BaseModel model) {
        return (T) uncheckedBase
                .updateSettings(model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T read(String locator, long timeout, TimeUnit timeoutUnit, long pollInterval, TimeUnit pollIntervalUnit) {
        return await()
                .atMost(timeout, timeoutUnit)
                .pollInterval(pollInterval, pollIntervalUnit)
                .until(() -> {
                    try {
                        return (T) uncheckedBase
                                .read(locator)
                                .then().assertThat().statusCode(HttpStatus.SC_OK)
                                .extract().as(endpoint.getModelClass());
                    } catch (AssertionError e) {
                        return null;
                    }
                }, Objects::nonNull);
    }
}

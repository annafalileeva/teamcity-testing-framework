package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.enums.Locator;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.SearchInterface;
import com.example.teamcity.api.requests.ServerSettingsInterface;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

@SuppressWarnings("unchecked")
public final class CheckedBase<T extends BaseModel> extends Request implements CrudInterface, SearchInterface, ServerSettingsInterface {
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
    public T search(Locator locator, String value) {
        return (T) uncheckedBase
                .search(locator, value)
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
}

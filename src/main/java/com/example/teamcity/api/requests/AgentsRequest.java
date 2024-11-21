package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.AgentAuthorisedInfo;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class AgentsRequest {
    private static final String AGENTS_URL = Endpoint.AGENTS.getUrl();
    private RequestSpecification spec;

    public AgentsRequest(RequestSpecification spec) {
        this.spec = spec;
    }

    public AgentAuthorisedInfo authorisedInfo(String locator, AgentAuthorisedInfo authorisedInfo) {
        return RestAssured.given()
                .spec(spec)
                .body(authorisedInfo)
                .put(AGENTS_URL + "/" + locator + "/authorizedInfo")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(AgentAuthorisedInfo.class);
    }
}

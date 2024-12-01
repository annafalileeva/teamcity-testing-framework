package com.example.teamcity.api;

import com.example.teamcity.api.models.Agent;
import com.example.teamcity.api.models.AgentAuthorisedInfo;
import com.example.teamcity.api.requests.AgentsRequest;
import com.example.teamcity.api.spec.Specifications;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.AGENTS;
import static io.qameta.allure.Allure.step;
import static java.util.concurrent.TimeUnit.SECONDS;

public class SetupAgentTest extends BaseApiTest {
    @Test(groups = {"Setup"})
    public void setupAgentTest() {
        step("Get id of unauthorized agent");
        var unauthorisedAgent = superUserCheckRequests.<Agent>getRequest(AGENTS).read("authorized:false",
                30, SECONDS, 1, SECONDS);
        softy.assertNotNull(unauthorisedAgent, "No unauthorised agent");

        step("Authorized agent");
        var agentsRequest = new AgentsRequest(Specifications.superUserSpec());
        var authorisedAgent = agentsRequest.authorisedInfo("id:" + unauthorisedAgent.getId(),
                AgentAuthorisedInfo.builder().status(true).build());

        step("Check agent status");
        softy.assertTrue(authorisedAgent.isStatus(), "Agent didn't authorised");
    }
}

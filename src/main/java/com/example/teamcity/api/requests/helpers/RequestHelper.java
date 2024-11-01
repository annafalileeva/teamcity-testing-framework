package com.example.teamcity.api.requests.helpers;

import com.example.teamcity.api.models.Build;
import com.example.teamcity.api.models.BuildTypeList;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;

import static com.example.teamcity.api.enums.Endpoint.BUILDS;
import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;


public class RequestHelper {
    private static CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserSpec());

    public static int getBuildTypesCount(String projectId) {
        return superUserCheckRequests.<BuildTypeList>getRequest(BUILD_TYPES)
                .search("project:id:" + projectId)
                .getCount();
    }

    public static void waitForBuildStatus(Long buildId, String buildStatus) {
        await()
                .atMost(20, SECONDS)
                .pollInterval(2, SECONDS)
                .until(() -> {
                    var status = superUserCheckRequests.<Build>getRequest(BUILDS).read(
                            "id:" + buildId).getStatus();
                    return status.equals(buildStatus);
                });
    }
}

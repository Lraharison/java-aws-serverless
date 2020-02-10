package com.cyranno.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static java.lang.Integer.valueOf;
import static java.lang.String.format;

@Slf4j
public class GetItemHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
    private final Map<Integer, String> items = ImmutableMap.of(
            1, "carrot",
            2, "broccoli",
            3, "cucumber",
            4, "green pepper",
            5, "potato");

    @Override
    public ApiGatewayResponse handleRequest(final Map<String, Object> input, final Context context) {
        final Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
        final String id = pathParameters.get("id");
        log.info("request item with id ; {}", id);
        if (StringUtils.isNullOrEmpty(id)) {
            log.error("Bad request error");
            return ApiGatewayResponse.builder()
                    .withStatusCode(400)
                    .withRawBody("{\"message\":\"Bad request error\"}")
                    .build();

        }
        final String item = this.items.get(valueOf(id));
        if (item == null) {
            log.error("Item with id {} not found", id);
            return ApiGatewayResponse.builder()
                    .withStatusCode(404)
                    .withRawBody(format("{\"message\": \"Item with id %s not found\"}", id))
                    .build();
        }

        return ApiGatewayResponse.builder()
                .withObjectBody(new Response(valueOf(id), item))
                .build();
    }
}

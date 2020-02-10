package com.cyranno.serverless;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertEquals;

public class GetItemHandlerTest {
    private final GetItemHandler getItemHandler = new GetItemHandler();

    @Test
    public void shouldReturnApiGatewayResponse() {
        final Map<String, Object> input = new HashMap<>();
        input.put("pathParameters", ImmutableMap.of("id", "1"));

        final ApiGatewayResponse apiGatewayResponse = this.getItemHandler.handleRequest(input, null);

        assertEquals("{\"id\":1,\"label\":\"carrot\"}", apiGatewayResponse.getBody());
        assertEquals(200, apiGatewayResponse.getStatusCode());
    }


    @Test
    public void shouldReturnBadRequestApiGatewayResponseIfIdIsNotProvided() {
        final Map<String, Object> input = new HashMap<>();
        input.put("pathParameters", emptyMap());

        final ApiGatewayResponse apiGatewayResponse = this.getItemHandler.handleRequest(input, null);

        assertEquals("{\"message\":\"Bad request error\"}", apiGatewayResponse.getBody());
        assertEquals(400, apiGatewayResponse.getStatusCode());
    }

    @Test
    public void shouldReturn404ApiGatewayResponseIfItemNotFound() {
        final Map<String, Object> input = new HashMap<>();
        input.put("pathParameters", ImmutableMap.of("id", "15"));

        final ApiGatewayResponse apiGatewayResponse = this.getItemHandler.handleRequest(input, null);

        assertEquals("{\"message\": \"Item with id 15 not found\"}", apiGatewayResponse.getBody());
        assertEquals(404, apiGatewayResponse.getStatusCode());
    }
}

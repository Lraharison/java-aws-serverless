package com.cyranno.serverless;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
public class ApiGatewayResponse {
    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    public ApiGatewayResponse(final int statusCode, final String body, final Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Slf4j
    public static class Builder {
        private final ObjectMapper objectMapper = new ObjectMapper();
        private int statusCode = 200;
        private Map<String, String> headers = emptyMap();
        private Object objectBody;
        private String rawBody;

        public Builder withStatusCode(final int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withObjectBody(final Object objectBody) {
            this.objectBody = objectBody;
            return this;
        }

        public Builder withHeaders(final Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withRawBody(final String rawBody) {
            this.rawBody = rawBody;
            return this;
        }

        public ApiGatewayResponse build() {
            String body = null;
            int statusCode = this.statusCode;
            final Map<String, String> headers = this.headers;
            if (this.rawBody != null) {
                body = this.rawBody;
            } else if (this.objectBody != null) {
                try {
                    body = this.objectMapper.writeValueAsString(this.objectBody);
                } catch (final JsonProcessingException e) {
                    statusCode = 500;
                    body = String.format("{\"message\":\"Failed to serialize object :%s\"}", e.getMessage());
                }
            }
            log.info("body response = {}", body);
            return new ApiGatewayResponse(statusCode, body, headers);
        }
    }
}

package edu.project3.record;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record Request(
    Method requestMethod,
    String resource,
    Version version
) {
    public enum Method {
        GET,
        HEAD,
        POST,
        PUT,
        DELETE,
        CONNECT,
        OPTIONS,
        TRACE,
        PATCH
    }

    public enum Version {
        HTTP10("HTTP/1.0"),
        HTTP11("HTTP/1.1"),
        HTTP20("HTTP/2.0"),
        HTTP30("HTTP/3.0");

        private final String name;
        private static final Map<String, Version> ENUM_MAP;

        static {
            Map<String, Version> requestVersionMap = new HashMap<>();

            for (Version version : Version.values()) {
                requestVersionMap.put(version.name, version);
            }

            ENUM_MAP = Collections.unmodifiableMap(requestVersionMap);
        }

        public static Version fromString(String value) {
            if (!ENUM_MAP.containsKey(value)) {
                throw new IllegalArgumentException("Unknown request version");
            }

            return ENUM_MAP.get(value);
        }

        Version(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}

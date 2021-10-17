package de.sebsprenger.structuredlogging.util;

import net.logstash.logback.marker.ObjectAppendingMarker;

public class JsonAttributeMarker extends ObjectAppendingMarker {

    public JsonAttributeMarker(String fieldName, Object object) {
        super(fieldName, object);
    }

    public JsonAttributeMarker(String fieldName, Object object, String messageFormatPattern) {
        super(fieldName, object, messageFormatPattern);
    }

    public JsonAttributeMarker and(String fieldName, Object object) {
        JsonAttributeMarker result = new JsonAttributeMarker(fieldName, object);
        super.add(result);
        return result;
    }

    public static JsonAttributeMarker put(String fieldName, Object object) {
        return new JsonAttributeMarker(fieldName, object);
    }
}

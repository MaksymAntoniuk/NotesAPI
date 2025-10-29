package io.maksym.web.util;

import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SchemaResponseValidator {

    private static final Logger logger = LoggerFactory.getLogger(SchemaResponseValidator.class);

    public static boolean assertResponseSchema(String pathToSchema, Response response) {
        boolean result = false;
        try {
            if (response.getBody().asString().equals("[]")) {
                logger.info("Response for - "
                        + pathToSchema
                        + "has an empty array. Skipping schema validation.");
            } else {
                response
                        .then()
                        .assertThat()
                        .body(JsonSchemaValidator
                                .matchesJsonSchemaInClasspath(pathToSchema));
                logger.info("Schema validation - " + pathToSchema + " - PASSED");
                result = true;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (!result) {
                logger.info("Schema validation - " + pathToSchema + " - FAILED");
            }
        }
    }
}

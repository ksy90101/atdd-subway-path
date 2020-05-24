package wooteco.subway.admin.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/truncate.sql")
public class PageAcceptanceTest {
    private final LineAcceptanceTest lineAcceptanceTest = new LineAcceptanceTest();

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @Test
    void linePage() {
        lineAcceptanceTest.createLine("신분당선");

        given().
            accept(MediaType.TEXT_HTML_VALUE).
        when().
            get("/admin/lines").
        then().
            log().all().
            statusCode(HttpStatus.OK.value());
    }

}

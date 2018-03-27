package dockerx.spring.boot.rxjava.asyncresult;


import io.reactivex.Single;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 21:57.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes =SingleDeferredResultTest.Application.class )
@DirtiesContext
public class SingleDeferredResultTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Configuration
    @EnableAutoConfiguration
    @RestController
    protected static class Application {

        @RequestMapping(method = RequestMethod.GET, value = "/single")
        public Single<String> single() {
            return Single.just("single value");
        }

        @RequestMapping(method = RequestMethod.GET, value = "/singleWithResponse")
        public Single<ResponseEntity<String>> singleWithResponse() {
            return Single.just(new ResponseEntity<>("single value", HttpStatus.NOT_FOUND));
        }

        @RequestMapping(method = RequestMethod.GET, value = "/throw")
        public Single<Object> error() {
            return Single.error(new RuntimeException("Unexpected"));
        }
    }

    @Test
    public void shouldRetrieveSingleValue() {

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(path("/single"), String.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("single value", response.getBody());
    }

    @Test
    public void shouldRetrieveSingleValueWithStatusCode() {

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(path("/singleWithResponse"), String.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("single value", response.getBody());
    }

    @Test
    public void shouldRetrieveErrorResponse() {

        // when
        ResponseEntity<Object> response = restTemplate.getForEntity(path("/throw"), Object.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private String path(String context) {
        return String.format("http://localhost:%d%s", port, context);
    }
}

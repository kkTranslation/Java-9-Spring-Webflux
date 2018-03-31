package dockerx.spring.boot.rxjava.asyncresult;

import dockerx.spring.boot.rxjava.dto.EventDto;
import io.reactivex.Observable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 23:04.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes =ObservableDeferredResultTest.Application.class )
@DirtiesContext
public class ObservableDeferredResultTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Configuration
    @EnableAutoConfiguration
    @RestController
    protected static class Application {

        @RequestMapping(method = RequestMethod.GET, value = "/empty")
        public ObservableDeferredResult<String> empty() {
            return new ObservableDeferredResult<>(Observable.<String>empty());
        }

        @RequestMapping(method = RequestMethod.GET, value = "/single")
        public ObservableDeferredResult<String> single() {
            return new ObservableDeferredResult<>(Observable.just("single value"));
        }

        @RequestMapping(method = RequestMethod.GET, value = "/multiple")
        public ObservableDeferredResult<String> multiple() {
            return new ObservableDeferredResult<>(Observable.just("multiple", "values"));
        }

        @RequestMapping(method = RequestMethod.GET, value = "/event", produces = APPLICATION_JSON_UTF8_VALUE)
        public ObservableDeferredResult<EventDto> event() {
            return new ObservableDeferredResult<>(
                    Observable.just(
                            new EventDto("Spring.io", new Date()),
                            new EventDto("JavaOne", new Date())
                    )
            );
        }

        @RequestMapping(method = RequestMethod.GET, value = "/throw")
        public ObservableDeferredResult<Object> error() {
            return new ObservableDeferredResult<>(Observable.error(new RuntimeException("Unexpected")));
        }

        @RequestMapping(method = RequestMethod.GET, value = "/timeout")
        public ObservableDeferredResult<String> timeout() {
            return new ObservableDeferredResult<>(Observable.timer(1, TimeUnit.MINUTES).map(aLong -> "single value"));
        }
    }

    @Test
    public void shouldRetrieveEmptyResponse() {

        // when
        ResponseEntity<List> response = restTemplate.getForEntity(path("/empty"), List.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    public void shouldRetrieveSingleValue() {

        // when
        ResponseEntity<List> response = restTemplate.getForEntity(path("/single"), List.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList("single value"), response.getBody());
    }

    @Test
    public void shouldRetrieveMultipleValues() {

        // when
        ResponseEntity<List> response = restTemplate.getForEntity(path("/multiple"), List.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Arrays.asList("multiple", "values"), response.getBody());
    }

    @Test
    public void shouldRetrieveJsonSerializedListValues() {

        // when
        ResponseEntity<List<EventDto>> response = restTemplate.exchange(path("/event"), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<EventDto>>() {});
        System.out.println(response.getBody());
        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("JavaOne", response.getBody().get(1).getName());
    }

    @Test
    public void shouldRetrieveErrorResponse() {

        // when
        ResponseEntity<Object> response = restTemplate.getForEntity(path("/throw"), Object.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void shouldTimeoutOnConnection() {

        // when
        ResponseEntity<Object> response = restTemplate.getForEntity(path("/timeout"), Object.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    private String path(String context) {
        return String.format("http://localhost:%d%s", port, context);
    }
}

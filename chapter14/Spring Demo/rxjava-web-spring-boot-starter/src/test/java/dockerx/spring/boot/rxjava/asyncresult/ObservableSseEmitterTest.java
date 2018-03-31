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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 23:20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes =ObservableSseEmitterTest.Application.class )
@DirtiesContext
public class ObservableSseEmitterTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Configuration
    @EnableAutoConfiguration
    @RestController
    protected static class Application {

        @RequestMapping(method = RequestMethod.GET, value = "/sse")
        public ObservableSseEmitter<String> single() {
            return new ObservableSseEmitter<>(Observable.just("single value"));
        }

        @RequestMapping(method = RequestMethod.GET, value = "/messages" ,produces = MediaType.TEXT_EVENT_STREAM_VALUE)
        public ObservableSseEmitter<String> messages() {
            return new ObservableSseEmitter<>(Observable.just("message 1", "message 2", "message 3"));
        }

        @RequestMapping(method = RequestMethod.GET, value = "/events")
        public ObservableSseEmitter<EventDto> event() {
            return new ObservableSseEmitter<>(APPLICATION_JSON_UTF8, Observable.just(
                    new EventDto("Spring.io", getDate(2016, 5, 11)),
                    new EventDto("JavaOne", getDate(2016, 9, 22))
            ));
        }
    }

    @Test
    public void shouldRetrieveSse() {

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(path("/sse"), String.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data:single value\n\n", response.getBody());
    }

    @Test
    public void shouldRetrieveSseWithMultipleMessages() {

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(path("/messages"), String.class);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("data:message 1\n\ndata:message 2\n\ndata:message 3\n\n", response.getBody());
    }

    @Test
    public void shouldRetrieveJsonOverSseWithMultipleMessages() {

        // when
        ResponseEntity<String> response = restTemplate.getForEntity(path("/events"), String.class);
        System.out.println(response.getBody());
        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void shouldRetrieveJsonOverSseWithMultipleMessages2() throws URISyntaxException {

        // when

        RequestEntity<Void> requestEntity = RequestEntity.get(new URI(path("/events")))
                                                 .accept(MediaType.TEXT_EVENT_STREAM).build();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        System.out.println(responseEntity.getBody());
        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private String path(String context) {
        return String.format("http://localhost:%d%s", port, context);
    }

    private static Date getDate(int year, int month, int day) {
        return new GregorianCalendar(year, month, day).getTime();
    }

}

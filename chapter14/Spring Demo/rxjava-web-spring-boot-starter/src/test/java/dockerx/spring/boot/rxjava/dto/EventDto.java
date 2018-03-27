package dockerx.spring.boot.rxjava.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author Author  知秋
 * @email fei6751803@163.com
 * @time Created by Auser on 2018/3/27 23:08.
 */
public class EventDto {
    private final String name;

    private final Date date;

    @JsonCreator
    public EventDto(@JsonProperty("name") String name, @JsonProperty("date") Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}

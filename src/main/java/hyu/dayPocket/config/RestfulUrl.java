package hyu.dayPocket.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@Getter
@AllArgsConstructor
public class RestfulUrl {
    private HttpMethod method;
    private String path;
}

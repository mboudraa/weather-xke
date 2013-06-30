package fr.xebia.xke.android.weather.request;

import com.octo.android.robospice.Jackson2SpringAndroidSpiceService;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Created by mounirboudraa on 04/06/13.
 */
public class WeatherSpiceService extends Jackson2SpringAndroidSpiceService {

    private static final int TIMEOUT = 10000;

    @Override
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate() {
            @Override
            protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
                ClientHttpRequest request = super.createRequest(url, method);
                HttpHeaders headers = request.getHeaders();
                headers.setAcceptEncoding(ContentCodingType.GZIP);
                return request;
            }
        };

        // bug on http connection for Android < 2.2
        // http://android-developers.blogspot.fr/2011/09/androids-http-clients.html
        // but still a problem for upload with Spring-android on android 4.1
        System.setProperty("http.keepAlive", "false");

        // set timeout for requests
        ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
        if (factory instanceof HttpComponentsClientHttpRequestFactory) {
            HttpComponentsClientHttpRequestFactory advancedFactory = (HttpComponentsClientHttpRequestFactory) factory;
            advancedFactory.setConnectTimeout(TIMEOUT);
            advancedFactory.setReadTimeout(TIMEOUT);
        } else if (factory instanceof SimpleClientHttpRequestFactory) {
            SimpleClientHttpRequestFactory advancedFactory = (SimpleClientHttpRequestFactory) factory;
            advancedFactory.setConnectTimeout(TIMEOUT);
            advancedFactory.setReadTimeout(TIMEOUT);
        }


        // web services support json responses
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        final List<HttpMessageConverter<?>> listHttpMessageConverters = restTemplate
                .getMessageConverters();

        listHttpMessageConverters.add(jsonConverter);
        restTemplate.setMessageConverters(listHttpMessageConverters);
        return restTemplate;
    }
}

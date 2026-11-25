package com.philosophizer.smoothie.connectivity.OpenFoodFacts;

import com.philosophizer.smoothie.model.OpenFoodFacts.OpenFoodFactsResponse;
import com.philosophizer.smoothie.utils.SearchTermNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Optional;

@Component
public class OpenFoodFactsClient {

    @Value("${openFoodFacts.baseUrl}")
    private String getItemBaseUrl;

    @Value("${openFoodFacts.suffix}")
    private String getItemSuffix;

    @Value("${openFoodFacts.user-agent}")
    private String userAgent;

    private final RestClient restClient = RestClient.builder()
            .requestInterceptor((request, body, execution) ->
            {System.out.println(request.getMethod() + " " + request.getURI());
            return execution.execute(request, body);}
            ).requestFactory(timeouts()).build();

//    @Autowired
//    public SearchTermNormalizer searchTermNormalizer;

    private static final Duration EXPIRY_SKEW = Duration.ofSeconds(60);
    private static final Logger log = LoggerFactory.getLogger(OpenFoodFactsClient.class);

    public Optional<OpenFoodFactsResponse> getItemByUpc(String upc){
        try {
            OpenFoodFactsResponse body = restClient.get()
                    .uri(getItemBaseUrl + upc + getItemSuffix)
                    .header(HttpHeaders.USER_AGENT, userAgent)
                    .retrieve()
                    .body(OpenFoodFactsResponse.class);

            if (body == null) {
                log.warn("open food facts response was empty");
                throw new IllegalStateException("OpenFoodFacts response was empty");
            }
            log.info("OpenFoodFacts response retrieved {}", body.toString());

            return Optional.ofNullable(body).filter(b -> ((b.result().id().equals("product_found") && b.isUsable(.7))));
        } catch(HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    private static JdkClientHttpRequestFactory timeouts() {
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build());
        factory.setReadTimeout(Duration.ofSeconds(12));
        return factory;

    }
}

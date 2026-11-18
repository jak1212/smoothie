package com.philosophizer.demeter.OpenFoodFacts;

import com.philosophizer.demeter.model.OpenFoodFacts.OpenFoodFactsItem;
import com.philosophizer.demeter.utils.SearchTermNormalizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    public SearchTermNormalizer searchTermNormalizer;

    private static final Duration EXPIRY_SKEW = Duration.ofSeconds(60);

    public Optional<OpenFoodFactsItem> getItemByUpc(String upc){
        try {
            OpenFoodFactsItem body = restClient.get()
                    .uri(getItemBaseUrl + upc + getItemSuffix)
                    .retrieve()
                    .body(OpenFoodFactsItem.class);

            if (body == null) {
                throw new IllegalStateException("OpenFoodFacts response was empty");
            }
            return Optional.ofNullable(body); //.filter(b -> b.result().id.equals("product_found"))
        } catch(HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    private static JdkClientHttpRequestFactory timeouts() {
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(
                HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build());
        factory.setReadTimeout(Duration.ofSeconds(10));
        return factory;

    }
}

package com.demo.core.zuul;


import com.demo.utils.BeanUtil;
import com.demo.utils.StringBuilderUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ZuulClient {
    private static final String SPILTOR = "/";
    private String gatewayUrl;
    private RestTemplate restTemplate;

    public ZuulClient(String gatewayUrl, RestTemplate restTemplate) {
        this.gatewayUrl = gatewayUrl;
        this.restTemplate = restTemplate;
    }

    private String getRealUrl(String url) {
        return StringBuilderUtil.append(new String[]{this.gatewayUrl, "/", url});
    }

    private String buildGetUrl(String url, Map<String, ?> urlVariables) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(this.getRealUrl(url));
        Iterator var4 = urlVariables.keySet().iterator();

        while(var4.hasNext()) {
            String property = (String)var4.next();
            uriComponentsBuilder.queryParam(property, new Object[]{"{" + property + "}"});
        }

        String realUrl = uriComponentsBuilder.build().toString();
        return realUrl;
    }

    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        Map<String, Object> urlVariablesMap = new HashMap(10);
        Object[] var5 = urlVariables;
        int var6 = urlVariables.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Object obj = var5[var7];
            Map<String, Object> variablesMap = BeanUtil.bean2Map(obj);
            urlVariablesMap.putAll(variablesMap);
        }

        return (T)this.getForObject(url, (Class<T>) responseType, (Map)urlVariablesMap);
    }

    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {
        String realUrl = this.buildGetUrl(url, urlVariables);
        return this.restTemplate.getForObject(realUrl, responseType, urlVariables);
    }

    public <T> T getForObject(String url, ParameterizedTypeReference typeReference, Map<String, ?> urlVariables) throws RestClientException {
        String realUrl = this.buildGetUrl(url, urlVariables);
        return (T)this.restTemplate.exchange(realUrl, HttpMethod.GET, (HttpEntity)null, typeReference, urlVariables).getBody();
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return this.restTemplate.postForObject(this.getRealUrl(url), request, responseType, uriVariables);
    }

    public <T> T postForObject(String url, Object request, ParameterizedTypeReference typeReference, Object... uriVariables) throws RestClientException {
        return (T)this.restTemplate.exchange(this.getRealUrl(url), HttpMethod.POST, new HttpEntity(request), typeReference, uriVariables).getBody();
    }

    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return this.restTemplate.postForObject(this.getRealUrl(url), request, responseType, uriVariables);
    }

    public <T> T postForObject(String url, Object request, ParameterizedTypeReference typeReference, Map<String, ?> uriVariables) throws RestClientException {
        return (T)this.restTemplate.exchange(this.getRealUrl(url), HttpMethod.POST, new HttpEntity(request), typeReference, uriVariables).getBody();
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }
}

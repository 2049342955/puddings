package com.demo.core.client;


import com.github.pagehelper.PageInfo;
import com.demo.core.exception.MultiLangException;
import com.demo.core.web.ResponseEntity;
import com.demo.core.web.ResponseState;
import com.demo.core.zuul.ZuulClient;
import com.demo.utils.StringBuilderUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClientException;

public abstract class AbstractResourceClient<T extends ResponseEntity> {
    @Autowired
    protected ZuulClient zuulClient;

    public AbstractResourceClient() {
    }

    protected abstract String getServiceName();

    private String processUrl(String url) {
        return StringBuilderUtil.append(new String[]{this.getServiceName(), url});
    }

    protected T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException {
        url = this.processUrl(url);
        T result = (T) this.zuulClient.getForObject(url, responseType, urlVariables);
        this.check(result);
        return result;
    }

    protected T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException {
        url = this.processUrl(url);
        T result = (T) this.zuulClient.getForObject(url, responseType, urlVariables);
        this.check(result);
        return result;
    }

    protected T getForObject(String url, ParameterizedTypeReference typeReference, Map<String, ?> urlVariables) throws RestClientException {
        url = this.processUrl(url);
        T result = (T) this.zuulClient.getForObject(url, typeReference, urlVariables);
        this.check(result);
        return result;
    }

    protected T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        url = this.processUrl(url);
        T result = (T) this.zuulClient.postForObject(url, request, responseType, uriVariables);
        this.check(result);
        return result;
    }

    protected T postForObject(String url, Object request, ParameterizedTypeReference typeReference, Object... uriVariables) throws RestClientException {
        url = this.processUrl(url);
        T result = (T) this.zuulClient.postForObject(url, request, typeReference, uriVariables);
        this.check(result);
        return result;
    }

    protected T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        url = this.processUrl(url);
        T result = (T) this.zuulClient.postForObject(url, request, responseType, uriVariables);
        this.check(result);
        return result;
    }

    protected T postForObject(String url, Object request, ParameterizedTypeReference typeReference, Map<String, ?> uriVariables) throws RestClientException {
        url = this.processUrl(url);
        T result = (T) this.zuulClient.postForObject(url, request, typeReference, uriVariables);
        this.check(result);
        return result;
    }

    private void check(T result) {
        if (!ResponseState.SUCCESS.getValue().equals(result.getCode())) {
            throw new MultiLangException(result.getCode(), result.getMsg());
        }
    }

    public ResponseEntity emptyResponse() {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(ResponseState.ERROR.getValue());
        responseEntity.setMsg("failed to call service : " + this.getServiceName());
        return responseEntity;
    }

    protected static <T> ResponseEntity<PageInfo<T>> successPageInfo(Map<String, Object> data, Class<T> tClass) {
        return success(BeanUtil.pageMap2pageBean(data, tClass));
    }

    protected static <T> ResponseEntity<List<T>> successList(List<Map<String, Object>> data, Class<T> tClass) {
        return success(BeanUtil.listMap2listBean(data, tClass));
    }

    protected static <T> ResponseEntity<T> successBean(Map<String, Object> data, Class<T> tClass) {
        return success(BeanUtil.map2Bean(data, tClass));
    }

    protected static <T> ResponseEntity<T> success(T data) {
        ResponseEntity<T> responseEntity = new ResponseEntity();
        responseEntity.setData(data);
        responseEntity.setCode(ResponseState.SUCCESS.getValue());
        return responseEntity;
    }

    protected static <T> String processGetUrl(String url, T data) {
        StringBuffer sb = new StringBuffer();
        Map<String, Object> map = BeanUtil.bean2UrlMap(data);
        Iterator iter = map.keySet().iterator();

        while(iter.hasNext()) {
            String key = (String)iter.next();
            sb.append("&" + key + "=" + map.get(key));
        }

        return url + "?" + sb.substring(1);
    }
}

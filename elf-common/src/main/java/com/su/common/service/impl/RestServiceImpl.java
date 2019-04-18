package com.su.common.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.su.common.Constants;
import com.su.common.exception.CommonException;
import com.su.common.service.RestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * @Desc
 * @author surongyao
 * @date 2018/7/25 下午3:15
 * @version
 */
public class RestServiceImpl implements RestService {

    private RestTemplate restTemplate;

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public JSONObject get(String url) {
        String result = restTemplate.getForEntity(url, String.class).getBody();
        return parseResult(result);
    }

    @Override
    public JSONObject post(String url, String param) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<String> formEntity = new HttpEntity(param, headers);

        String result = restTemplate.postForObject(url, formEntity, String.class);

        return parseResult(result);
    }

    @Override
    public JSONObject exchange(String url, HttpMethod httpMethod, String param) {

        // 请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<>(param, headers);

        ResponseEntity<String> resultEntity = restTemplate.exchange(url, httpMethod, entity, String.class);
        if (resultEntity.getStatusCode() == HttpStatus.OK) {
            return parseResult(resultEntity.getBody());
        }
        return null;
    }


    private JSONObject parseResult(String result) {
        if(StringUtils.isNotEmpty(result)){
            JSONObject json = JSONObject.parseObject(result);
            if(json!=null && json.getInteger("code")==Constants.SUCCESS){
                return json.getJSONObject("data");
            }else if(json!=null && json.getInteger("code")!=Constants.SUCCESS){
                throw new CommonException(json.getInteger("code"), json.getString("msg"));
            }else{
                throw new CommonException(Constants.SERVER_ERROR, "调用服务出错");
            }
        }else{
            throw new CommonException(Constants.SERVER_ERROR, "调用服务出错");
        }
    }

}

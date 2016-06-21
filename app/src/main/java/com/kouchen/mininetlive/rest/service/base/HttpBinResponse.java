package com.kouchen.mininetlive.rest.service.base;

import java.util.Map;

/**
 * Created by cainli on 16/6/5.
 */
public class HttpBinResponse {

    // the request url
    public String url;

    // the requester ip
    public String origin;

    // all headers that have been sent
    public Map headers;

    // url arguments
    public Map args;

    // post form parameters
    public Map form;

    // post body json
    public Map json;
}

package com.tecno_comfenalco.pa.shared.utils.http;

public record RequestParams(String name, Integer page, Integer size, String sortBy, DirectionEnum direction) {

}

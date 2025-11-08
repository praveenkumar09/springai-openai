package com.praveen.springai_openai.record;

import java.util.List;

public record CountryCities(
        String country,
        List<String> cities
){
}

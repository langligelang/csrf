package com.maoge.csrf.config;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Constant {

    public static String phoneAdmin ="13222222222";
    public static Map<String, List<String>> concurrentHashMap = new ConcurrentHashMap<String,List<String>>();

}

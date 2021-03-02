package com.github.jsoncat.common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description 控制台打印 banner 标志
 * @Author Goodenough
 * @Date 2021/3/2 17:36
 */
public class Banner {

    // banner made by https://www.bootschool.net/ascii
    private static final String DEFAULT_BANNER_NAME = "default-banner.txt";
    private static final String CUSTOM_BANNER_NAME = "banner.txt";

    public static void print(){
        URL url = Thread.currentThread().getContextClassLoader().getResource(CUSTOM_BANNER_NAME);
        if (url != null){
            try {
                Path path = Paths.get(url.toURI());
                Files.lines(path).forEach(System.out::println);
            } catch (URISyntaxException | IOException e) {

            }
        } else {
            url = Thread.currentThread().getContextClassLoader().getResource(DEFAULT_BANNER_NAME);
            try {
                Path path = Paths.get(url.toURI());
                Files.lines(path).forEach(System.out::println);
            } catch (URISyntaxException | IOException e) {

            }
        }
    }
}

package com.bolingcavalry.jdk8mavenspringboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class Jdk8mavenspringbootApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(new Date());
    }

}

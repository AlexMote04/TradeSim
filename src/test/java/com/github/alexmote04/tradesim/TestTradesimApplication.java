package com.github.alexmote04.tradesim;

import org.springframework.boot.SpringApplication;

public class TestTradesimApplication {

    public static void main(String[] args) {
        SpringApplication.from(TradesimApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}

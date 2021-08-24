package io.terminus.dice.trial.demo.helloworld.controller;

import io.terminus.dice.monitor.sdk.metrics.Counter;
import io.terminus.dice.monitor.sdk.metrics.CounterBuilder;
import io.terminus.dice.monitor.sdk.metrics.Meter;
import io.terminus.dice.monitor.sdk.metrics.MeterBuilder;
import io.terminus.dice.monitor.sdk.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private static Logger log = LoggerFactory.getLogger(HelloWorldController.class);
    private static Counter counter = MetricRegistry.registerCounter(new CounterBuilder("hello_count", "Hello Api Call Count. "));
    private static Meter meter = MetricRegistry.registerMeter(new MeterBuilder("error_qps", "Request qps."));

    @Value("${app.name}")
    private String appName;

    @GetMapping
    public String home() {
        return "home";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/hello")
    public String hello() {
        MDC.put("route", "hello");
        log.info("Call hello.");
        counter.add(1);
        return "Hello World!!!";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "Hello, this is " + appName;
    }

    @GetMapping("/error")
    public String error() {
        meter.mark(1);
        throw new RuntimeException("错误分析演示");
    }

    @GetMapping("/sleep")
    public String sleep() throws InterruptedException {
        Thread.sleep(5000);
        return "sleep";
    }
}

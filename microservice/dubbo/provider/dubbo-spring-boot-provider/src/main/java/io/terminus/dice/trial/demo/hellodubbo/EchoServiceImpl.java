package io.terminus.dice.trial.demo.hellodubbo;



import org.apache.dubbo.config.annotation.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EchoServiceImpl implements EchoService {

    public String echo(String name) {
        long start = System.currentTimeMillis();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }

        long end = System.currentTimeMillis();
        return "\r\n\t" + start + " Provider received." +
                "\r\n\t\tProvider processed after sleep 1 second! Echo String: \"" + name + "\"" +
                "\r\n\t" + end + " Provider Return";

    }
}

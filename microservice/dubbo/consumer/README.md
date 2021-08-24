# 使用 Spring Boot 开发 Dubbo 微服务应用


# 本地开发应用

## 前提条件
在使用 Spring Boot 开发 Dubbo 微服务应用前，请先完成以下工作：

- 下载 [Maven](https://aliware-images.oss-cn-hangzhou.aliyuncs.com/EDAS/App-develop/apache-maven-3.6.0-bin.tar.gz)并设置环境变量。
- 下载最新版本的 [Nacos Server](https://github.com/alibaba/nacos/releases)。
- 启动 Nacos Server。
  1. 解压下载的 Nacos Server 压缩包
  2. 进入`nacos/bin`目录，启动 Nacos Server。
    - Linux/Unix/Mac 系统：执行命令`sh startup.sh -m standalone`。
    - Windows 系统：双击执行`startup.cmd`文件。




## 创建服务提供者

1. 创建命名为`spring-boot-dubbo-provider`的 Maven 工程。
2. 在`pom.xml`文件中添加所需的依赖。这里以 Spring Boot 2.1.6.RELEASE 为例。

```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.1.6.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>io.terminus.boot.rpc</groupId>
            <artifactId>dubbo-light-provider</artifactId>
            <version>2.1.0-RELEASE</version>
        </dependency>
    </dependencies>
```

3. 开发 Dubbo 服务提供者。
  Dubbo 中服务都是以接口的形式提供的。
  - 在`src/main/java`路径下创建一个 package `io.terminus.dice.trial.demo.hellodubbo`。
  - 在`io.terminus.dice.trial.demo.hellodubbo`下创建一个接口（interface） `EchoService`，里面包含一个 `echo` 方法。

```
package io.terminus.dice.trial.demo.hellodubbo;

public interface EchoService {
    String echo(String name);
}
```

  - 在`io.terminus.dice.trial.demo.hellodubbo`下创建一个类`EchoServiceImpl`，实现此接口。

```
package io.terminus.dice.trial.demo.hellodubbo;

import org.springframework.stereotype.Service;


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
```


4. 配置 Dubbo 服务。
  - 在 `src/main/resources`路径下创建`application.properties`或`application.yaml`文件并打开。
  - 在`application.properties`或`application.yaml`中添加如下配置。

```yaml
spring:
  application.name: dubbo-spring-boot-provider
dubbo:
  application.name: dubbo-spring-boot-provider
  registry:
    address: nacos://${NACOS_ADDRESS:127.0.0.1:8848}?namespace=${NACOS_TENANT_ID:}
  provider:
    scan-packages: io.terminus.dice.trial.demo.hellodubbo
```

**说明**
- 以上三个配置没有默认值，必须要给出具体的配置。
- `dubbo.provider.scan-packages`的值是开发的代码中需要暴露外部调用的，带@Service注解的类所在的包，有多个时用空格进行分隔
- `dubbo.registry.address`的值前缀必须以 **nacos://** 开头，后面的 IP 地址和端口指的是 Nacos Server 的地址。代码示例中为本地地址，如果您将 Nacos Server 部署在其它机器上，请修改为实际的 IP 地址。


5. 开发并启动 Spring Boot 入口类`DubboProvider`。

```
package io.terminus.dice.trial.demo.hellodubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class DubboProvider {
    public static void main(String[] args) {
        SpringApplication.run(DubboProvider.class, args);
    }
}
```

6. 登录 Nacos 控制台 `http://127.0.0.1:8848`，在左侧导航栏中单击**服务列表** ，查看提供者列表。可以看到服务提供者里已经包含了`io.terminus.dice.trial.demo.hellodubbo.EchoService`，且可以查询该服务的服务分组和提供者 IP。


## 创建服务消费者

1. 创建一个 Maven 工程，命名为`spring-boot-dubbo-consumer`。
2. 在`pom.xml`文件中添加相关依赖。这里以 Spring Boot 2.1.6.RELEASE 为例。<br />

```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.1.5.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>io.terminus.boot.rpc</groupId>
            <artifactId>dubbo-light-consumer</artifactId>
            <version>2.1.0-RELEASE</version>
        </dependency>
    </dependencies>
```


3. 开发 Dubbo 消费者。
  - 在`src/main/java`路径下创建 package `io.terminus.dice.trial.demo.hellodubbo`。
  - 在`io.terminus.dice.trial.demo.hellodubbo`下创建一个接口（interface） `EchoService`，里面包含一个 `echo` 方法。

```
package io.terminus.dice.trial.demo.hellodubbo;

public interface EchoService {
    String echo(String name);
}
```

  - 开发 Dubbo 服务调用。例如需要在 Controller 中调用一次远程 Dubbo 服务，开发的代码如下所示。

```
package io.terminus.dice.trial.demo.hellodubbo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoConsumerController {
    @Autowired
    private EchoService demoService;

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public Boolean ping() {
        try {
            String pong = demoService.echo("ping");

            System.out.println("Service returned: " + pong);
            return pong.contains("ping");
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
    }
    @RequestMapping(value = "/consumer-echo/{str}", method = RequestMethod.GET)
    public String feign1(@PathVariable String str) {
        long start = System.currentTimeMillis();

        String result = demoService.echo(str);

        long end = System.currentTimeMillis();
        return "" + start + " Consumer received." +
                "\t" + result +
                "\r\n" + end + " Consumer Return";
    }
}
```


4. 在`application.properties/application.yaml`配置文件中新增以下配置。

```yaml
spring:
  application.name: dubbo-spring-boot-consumer
dubbo:
  application.name: dubbo-spring-boot-consumer
  registry:
    address: nacos://${NACOS_ADDRESS:127.0.0.1:8848}?namespace=${NACOS_TENANT_ID:}
```

**说明**

  - 以上两个配置没有默认值，必须要给出具体的配置。
  - `dubbo.registry.address`的值前缀必须以 `nacos://` 开头，后面的 IP 地址和端口为 Nacos Server 的地址。代码示例中为本地地址，如果您将 Nacos Server 部署在其它机器上，请修改为实际的 IP 地址。

5. 开发并启动 Spring Boot 入口类`DubboConsumer`。

```
package io.terminus.dice.trial.demo.hellodubbo;

import io.terminus.boot.rpc.dubbo.light.consumer.ServiceSubscriber;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDubbo
public class DubboConsumer {
    @Autowired
    private ServiceSubscriber serviceSubscriber;

    @Bean
    public EchoService demoService(){
        return serviceSubscriber.consumer(EchoService.class).subscribe();
    }
    public static void main(String[] args) {
        SpringApplication.run(DubboConsumer.class, args);
    }
}
```

6. 登录 Nacos 控制台 `http://127.0.0.1:8848`，在左侧导航栏中单击**服务列表**，再在服务列表页面单击**调用者列表**页签，查看调用者列表。可以看到包含了`io.terminus.dice.trial.demo.hellodubbo.EchoService`，且可以查看该服务的服务分组和调用者 IP。

## 结果验证


```
`curl http://localhost:8088/ping`
true
```


# 将应用部署到dice
将应用部署到dice，注意dice会注入下面两份application.yml中的变量

```yaml
spring:
  application.name: dubbo-spring-boot-provider
dubbo:
  application.name: dubbo-spring-boot-provider
  registry:
    address: nacos://${NACOS_ADDRESS:127.0.0.1:8848}?namespace=${NACOS_TENANT_ID:}
  provider:
    scan-packages: io.terminus.dice.trial.demo.hellodubbo
```


```yaml
spring:
  application.name: dubbo-spring-boot-consumer
dubbo:
  application.name: dubbo-spring-boot-consumer
  registry:
    address: nacos://${NACOS_ADDRESS:127.0.0.1:8848}?namespace=${NACOS_TENANT_ID:}
```


修改dice.yml，添加注册中心 v2.0的依赖：

![image.png](https://terminus-paas.oss-cn-hangzhou.aliyuncs.com/paas-doc/2020/04/22/13796bbd-8871-446a-a06f-606f1fceb536.png)

这样，服务部署成功后，dice会自动将NACOS_ADDRESS, NACOS_TENANT_ID这两个环境变量注入

![image.png](https://terminus-paas.oss-cn-hangzhou.aliyuncs.com/paas-doc/2020/04/22/1b41b004-1849-4b5e-be37-3034516d66e1.png)

点击注册中心的卡片，可以看到注册上来的服务
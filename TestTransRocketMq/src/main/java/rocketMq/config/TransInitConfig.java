package rocketMq.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.midea.mcsp.ofas.transmq"})
@MapperScan({"com.midea.mcsp.ofas.transmq.gen.mapper"})
public class TransInitConfig {
    public TransInitConfig() {
    }
}

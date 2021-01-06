package rocketMq.core.aspet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @Author：
 * @Description：
 * @Date： 2021/1/4 17:43
 */

@Aspect
@Component
public class TransMQAspect {
    private static final Logger log = LoggerFactory.getLogger(TransMQAspect.class);
    @Autowired
    TransMQ transMQ;
    @Autowired
    TransactionInterceptorHandler transactionInterceptorHandler;

    public TransMQAspect() {
    }

    @Around("@annotation(com.midea.mcsp.ofas.transmq.core.aspect.ITransMQ)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!this.transactionInterceptorHandler.hasTransaction()) {
            return joinPoint.proceed();
        } else {
            MethodSignature msig = (MethodSignature)joinPoint.getSignature();
            Method targetMethod = joinPoint.getTarget().getClass().getDeclaredMethod(msig.getName(), msig.getMethod().getParameterTypes());
            Object[] args = joinPoint.getArgs();
            if (log.isDebugEnabled()) {
                log.debug("TransMQAspect args: {}", JsonUtils.toJsonString(args));
            }

            TransMsg transMsg = new TransMsg();
            String des = args[0].toString();
            if (des != null) {
                if (des.contains(":")) {
                    String[] split = des.split(":");
                    transMsg.setMqTopic(split[0]);
                    transMsg.setMqTag(split[1]);
                } else {
                    transMsg.setMqTopic(des);
                }

                if (args[1] instanceof Message) {
                    Message msg = (Message)args[1];
                    if (msg.getPayload() instanceof String) {
                        transMsg.setMessage(msg.getPayload().toString());
                    } else {
                        String msgContent = JsonUtils.toJsonString(msg.getPayload());
                        if (log.isDebugEnabled()) {
                            log.debug("msgContent: {}", msgContent);
                        }

                        transMsg.setMessage(msgContent);
                    }

                    Optional.ofNullable(msg.getHeaders().get("KEYS")).ifPresent((msgKey) -> {
                        transMsg.setMessageKey(msgKey.toString());
                    });
                } else if (args[1] instanceof String) {
                    transMsg.setMessage(args[1].toString());
                } else {
                    String msgContent = JsonUtils.toJsonString(args[1]);
                    if (log.isDebugEnabled()) {
                        log.debug("msgContent: {}", msgContent);
                    }

                    transMsg.setMessage(msgContent);
                }

                transMsg.setId(Snowflake.nextId());
                if (targetMethod.getName().equals("synSend")) {
                    transMsg.setSendType("synSend");
                } else if (targetMethod.getName().equals("asynSend")) {
                    transMsg.setSendType("asynSend");
                }

                transMsg.setStatus(0);
                transMsg.setRetryTimes(0);
                transMsg.setBusiClass(this.transactionInterceptorHandler.getClassName());
                transMsg.setBusiMethod(this.transactionInterceptorHandler.getMethodName());
                this.transactionInterceptorHandler.addMessage(transMsg);
                return null;
            } else {
                throw new BussinessException();
            }
        }
    }
}


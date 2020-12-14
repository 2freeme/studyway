package com.trade.statemachine.tox;
import com.trade.statemachine.web.entity.TradeOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
public class TradeOrderXChoice implements Guard<TradeOrderXStates, TradeOrderXEvents> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean evaluate(StateContext<TradeOrderXStates, TradeOrderXEvents> context) {
        boolean result=false;
        switch (context.getTarget().getId()) {
            case CHOICE_ALL_CANCELLED:
                return CHOICE_ALL_CANCELLED(context);
            default:
                logger.info("Guard未知状态");
                break;
        }

            TradeOrder s= context.getMessage().getHeaders().get("order", TradeOrder.class);
        return result;
    }
    private boolean CHOICE_ALL_CANCELLED(StateContext<TradeOrderXStates, TradeOrderXEvents> context){
        TradeOrder s= context.getMessage().getHeaders().get("order", TradeOrder.class);
        boolean isTrue=true;
        //判断待评是否>0
        if(isTrue){
            logger.info("[触发分支CHOICE_ALL_CANCELLED,状态自动切换至:] "+"--->待评审");
        }else{
            logger.info("[触发分支CHOICE_ALL_CANCELLED,状态自动切换至:] "+"--->关闭");
        }
        return s.isGroup();
    }
}

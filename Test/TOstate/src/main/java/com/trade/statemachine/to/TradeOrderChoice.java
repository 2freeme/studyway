package com.trade.statemachine.to;
import com.trade.statemachine.web.entity.TradeOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
public class TradeOrderChoice implements Guard<TradeOrderStates, TradeOrderEvents> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean evaluate(StateContext<TradeOrderStates, TradeOrderEvents> context) {
        boolean result=false;
        switch (context.getTarget().getId()) {
            case CHOICE_ALL_CANCELLED:
                return CHOICE_ALL_CANCELLED(context);
            case CHOICE_ALL_BUSINESS_SENT_OR_NOTO:
                return CHOICE_ALL_BUSINESS_SENT_OR_NOTO(context);
            case CHOICE_AUDIT_FINISH:
                return CHOICE_AUDIT_FINISH(context);
            case CHOICE_DISPATCH_ALL:
                return CHOICE_DISPATCH_ALL(context);
            case CHOICE_EXTRA_MONEY:
                return CHOICE_EXTRA_MONEY(context);
            case CHOICE_GROUP_RAISED:
                return CHOICE_GROUP_RAISED(context);
            case CHOICE_STORE_SENT_OR_INDEED:
                return CHOICE_STORE_SENT_OR_INDEED(context);
            default:
                logger.info("Guard未知状态");
                break;
        }

            TradeOrder s= context.getMessage().getHeaders().get("order", TradeOrder.class);

        return result;
    }
    private boolean CHOICE_GROUP_RAISED(StateContext<TradeOrderStates, TradeOrderEvents> context){
        TradeOrder s= context.getMessage().getHeaders().get("order", TradeOrder.class);
        if(s.isGroup()){
            logger.info("[触发分支Guard_CHOICE_GROUP_RAISED,状态自动切换至:] "+"--->是,待成团");
        }else{
            logger.info("[触发分支Guard_CHOICE_GROUP_RAISED,状态自动切换至:] "+"--->否,所有商品是否全部转派");
        }
        return s.isGroup();
    }

    private boolean CHOICE_ALL_CANCELLED(StateContext<TradeOrderStates, TradeOrderEvents> context){
        boolean result=true;
        if(result){
            logger.info("[触发分支Guard_CHOICE_ALL_CANCELLED,状态自动切换至:] "+"--->无派生TO");
        }else{
            logger.info("[触发分支Guard_CHOICE_ALL_CANCELLED,状态自动切换至:] "+"--->有派生TO,或待评>0");
        }
        return result;
    }
    private boolean CHOICE_ALL_BUSINESS_SENT_OR_NOTO(StateContext<TradeOrderStates, TradeOrderEvents> context){
        boolean result=true;
        if(result){
            logger.info("[触发分支Guard_CHOICE_ALL_BUSINESS_SENT_OR_NOTO,状态自动切换至:] "+"--->是,去到代签收");
        }else{
            logger.info("[触发分支Guard_CHOICE_ALL_BUSINESS_SENT_OR_NOTO,状态自动切换至:] "+"--->否,去到判断门店是否发货完毕或无需发货");
        }
        return result;
    }
    private boolean CHOICE_AUDIT_FINISH(StateContext<TradeOrderStates, TradeOrderEvents> context){
        boolean result=true;
        if(result){
            logger.info("[触发分支Guard_CHOICE_AUDIT_FINISH,状态自动切换至:] "+"--->待评=0,评审数>0");
        }else{
            logger.info("[触发分支Guard_CHOICE_AUDIT_FINISH,状态自动切换至:] "+"--->待评>0");
        }
        return result;
    }
    private boolean CHOICE_DISPATCH_ALL(StateContext<TradeOrderStates, TradeOrderEvents> context){
        boolean result=true;
        if(result){
            logger.info("[触发分支Guard_CHOICE_DISPATCH_ALL,状态自动切换至:] "+"--->是,待其他发货");
        }else{
            logger.info("[触发分支Guard_CHOICE_DISPATCH_ALL,状态自动切换至:] "+"--->否,还未评审过");
        }
        return result;
    }
    private boolean CHOICE_EXTRA_MONEY(StateContext<TradeOrderStates, TradeOrderEvents> context){
        boolean result=true;
        if(result){
            logger.info("[触发分支Guard_CHOICE_EXTRA_MONEY,状态自动切换至:] "+"--->代付尾款");
        }else{
            logger.info("[触发分支Guard_CHOICE_EXTRA_MONEY,状态自动切换至:] "+"--->(差价=0),判断所有商品是否全部转派");
        }
        return result;
    }
    private boolean CHOICE_STORE_SENT_OR_INDEED(StateContext<TradeOrderStates, TradeOrderEvents> context){
        boolean result=true;
        if(result){
            logger.info("[触发分支Guard_CHOICE_SROTE_SENT_OR_INDEED,状态自动切换至:] "+"--->是,去到待其他发货");
        }else{
            logger.info("[触发分支Guard_CHOICE_SROTE_SENT_OR_INDEED,状态自动切换至:] "+"--->否,去到判断门店是否发货完毕或无需发货");
        }
        return result;
    }
}

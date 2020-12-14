package com.trade.statemachine.pso_refund;
import com.trade.statemachine.web.entity.PostSaleOrderRefund;
import com.trade.statemachine.web.entity.TradeOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
public class PostSaleOrderRefundChoice implements Guard<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean evaluate(StateContext<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> context) {
        boolean result=false;
        PostSaleOrderRefund s= context.getMessage().getHeaders().get("order", PostSaleOrderRefund.class);
        return result;
    }
}

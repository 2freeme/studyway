package rocketMq.gen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.mcsp.aftersale.atoapi.gen.dto.TcTransactionMsgGenDTO;
import com.midea.mcsp.aftersale.atosvc.gen.entity.TcTransactionMsg;
import com.midea.mcsp.base.core.util.request.*;
import com.midea.mcsp.base.core.util.request.QueryConditionRequest;
import com.midea.mcsp.base.core.util.response.PaginationResult;
import com.midea.mcsp.base.core.util.response.Result;
import rocketMq.common.Result;
import rocketMq.gen.TcTransactionMsgGenDTO;
import rocketMq.gen.entity.TcTransactionMsg;

import java.util.Collection;
import java.util.List;

public interface TcTransactionMsgGenService extends IService<TcTransactionMsg> {

    /**
    * 单条数据插入
    * @param request
    * @return
    */
    Result<TcTransactionMsgGenDTO> insertTcTransactionMsg(TcTransactionMsgGenDTO request);

    /**
    * 批量插入
    * @param request
    * @return
    */
    Result<Boolean> batchInsertTcTransactionMsg(List<TcTransactionMsgGenDTO> request);

    /**
    * 根据id删除数据
    * @param request
    * @return
    */
    Result<Boolean> deleteTcTransactionMsg(Long request);

    /**
    * 根据id列表删除数据
    * @param request
    * @return
    */
    Result<Boolean> batchDeleteTcTransactionMsg(Collection<Long> request);

    /**
    * 根据搜索条件删除数据
    * @param request
    * @return
    */
    Result<Boolean> deleteTcTransactionMsgByWrapper(QueryConditionRequest request);

    /**
    * 根据id更新数据
    * @param request
    * @return
    */
    Result<Boolean> updateTcTransactionMsg(TcTransactionMsgGenDTO request);

    /**
    * 批量更新数据
    * @param request
    * @return
    */
    Result<Boolean> batchUpdateTcTransactionMsg(List<TcTransactionMsgGenDTO> request);

    /**
    * 根据搜索条件更新数据
    * @param request
    * @return
    */
    Result<Boolean> updateTcTransactionMsgByWrapper(UpdateByWrapperRequest<TcTransactionMsgGenDTO> request);

    /**
    * 根据id列表更新数据
    * @param request
    * @return
    */
    Result<Boolean> updateTcTransactionMsgByIds(UpdateByIdsRequest<TcTransactionMsgGenDTO> request);

    /**
    * 根据id查询数据
    * @param request
    * @return
    */
    Result<TcTransactionMsgGenDTO> selectTcTransactionMsgById(Long request);

    /**
    * 批量插入或更新数据
    * @param request
    * @return
    */
    Result<Boolean> batchSouTcTransactionMsg(List<TcTransactionMsgGenDTO> request);

    /**
    * 根据搜索条件查询数据
    * @param request
    * @param pagination
    * @return
    */
    PaginationResult<List<TcTransactionMsgGenDTO>> queryTcTransactionMsg(QueryConditionRequest request, Pagination pagination);

    /**
    * 根据搜索条件查询数据总量
    * @param request
    * @return
    */
    Result<Integer> countTcTransactionMsg(QueryConditionRequest request);
}

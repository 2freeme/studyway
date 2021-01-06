package rocketMq.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.mcsp.aftersale.atoapi.gen.dto.TcTransactionMsgGenDTO;
import com.midea.mcsp.aftersale.atosvc.gen.entity.TcTransactionMsg;
import com.midea.mcsp.aftersale.atosvc.gen.mapper.TcTransactionMsgGenMapper;
import com.midea.mcsp.aftersale.atosvc.gen.service.TcTransactionMsgGenService;
import com.midea.mcsp.atomic.svccore.util.QueryWrapperUtil;
import com.midea.mcsp.base.core.util.request.*;
import com.midea.mcsp.base.core.util.response.PaginationResult;
import com.midea.mcsp.base.core.util.response.Result;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.midea.mcsp.base.core.util.request.Pagination.*;

@Service
public class TcTransactionMsgGenServiceImpl extends ServiceImpl<TcTransactionMsgGenMapper, TcTransactionMsg> implements TcTransactionMsgGenService {

    @Override
    public Result<TcTransactionMsgGenDTO> insertTcTransactionMsg(TcTransactionMsgGenDTO request) {
        TcTransactionMsg entity = new TcTransactionMsg().accept(request);
        this.save(entity);
        return Result.getSuccessResult(entity.send());
    }

    @Override
    public Result<Boolean> batchInsertTcTransactionMsg(List<TcTransactionMsgGenDTO> request) {
        if(CollectionUtils.isEmpty(request)){
            return Result.getSuccessResult(true);
        }
        List<TcTransactionMsg> entitys = new ArrayList<>();
        for(TcTransactionMsgGenDTO dto : request){
            entitys.add(new TcTransactionMsg().accept(dto));
        }
        return Result.getSuccessResult(this.saveBatch(entitys));
    }

    @Override
    public Result<Boolean> deleteTcTransactionMsg(Long request) {
        return Result.getSuccessResult(this.removeById(request));
    }

    @Override
    public Result<Boolean> batchDeleteTcTransactionMsg(Collection<Long> request) {
        return Result.getSuccessResult(this.removeByIds(request));
    }

    @Override
    public Result<Boolean> deleteTcTransactionMsgByWrapper(QueryConditionRequest request) {
        return Result.getSuccessResult(this.remove(QueryWrapperUtil.parseWrapper(request)));
    }

    @Override
    public Result<Boolean> updateTcTransactionMsg(TcTransactionMsgGenDTO request) {
        TcTransactionMsg entity = new TcTransactionMsg().accept(request);
        return Result.getSuccessResult(this.updateById(entity));
    }

    @Override
    public Result<Boolean> batchUpdateTcTransactionMsg(List<TcTransactionMsgGenDTO> request) {
        if(CollectionUtils.isEmpty(request)) return Result.getSuccessResult(false);
        List<TcTransactionMsg> entitys = new ArrayList<>();
        for(TcTransactionMsgGenDTO dto : request){
            entitys.add(new TcTransactionMsg().accept(dto));
        }
        return Result.getSuccessResult(this.updateBatchById(entitys));
    }

    @Override
    public Result<Boolean> updateTcTransactionMsgByWrapper(UpdateByWrapperRequest<TcTransactionMsgGenDTO> request){
        TcTransactionMsg entity = new TcTransactionMsg().accept(request.getEntity());
        return Result.getSuccessResult(this.update(entity, QueryWrapperUtil.parseWrapper(request.getRequest())));
    }

    @Override
    public Result<Boolean> updateTcTransactionMsgByIds(UpdateByIdsRequest<TcTransactionMsgGenDTO> request) {
        TcTransactionMsg entity = new TcTransactionMsg().accept(request.getEntity());
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.in("id", request.getIds());
        return Result.getSuccessResult(this.update(entity, wrapper));
    }

    @Override
    public Result<TcTransactionMsgGenDTO> selectTcTransactionMsgById(Long request) {
        TcTransactionMsg entity = this.getById(request);
        return Result.getSuccessResult(entity == null? null : entity.send());
    }

    @Override
    public Result<Boolean> batchSouTcTransactionMsg(List<TcTransactionMsgGenDTO> request) {
        if(CollectionUtils.isEmpty(request)){
            return Result.getSuccessResult(true);
        }
        List<TcTransactionMsg> entitys = new ArrayList<>();
        for(TcTransactionMsgGenDTO dto : request){
            entitys.add(new TcTransactionMsg().accept(dto));
        }
        return Result.getSuccessResult(this.saveOrUpdateBatch(entitys));
    }

    @Override
    public PaginationResult<List<TcTransactionMsgGenDTO>> queryTcTransactionMsg(QueryConditionRequest request, Pagination pagination){
        pagination = Optional.ofNullable(pagination).orElse(new Pagination(PAGE_NO, MAX_PAGE_SIZE, DEFAULT_COUNT_FLAG));
        IPage<TcTransactionMsg> result = this.page(pagination.toPage(), QueryWrapperUtil.parseWrapper(request));
        pagination.setCount(result.getTotal());
        List<TcTransactionMsgGenDTO> dtos = result.getRecords()==null? null : result.getRecords().stream().map(TcTransactionMsg::send).collect(Collectors.toList());
        return PaginationResult.getSuccessResult(dtos, pagination);
    }

    @Override
    public Result<Integer> countTcTransactionMsg(QueryConditionRequest request){
        return Result.getSuccessResult(this.count(QueryWrapperUtil.parseWrapper(request)));
    }
}

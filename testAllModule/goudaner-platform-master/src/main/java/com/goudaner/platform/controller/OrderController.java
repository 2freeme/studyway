package com.goudaner.platform.controller;

import com.goudaner.platform.base.SyResult;
import com.goudaner.platform.dto.GdOrderDto;
import com.goudaner.platform.service.GdOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private GdOrderService gdOrderService;
    @RequestMapping("/orderEvent")
    public SyResult<String> orderEvent(){
        try {
            GdOrderDto gdOrderDto = new GdOrderDto();
            gdOrderDto.setEventCode(1);
            gdOrderDto.setOrderId("O0509143813041205261");
            gdOrderService.orderEvent(gdOrderDto);
            return new   SyResult<String>();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("程序异常，等会你tm再试");
//            return "程序异常，等会你tm再试";
        return null;
        }
    }

    @RequestMapping("/createOrder")
    public String createOrder(@RequestBody GdOrderDto gdOrderDto){
        try {
            return gdOrderService.createOrder(gdOrderDto);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("程序异常，等会你tm再试");
            return "程序异常，等会你tm再试";
        }
    }
}

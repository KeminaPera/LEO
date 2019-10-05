package com.keminapera.constellation.leo.controller;

import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.service.LogisticsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物流信息获取controller
 *
 * @author KeminaPera
 * @date 2019/10/2 10:09
 */
@RestController
@RequestMapping(value = "/logistics")
public class LogisticsConroller {

    private LogisticsService logisticsService;
    public LogisticsConroller(LogisticsService logisticsService) {
        this.logisticsService = logisticsService;
    }

    @GetMapping(value = "/one")
    @ApiOperation(value = "获取单个物流信息", tags = "v0.1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "company", value = "快递公司号", dataType = "int", example = "4"),
            @ApiImplicitParam(name = "number", value = "快递单号", dataType = "string", example = "73120366780667")})
    public LogisticsVo getLogisticsInfo(int company, String number) {
        return logisticsService.query(number, company);
    }
}

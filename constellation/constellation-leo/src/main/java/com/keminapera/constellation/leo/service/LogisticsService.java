package com.keminapera.constellation.leo.service;

import com.keminapera.constellation.leo.comon.LogisticsStateEnum;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.manager.ExpressCompanySelector;
import com.keminapera.constellation.leo.service.manager.IExpressCompany;
import com.keminapera.constellation.leo.service.storage.LogisticsInfoStorage;
import com.keminapera.constellation.leo.service.storage.LogisticsStorage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 物流信息的业务逻辑类
 *
 * @author KeminaPera
 * @date 2019/10/2 10:13
 */
@Service
public class LogisticsService {
    private LogisticsStorage logisticsStorage;
    private LogisticsInfoStorage logisticsInfoStorage;
    private ExpressCompanySelector expressCompanySelector;

    public LogisticsService(LogisticsStorage logisticsStorage, LogisticsInfoStorage logisticsInfoStorage, ExpressCompanySelector expressCompanySelector) {
        this.logisticsStorage = logisticsStorage;
        this.logisticsInfoStorage = logisticsInfoStorage;
        this.expressCompanySelector = expressCompanySelector;
    }

    /**
     *  通过快递单号和快递公司查询物流信息
     *
     * @param number 快递单号
     * @param company 快递公司
     * @return 物流信息
     */
    public LogisticsVo query(String number, int company){
        LogisticsVo logisticsVo = new LogisticsVo();
        Logistics localLogistics = logisticsStorage.getLogisticsByNumAndCom(number, company);
        IExpressCompany expressCompany = expressCompanySelector.select(company);
        //该系统还没有该快递物流的信息
        if (localLogistics == null) {
            LogisticsVo aLogisticsVo = expressCompany.queryLogistics(number);
            logisticsStorage.insert(aLogisticsVo.getLogistics());
            logisticsInfoStorage.insertBatch(aLogisticsVo.getLogisticsInfoList());
            return aLogisticsVo;
        }
        //现在本地查询，如果已完成，直接返回结果
        if (localLogistics.getState() == LogisticsStateEnum.FINISHED.getState()) {
            logisticsVo.setLogistics(localLogistics);
            List<LogisticsInfo> logisticsInfoList = logisticsInfoStorage.getLogisticsInfo(number);
            Optional.ofNullable(logisticsInfoList).ifPresent(logisticsVo::setLogisticsInfoList);
            return logisticsVo;
        }
        List<LogisticsInfo> logisticsInfoList = expressCompany.queryLogisticsInfoList(number);
        Date latestTime = localLogistics.getLatestTime();
        List<LogisticsInfo> insertList = new ArrayList<>(16);
        LogisticsInfo latestLogisticsInfo = null;
        Date maxDate = latestTime;
        for (LogisticsInfo logisticsInfo : logisticsInfoList) {
            Date time = logisticsInfo.getTime();
            if (time.after(latestTime)) {
                insertList.add(logisticsInfo);
                if (time.after(maxDate)) {
                    maxDate = time;
                    latestLogisticsInfo = logisticsInfo;
                }
            }
        }
        //比对是否最新信息已更新
        if (!insertList.isEmpty()) {
            logisticsInfoStorage.insertBatch(insertList);
            localLogistics.setLatestTime(latestLogisticsInfo.getTime());
            localLogistics.setLatestProgress(latestLogisticsInfo.getDesc());
            logisticsStorage.update(number, latestLogisticsInfo.getTime(), latestLogisticsInfo.getDesc());
        }
        logisticsVo.setLogistics(localLogistics);
        logisticsVo.setLogisticsInfoList(logisticsInfoList);
        return logisticsVo;
    }
}

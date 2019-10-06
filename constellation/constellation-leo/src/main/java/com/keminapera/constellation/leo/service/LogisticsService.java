package com.keminapera.constellation.leo.service;

import com.keminapera.constellation.leo.comon.LogisticsStateEnum;
import com.keminapera.constellation.leo.entity.LogisticsVo;
import com.keminapera.constellation.leo.pojo.Logistics;
import com.keminapera.constellation.leo.pojo.LogisticsInfo;
import com.keminapera.constellation.leo.service.manager.ExpressCompanySelector;
import com.keminapera.constellation.leo.service.manager.IExpressCompany;
import com.keminapera.constellation.leo.service.manager.IWebSite;
import com.keminapera.constellation.leo.service.storage.LogisticsInfoStorage;
import com.keminapera.constellation.leo.service.storage.LogisticsStorage;
import org.apache.commons.collections4.CollectionUtils;
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
            LogisticsVo aLogisticsVo = expressCompany instanceof IWebSite ? ((IWebSite) expressCompany).queryLogistics(number, company) : expressCompany.queryLogistics(number);
            logisticsStorage.insert(aLogisticsVo.getLogistics());
            logisticsInfoStorage.insertBatch(aLogisticsVo.getLogisticsInfoList());
            return aLogisticsVo;
        } else if (localLogistics.getState() == LogisticsStateEnum.FINISHED.getState()) {
            //先在本地查询，如果已完成，直接返回结果
            logisticsVo.setLogistics(localLogistics);
            List<LogisticsInfo> logisticsInfoList = logisticsInfoStorage.getLogisticsInfo(number);
            Optional.ofNullable(logisticsInfoList).ifPresent(logisticsVo::setLogisticsInfoList);
            return logisticsVo;
        }
        //本地系统已有该快递物流信息且还没有完成，对比是否物流信息已更新
        LogisticsVo vo = expressCompany instanceof IWebSite ? ((IWebSite) expressCompany).queryLogistics(number, company) : expressCompany.queryLogistics(number);
        Logistics remoteLogistics = vo.getLogistics();
        List<LogisticsInfo> orderedLogisticsInfoList = vo.getLogisticsInfoList();
        List<LogisticsInfo> insertList = new ArrayList<>(16);
        if (remoteLogistics.getLatestTime().after(localLogistics.getLatestTime())) {
            checkLatestLogistics(localLogistics, orderedLogisticsInfoList, insertList);
            logisticsInfoStorage.insertBatch(insertList);
            logisticsStorage.updateLogisticsStateInfo(remoteLogistics);
        }
        logisticsVo.setLogistics(localLogistics);
        logisticsVo.setLogisticsInfoList(orderedLogisticsInfoList);
        return logisticsVo;
    }

    /**
     * 物流信息中需要添加到本地的物流信息列表
     *
     * @param localLogistics           本地物流信息
     * @param orderedLogisticsInfoList 已拍好序的物流信息列表，{@link LogisticsInfo#getTime()}的降序
     * @param insertList               需要添加的列表
     */
    private void checkLatestLogistics(Logistics localLogistics, List<LogisticsInfo> orderedLogisticsInfoList, List<LogisticsInfo> insertList) {
        if (CollectionUtils.isNotEmpty(orderedLogisticsInfoList)) {
            Date oldLatestTime = localLogistics.getLatestTime();
            for (LogisticsInfo logisticsInfo : orderedLogisticsInfoList) {
                if (logisticsInfo.getTime().after(oldLatestTime)) {
                    insertList.add(logisticsInfo);
                } else {
                    break;
                }
            }
        }
    }
}

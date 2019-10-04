package com.keminapera.constellation.leo.cache;

import com.keminapera.constellation.leo.comon.QueryTypeEnum;
import com.keminapera.constellation.leo.dao.CompanyDao;
import com.keminapera.constellation.leo.pojo.Company;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目初始化的缓存
 *
 * @author KeminaPera
 * @date 2019/10/2 15:36
 */
@Slf4j
public class BaseInfoCache {
    private CompanyDao companyDao;
    public BaseInfoCache(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Bean(name = "companyNumber2ComCache")
    public Map<Integer, String> getValidCompany(){
        List<Company> validCompanyList = companyDao.getAll(QueryTypeEnum.VALID.getType());
        if (CollectionUtils.isEmpty(validCompanyList)) {
            List<Company> allCompany = companyDao.getAll(QueryTypeEnum.ALL.getType());
            if (CollectionUtils.isEmpty(allCompany)) {
                log.error("没有合法的快递公司信息,请执行company.sql脚本初始化数据库");
            }else{
                log.error("所有快递公司都无效，请选择合适的更新成有效");
            }
        }
        return validCompanyList.stream().collect(Collectors.toMap(Company::getNumber, Company::getCom));
    }

    @Bean(name = "companyCom2NumberCache")
    public Map<String, Integer> getValidCompany2(){
        List<Company> validCompanyList = companyDao.getAll(QueryTypeEnum.VALID.getType());
        if (CollectionUtils.isEmpty(validCompanyList)) {
            List<Company> allCompany = companyDao.getAll(QueryTypeEnum.ALL.getType());
            if (CollectionUtils.isEmpty(allCompany)) {
                log.error("没有合法的快递公司信息,请执行company.sql脚本初始化数据库");
            }else{
                log.error("所有快递公司都无效，请选择合适的更新成有效");
            }
        }
        return validCompanyList.stream().collect(Collectors.toMap(Company::getCom, Company::getNumber));
    }
}

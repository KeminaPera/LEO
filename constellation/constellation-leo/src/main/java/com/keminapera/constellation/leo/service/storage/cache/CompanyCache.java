package com.keminapera.constellation.leo.service.storage.cache;

import com.keminapera.constellation.leo.comon.QueryTypeEnum;
import com.keminapera.constellation.leo.dao.CompanyDao;
import com.keminapera.constellation.leo.pojo.Company;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 快递公司cache
 *
 * @author KeminaPera
 * @date 2019/10/2 15:24
 */
@Component
public class CompanyCache {
    private CompanyDao companyDao;
    public CompanyCache(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    /**
     * 更新快递公司信息
     * @param company 快递公司实体类
     */
    public void update(Company company) {
        companyDao.update(company);
    }

    /**
     * 设置该快递公司是否有效
     * @param id 公司编号
     * @param valid 是否有效
     */
    public void updateValidById(String id, int valid) {
        companyDao.updateValidById(id, valid);
    }

    /**
     * 根据编号查询快递公司信息
     * @param id 编号
     * @return 目标结果
     */
    public Company select(String id) {
        return companyDao.select(id);
    }

    /**
     * 获取该类型的公司列表
     * @param type {@link QueryTypeEnum} 查询类型
     * @return 快递公司列表
     */
    public List<Company> getAll(int type) {
        return companyDao.getAll(type);
    }
}

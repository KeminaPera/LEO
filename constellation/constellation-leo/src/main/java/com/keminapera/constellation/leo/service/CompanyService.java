package com.keminapera.constellation.leo.service;

import com.keminapera.constellation.leo.comon.QueryTypeEnum;
import com.keminapera.constellation.leo.pojo.Company;
import com.keminapera.constellation.leo.service.storage.CompanyStorage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 快递公司service
 *
 * @author KeminaPera
 * @date 2019/10/2 15:31
 */
@Service
public class CompanyService {
    private CompanyStorage companyStorage;
    public CompanyService(CompanyStorage companyStorage) {
        this.companyStorage = companyStorage;
    }

    /**
     * 更新快递公司信息
     * @param company 快递公司实体类
     */
    public void update(Company company) {
        companyStorage.update(company);
    }

    /**
     * 设置该快递公司是否有效
     * @param id 公司编号
     * @param valid 是否有效
     */
    public void updateValidById(String id, int valid) {
        companyStorage.updateValidById(id, valid);
    }

    /**
     * 根据编号查询快递公司信息
     * @param id 编号
     * @return 目标结果
     */
    public Company select(String id) {
        return companyStorage.select(id);
    }

    /**
     * 获取该类型的公司列表
     * @param type {@link QueryTypeEnum} 查询类型
     * @return 快递公司列表
     */
    public List<Company> getAll(int type) {
        return companyStorage.getAll(type);
    }
}

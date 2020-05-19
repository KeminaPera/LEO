package com.keminapera.constellation.leo.mapper;

import com.keminapera.constellation.leo.comon.QueryTypeEnum;
import com.keminapera.constellation.leo.pojo.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 快递公司mapper
 *
 * @author KeminaPera
 * @date 2019/10/2 14:35
 */
@Repository
@Mapper
public interface CompanyMapper {

    /**
     * 更新快递公司
     * @param company 需更新的公司实体类
     */
    void update(Company company);

    /**
     * 删除快递公司（逻辑删除）
     * @param companyId 快递公司编号
     * @param valid 是否有效
     */
    void updateValidById(String companyId, int valid);

    /**
     * 查询快递公司
     * @param companyId 公司变编号
     * @return Company 目标结果
     */
    Company select(String companyId);

    /**
     * 获取所有快递公司信息
     * @param type {@link QueryTypeEnum} 获取该类型的快递公司
     * @return List<Company> 符合条件的快递公司列表
     */
    List<Company> getAll(int type);
}

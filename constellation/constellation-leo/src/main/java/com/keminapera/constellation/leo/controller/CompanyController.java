package com.keminapera.constellation.leo.controller;

import com.keminapera.constellation.leo.comon.QueryTypeEnum;
import com.keminapera.constellation.leo.pojo.Company;
import com.keminapera.constellation.leo.service.CompanyService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 快递公司controller
 *
 * @author KeminaPera
 * @date 2019/10/2 15:34
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    private CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * 更新快递公司
     * @param company 公司实体类
     */
    @PutMapping("/")
    @ApiOperation(value = "更新快递公司信息", tags = "v0.1")
    @ApiImplicitParam(name = "company", value = "需要更新的快递公司信息", dataType = "Company")
    public void update(Company company) {
        companyService.update(company);
    }

    /**
     * 更新该快递公司是否有效
     * @param company 快递公司实体类
     */
    @PatchMapping("/valid")
    @ApiOperation(value = "修改快递公司是否有效", tags = "v0.1")
    @ApiImplicitParam(name = "company", value = "快递公司信息", dataType = "Company")
    public void updateValidById(Company company) {
        companyService.updateValidById(company.getId(), company.getValid());
    }

    /**
     * 根据id获取公司信息
     * @param id 快递公司编号
     * @return 该快递公司
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID获取公司信息", tags = "v0.1")
    @ApiImplicitParam(name = "id", value = "公司ID", dataType = "String")
    public Company select(@PathVariable("id") String id) {
        return companyService.select(id);
    }

    /**
     * 获取所有满足该类型的快递公司
     * @param type {@link QueryTypeEnum} 查询类型
     * @return 快递公司列表
     */
    @GetMapping("/type/{type}")
    @ApiOperation(value = "获取满足条件的所有公司信息", tags = "v0.1")
    @ApiImplicitParam(name = "type", value = "查询类型", paramType = "path", dataType = "int", allowableValues = "1,2,3")
    public List<Company> getAll(@PathVariable("type") int type) {
        return companyService.getAll(type);
    }
}

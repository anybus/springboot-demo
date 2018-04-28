package com.eva.servicedemo1.controller;


import com.eva.common.cn.IdCardNoTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest-api")
@PropertySource(value = "classpath:commonutil.properties")
public class DemoRestController {
    @Value("${testName}")
    private String testName;

    @RequestMapping("/getBirthdayByIDCard")
    public String getBirthdayFromIDCard(String idcard) {
        return IdCardNoTool.getBirthday(idcard);
    }

    @RequestMapping("/getName")
    public String getName() {
        return this.testName;
    }
}

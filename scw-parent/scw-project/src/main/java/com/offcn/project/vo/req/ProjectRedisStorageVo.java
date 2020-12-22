package com.offcn.project.vo.req;

import com.offcn.project.po.TReturn;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRedisStorageVo {//组合实体类(1.project.2.多-标签,3.多-分类)

    private String projectToken;//临时token(前3步存取project从redis)



    private Integer memberid;//发起人


    private String name;//项目名称
    private String remark;
    private Integer money;//筹资金额
    private Integer day;//筹资天数
    private String headerImage;//头图
    private List<String> detailsImage;//详情图(多)
    //2.多-标签
    private List<Integer> tagids;
    //3.多-分类
    private List<Integer> typeids;



    private List<TReturn> projectReturns;//项目汇报
}

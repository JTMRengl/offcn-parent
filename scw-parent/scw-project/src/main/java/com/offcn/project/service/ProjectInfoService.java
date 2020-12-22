package com.offcn.project.service;

import com.offcn.project.po.*;

import java.util.List;

public interface ProjectInfoService {
    /**
     * 根据项目id查询回报
     * @param projectid
     * @return
     */
    public List<TReturn> getProjectReturns(Integer projectid);

    /**
     * 根据项目id查询图片
     * @param projectid
     * @return
     */
    public List<TProjectImages> getProjectImages(Integer projectid);

    /**
     * 查询所有项目
     * @return
     */
    public List<TProject> getAllProjects();

    /**
     * 获取项目信息
     * @param projectId
     * @return
     */
    TProject getProjectInfo(Integer projectId);

    /**
     * 获得项目标签
     * @return
     */
    List<TTag> getAllProjectTags();
    /**
     * 获取项目分类
     * @return
     */
    List<TType> getProjectTypes();

    /**
     * 获取回报信息
     * @param returnId
     * @return
     */
    TReturn getReturnInfo(Integer returnId);

}

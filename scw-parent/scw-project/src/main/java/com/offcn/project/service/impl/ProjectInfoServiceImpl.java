package com.offcn.project.service.impl;

import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {

    @Autowired
    private TReturnMapper returnMapper;

    @Autowired
    private TProjectImagesMapper projectImagesMapper;

    @Autowired
    private TProjectMapper projectMapper;

    @Autowired
    private TTagMapper tagMapper;

    @Autowired
    private TTypeMapper typeMapper;


    @Override
    public List<TReturn> getProjectReturns(Integer projectid) {
        TReturnExample example = new TReturnExample();
        example.createCriteria().andProjectidEqualTo( projectid );
        return returnMapper.selectByExample( example );
    }

    @Override
    public List<TProjectImages> getProjectImages(Integer projectid) {
        TProjectImagesExample example = new TProjectImagesExample();
        example.createCriteria().andProjectidEqualTo( projectid );
        return projectImagesMapper.selectByExample( example );
    }

    @Override
    public List<TProject> getAllProjects() {
        return projectMapper.selectByExample( null );
    }

    /**
     * 获取项目信息
     *
     * @param projectId
     * @return
     */
    @Override
    public TProject getProjectInfo(Integer projectId) {
        TProject project = projectMapper.selectByPrimaryKey(projectId);
        return project;
    }
    /**
     * 获得项目标签
     *
     * @return
     */
    @Override
    public List<TTag> getAllProjectTags() {
        return tagMapper.selectByExample(null);
    }

    /**
     * 获取项目分类
     *
     * @return
     */
    @Override
    public List<TType> getProjectTypes() {
        return typeMapper.selectByExample(null);
    }
    /**
     * 获取回报信息
     *
     * @param returnId
     * @return
     */
    @Override
    public TReturn getReturnInfo(Integer returnId) {
        return returnMapper.selectByPrimaryKey(returnId);
    }

}

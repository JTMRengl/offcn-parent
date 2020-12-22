package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.project.constants.ProjectConstant;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.enums.ProjectStatusEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private TProjectMapper projectMapper;

    @Autowired
    private TProjectImagesMapper projectImagesMapper;

    @Autowired
    private TProjectTagMapper projectTagMapper;

    @Autowired
    private TProjectTypeMapper projectTypeMapper;

    @Autowired
    private TReturnMapper returnMapper;

    @Override
    public String initCreateProject(Integer memberId) {

        String token = UUID.randomUUID().toString().replace( "-", "" );

        ProjectRedisStorageVo initVo = new ProjectRedisStorageVo();
        initVo.setProjectToken( token );
        initVo.setMemberid( memberId );

        String jsonString = JSON.toJSONString( initVo );

        redisTemplate.opsForValue().set( ProjectConstant.TEMP_PROJECT_PREFIX +token,  jsonString);

        return token;
    }

    @Override
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo project) {

        //1.project(基本信息)
        TProject projectBase = new TProject();
        BeanUtils.copyProperties( project,projectBase );
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        projectBase.setCreatedate(df.format(new Date()));
        projectMapper.insertSelective( projectBase );

        //2.图片
        Integer projectId = projectBase.getId();
        String headerImage = project.getHeaderImage();
        TProjectImages images = new TProjectImages(null,projectId,headerImage, ProjectImageTypeEnume.HEADER.getCode());
        //2、将项目上传的图片保存起来
        //2.1）、保存头图
        projectImagesMapper.insertSelective(images);
        List<String> detailsImage = project.getDetailsImage();
        //2.2)、保存详情图
        for(String string:detailsImage){
            TProjectImages img = new TProjectImages(null,projectId,string, ProjectImageTypeEnume.DETAILS.getCode());
            projectImagesMapper.insertSelective(img);
        }

        //插标签
        List<Integer> tagids = project.getTagids();
        for (Integer tagid : tagids) {
            TProjectTag projectTag = new TProjectTag(null,projectId,tagid);
            projectTagMapper.insertSelective( projectTag );
        }

        //4、保存项目的分类信息
        List<Integer> typeids = project.getTypeids();
        for (Integer tid:typeids) {
            TProjectType tProjectType = new TProjectType(null,projectId,tid);
            projectTypeMapper.insertSelective(tProjectType);
        }

        //插回报
        List<TReturn> returns = project.getProjectReturns();
        //设置项目的id
        for(TReturn tReturn:returns){
            tReturn.setProjectid(projectId);
            returnMapper.insertSelective(tReturn);
        }

        //删除redis中的vo【临时存储数据】
        redisTemplate.delete( ProjectConstant.TEMP_PROJECT_PREFIX + project.getProjectToken() );
    }

}

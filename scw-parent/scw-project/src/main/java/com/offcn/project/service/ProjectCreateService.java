package com.offcn.project.service;

import com.offcn.project.enums.ProjectStatusEnume;
import com.offcn.project.po.TProject;
import com.offcn.project.po.TReturn;
import com.offcn.project.vo.req.ProjectRedisStorageVo;

import java.util.List;

public interface ProjectCreateService {

    public String initCreateProject(Integer memberId);

    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo project);

}

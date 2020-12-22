package com.offcn.project.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.service.ProjectInfoService;
import com.offcn.project.vo.resp.ProjectDetailVo;
import com.offcn.project.vo.resp.ProjectVo;
import com.offcn.utils.OssTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
@Api(tags="项目基本功能模块（文件上传、项目信息获取等）")
@Slf4j
public class ProjectInfoController {

    @Autowired
    private OssTemplate ossTemplate;

    @Autowired
    private ProjectInfoService projectInfoService;

    @ApiOperation( "图片上传" )
    @PostMapping("/upload")
    public AppResponse<Map<String,Object>> upload(@RequestParam("file") MultipartFile[] files) throws IOException {

        Map<String,Object> map = new HashMap<>(  );
        List list = new ArrayList<>(  );

        if(files != null && files.length >0 ){
            for (MultipartFile file : files) {
                String upload = ossTemplate.upload( file.getInputStream(), file.getOriginalFilename() );
                list.add( upload );
            }

            map.put( "urls", list );
            log.debug( "ossTemplate信息：{},文件上传成功访问路径{}" ,ossTemplate,list);
        }

        return AppResponse.ok( map );
    }

    @ApiOperation( "根据项目id获取回报" )
    @GetMapping("/details/returns/{projectid}")
    public AppResponse<List<TReturn>> getProjectReturns(@PathVariable("projectid") Integer projectid){

        List<TReturn> returns = projectInfoService.getProjectReturns( projectid );
        return AppResponse.ok( returns );
    }

    @ApiOperation( "获取所有项目" )
    @GetMapping("/all")
    public AppResponse<List<ProjectVo>> all(){

       //1 .创建将返回的 vo
        List<ProjectVo> proVo = new ArrayList<>(  );

        //2.查询所有项目
        List<TProject> allProjects = projectInfoService.getAllProjects();
        for (TProject project : allProjects) {

            ProjectVo projectVo = new ProjectVo();
            BeanUtils.copyProperties( project, projectVo );
            //根据项目id查询图片
            List<TProjectImages> projectImages = projectInfoService.getProjectImages( project.getId() );
            for (TProjectImages projectImage : projectImages) {//找头图
                if(projectImage.getImgtype() == ProjectImageTypeEnume.HEADER.getCode()){
                    projectVo.setHeaderImage(  projectImage.getImgurl());
                }
                proVo.add( projectVo );
            }

        }

        return  AppResponse.ok(proVo  );
    }


    @ApiOperation("获取项目信息详情")
    @GetMapping("/details/info/{projectId}")
    public AppResponse<ProjectDetailVo> detailsInfo(@PathVariable("projectId") Integer projectId) {
        TProject projectInfo = projectInfoService.getProjectInfo(projectId);
        ProjectDetailVo projectVo = new ProjectDetailVo();
        // 1、查出这个项目的所有图片
        List<TProjectImages> projectImages = projectInfoService.getProjectImages(projectInfo.getId());
        List<String> detailsImage = projectVo.getDetailsImage();
        if(detailsImage==null){
            detailsImage=new ArrayList<>();
        }
        for (TProjectImages tProjectImages : projectImages) {
            if (tProjectImages.getImgtype() == ProjectImageTypeEnume.HEADER.getCode()) {
                projectVo.setHeaderImage(tProjectImages.getImgurl());
            } else {
                detailsImage.add(tProjectImages.getImgurl());
            }
        }
        projectVo.setDetailsImage(detailsImage);

        // 2、项目的所有支持回报；
        List<TReturn> returns = projectInfoService.getProjectReturns(projectInfo.getId());
        projectVo.setProjectReturns(returns);
        BeanUtils.copyProperties(projectInfo, projectVo);
        return AppResponse.ok(projectVo);
    }

    @ApiOperation("获取系统所有的项目标签")
    @GetMapping("/tags")
    public AppResponse<List<TTag>> tags() {
        List<TTag> tags = projectInfoService.getAllProjectTags();
        return AppResponse.ok(tags);
    }
    @ApiOperation("获取系统所有的项目分类")
    @GetMapping("/types")
    public AppResponse<List<TType>> types() {
        List<TType> types = projectInfoService.getProjectTypes();
        return AppResponse.ok(types);
    }
    @ApiOperation("获取回报信息")
    @GetMapping("/returns/info/{returnId}")
    public AppResponse<TReturn> getTReturn(@PathVariable("returnId") Integer returnId){
        TReturn tReturn = projectInfoService.getReturnInfo(returnId);
        return AppResponse.ok(tReturn);
    }


}

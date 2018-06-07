package com.jeesite.modules.sys.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rabbit on 2018/5/8.
 */

public class activitiEngine {

    private static Logger logger = LoggerFactory.getLogger(activitiEngine.class);
    public static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    //创建流程引擎
    public void createProcessEngineByXmlCfg(){
        ProcessEngineConfiguration pec = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine new_processEngine = pec.buildProcessEngine();
        processEngine = new_processEngine;
    }

    //部署流程引擎
    public static void deployProcessEngine(String resource, String name){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource(resource).name(name);
        builder.deploy();
    }

    public static void deployProcessEngine(String resource){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource(resource).deploy();
    }


    public static ProcessEngine applyProcessEngine(){
        return processEngine;
    }


}

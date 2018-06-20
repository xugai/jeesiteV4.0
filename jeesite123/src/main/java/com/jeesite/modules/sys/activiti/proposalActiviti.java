package com.jeesite.modules.sys.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rabbit on 2018/5/31.
 * 提案审核工作流
 */
public class proposalActiviti {
    private static Logger logger = LoggerFactory.getLogger(proposalActiviti.class);
    private static ProcessEngine processEngine = activitiEngine.applyProcessEngine();
    private static String processInstanceId;


    @Test
    public void deployProcessEngine(){
        activitiEngine.deployProcessEngine("proposalVerify.bpmn", "proposalVerify");
    }

    /**
     *
     * @param empName 提交提案的员工姓名
     * @param boss1   研发部部门经理姓名
     * @param boss2   运营部部门经理姓名
     */
    public static String startProcessEngine(String empName, String boss1, String boss2){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("base", empName);
        map.put("boss1", boss1);
        map.put("boss2", boss2);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProposal_1", map);

        //把当前的第一个任务对象给执行完毕
        Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
        if(task != null){
            logger.info("当前任务：" + task.getName() + "执行完毕！");
            processEngine.getTaskService().complete(task.getId());
        }
        return processInstance.getProcessInstanceId();
    }

   /* @Test
    public void doTask(){
        processEngine.getTaskService().complete("232567");
    }*/

    // TODO: 2018/6/6 新增proId参数，用以标识当前task是哪个流程实例的task，同时在设置变量时把key都改为部门机构的名字
    public static String handleTask(String proId, String officeName, String advice){

        List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(proId).list();
        if(taskList.size() > 0 && taskList != null) {
            for (Task task : taskList) {
                if (task.getName().contains(officeName)) {
                    processEngine.getTaskService().setVariable(task.getId(), officeName, advice);
                    logger.info("当前任务：" + task.getName() + "执行完毕！");
                    processEngine.getTaskService().complete(task.getId());
                    // TODO: 2018/5/31 在数据库t_proposal表中更新提案审核状态为：（officeName）单方审核中，在service层执行此逻辑
                    return queryDecisionTask(proId);
                }
            }
            return null;
        }
        return null;
    }

    private static String queryDecisionTask(String proId){
        List<Task> taskList = processEngine.getTaskService().createTaskQuery().processInstanceId(proId).list();
        Map<String, Object> params = new HashMap<String, Object>();
        if(taskList != null && (taskList.size() == 1)){
            if(taskList.get(0).getName().contains("决策")){
                    // TODO: 2018/6/1 判断两个部门的意见，然后以此决策，向后面的任务传递决策信息。6.1happy
                    String flag1 = (String) processEngine.getTaskService().getVariable(taskList.get(0).getId(), "研发部");
                    String flag2 = (String) processEngine.getTaskService().getVariable(taskList.get(0).getId(), "运营部");
                    if(StringUtils.isBlank(flag1) || StringUtils.isBlank(flag2)){
                        System.out.println("部门审核提案发生错误！");
                        return null;
                    }
                    if("true".equals(flag1) && "true".equals(flag2)){
                        params.put("result", "pass");
                    }else{
                        params.put("result", "disagree");
                    }
                    processEngine.getTaskService().complete(taskList.get(0).getId(), params);
                    return finalDecision(proId);
            }else {
                logger.info("当前任务不存在决策行为！");
                return null;
            }
        }else{
            logger.info("当前无活跃任务！");
            return null;
        }
    }

    private static String finalDecision(String proId) {
        List<Execution> executionList = processEngine.
                                        getRuntimeService().
                                        createExecutionQuery().
                                        processInstanceId(proId).list();
        if (executionList.size() == 1) {
            HistoricActivityInstance historicActivityInstance = processEngine.
                                                                getHistoryService().
                                                                createHistoricActivityInstanceQuery().
                                                                executionId(executionList.get(0).getId()).
                                                                activityType("receiveTask").
                                                                singleResult();
            if (historicActivityInstance != null) {
                logger.info("当前等待活动执行完成！");
                processEngine.getRuntimeService().signal(executionList.get(0).getId());
                return historicActivityInstance.getActivityName();
            }else{
                logger.info("当前不存在等待活动！");
                return null;
            }
        }else{
            logger.info("当前不存在等待活动！");
            return null;
        }
    }
}

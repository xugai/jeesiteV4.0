package com.jeesite.modules.sys.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rabbit on 2018/5/31.
 * 请假工作流
 */
public class vacateActiviti {
    //开始执行流程引擎

    private static Logger logger = LoggerFactory.getLogger(vacateActiviti.class);
    private static ProcessEngine processEngine = activitiEngine.applyProcessEngine();

    public static boolean startProcessEngine(String empName, String manName, int days, String reason){
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("base", empName);
        map.put("boss", manName);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("restApply",map);
        logger.info("流程执行对象的id：{} ", processInstance.getId());
        logger.info("流程执行实例的id：{} ", processInstance.getProcessInstanceId());
        logger.info("流程定义的id：{} ", processInstance.getProcessDefinitionId());
        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .singleResult();
        runtimeService.setVariable(execution.getId(),"days", days);
        runtimeService.setVariable(execution.getId(),"reason", reason);
        //让工作流执行处理当前活跃的任务对象
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().list();
        if(taskList != null && taskList.size() > 0){
            Task task = taskList.get(taskList.size() - 1);
            handleTask(task.getId());
            return true;
        }
        return false;
    }

    /**
     * 通过执行人的名字，查询当前执行人有多少任务需要着手处理
     * @param name
     * @return
     * @author BeHe
     * @date 2018/5/12
     */
    public static List<Task> queryTask(String name){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> taskList = taskQuery.taskAssignee(name).list();
        if(taskList != null && taskList.size() > 0){
            return taskList;
        }else{
            return null;
        }
    }

    public static boolean handleTask(String taskId){
        try{
            processEngine.getTaskService().complete(taskId);
            logger.info("当前任务对象执行成功！");
            return true;
        }catch(Exception ex){
            logger.info("执行当前任务对象发生异常！");
            return false;
        }
    }

    public static String queryTaskId(String empName){
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<HistoricTaskInstance> lists =  processEngine.getHistoryService().
                createHistoricTaskInstanceQuery().
                taskAssignee(empName).list();
        Task latestTask = taskQuery.executionId(lists.get(lists.size() - 1).getExecutionId()).singleResult();
        return latestTask.getId();
    }

    /*
    public void hasNextActivity(){
        ProcessInstance processInstance = processEngine.getRuntimeService()
                                            .createProcessInstanceQuery()
                                            .processInstanceId("65001")
                                            .singleResult();
        if(processInstance == null){
            System.out.println("当前流程实例已执行完成！");
        }else{
            System.out.println("processInstance.getId()----> " + processInstance.getId());
            System.out.println("processInstance.getProcessInstanceId----> " + processInstance.getProcessInstanceId());
        }
    }
    */
}

package com.jeesite.modules.sys.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ExpenseActivity {

	private static ProcessEngine processEngine = activitiEngine.applyProcessEngine();
    @Test
    public void deployProcessEngine(){
        activitiEngine.deployProcessEngine("expense.bpmn", "expense_1");
    }
    
    @Test
    public void startProcessEngine(){
    	 ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    	 ProcessInstance processInstance = processEngine.getRuntimeService()
    			.startProcessInstanceByKey("expense");
    	List<Task> taskList = processEngine.getTaskService()
    			.createTaskQuery()
    			.processDefinitionId(processInstance.getProcessDefinitionId())
    			.list();
    	for(Task task:taskList){
    		System.out.println(task.getId());
    	}
    }
   
}

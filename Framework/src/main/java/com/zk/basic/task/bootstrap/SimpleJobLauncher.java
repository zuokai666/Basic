package com.zk.basic.task.bootstrap;

import java.util.ArrayList;
import java.util.List;

public class SimpleJobLauncher {
	
	private List<Step> steps;
	
	public void run(){
		for(int i=0;i<steps.size();i++){
			steps.get(i).execute();
		}
	}
	
	public void addStep(Step step){
		if(steps == null){
			steps = new ArrayList<>();
		}
		steps.add(step);
	}
}
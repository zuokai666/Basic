package kb.other.serial;

import java.util.concurrent.Callable;

public class SleepTask implements Callable<Boolean>{
	
	private int timeMills;
	
	public SleepTask(int timeMills) {
		this.timeMills = timeMills;
	}
	
	@Override
	public Boolean call() throws Exception {
		System.out.println("开始睡觉");
		try {
			Thread.sleep(timeMills);
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
		System.out.println("睡觉结束");
		return Boolean.TRUE;
	}
}
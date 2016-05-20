package com.ckn.practice;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.time.DateUtils;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年5月16日-下午6:12:15
 */
public class TaskTimeoutDemo {
	
	public static void main(String[] args) throws ParseException {
		System.out.println("Start.....");
		
		/*ExecutorService exec=Executors.newCachedThreadPool();
		testTask(exec,5);
		//testTask(exec,15);
		exec.shutdown();*/
		Date date=DateUtils.parseDate("2016-05-17 90400", new String[]{"yyyy-MM-dd hhmmss"});
		System.out.println(date.toLocaleString());
		
		System.out.println("End!");
	}
	
	public static void testTask(ExecutorService exec, int timeout){
		MyTask task=new MyTask();
		Future<Boolean> future=exec.submit(task);
		
		Boolean taskResult=null;
		String failReason=null;
		try{
			taskResult=future.get(timeout, TimeUnit.SECONDS);
		}catch(InterruptedException e){
			failReason="主线程在等待计算结果是被中断!";
		}catch(ExecutionException e){
			failReason="主线程等待计算结果，但计算抛出异常!";
		}catch(TimeoutException e){
			failReason="主线程等待计算结果超时，因此在主线程中中断任务线程!";
			exec.shutdownNow();
		}
		
		System.out.println("taskResult:"+taskResult);	
		System.out.println("failReason:"+failReason);
	}

}


class MyTask implements Callable<Boolean> {

	/**/
	public Boolean call() throws Exception {
		for(int i=0;i<100L;i++){
			Thread.sleep(100);
			System.out.println("-");
		}
		return Boolean.TRUE;
	}
	
}




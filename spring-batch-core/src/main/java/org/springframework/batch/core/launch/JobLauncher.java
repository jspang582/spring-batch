/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.core.launch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * 基于不同的运行时标识符控制作业的简单接口，包括可能的特别执行。
 * 非常重要的一点是，这个接口绝对不能保证对它的调用是同步执行还是异步执行。
 * 应该检查特定实现的javadocs，以确保调用方完全理解作业将如何运行。
 *
 * Simple interface for controlling jobs, including possible ad-hoc executions,
 * based on different runtime identifiers. It is extremely important to note
 * that this interface makes absolutely no guarantees about whether or not calls
 * to it are executed synchronously or asynchronously. The javadocs for specific
 * implementations should be checked to ensure callers fully understand how the
 * job will be run.
 * 
 * @author Lucas Ward
 * @author Dave Syer
 */

public interface JobLauncher {

	/**
	 * 为给定的job和JobParameters启动一个作业执行。
	 * 如果JobExecution能够成功创建，那么这个方法将始终返回它，无论执行是否成功。
	 * 如果有一个过去的JobExecution已经暂停，返回相同的JobExecution，而不是一个新的创建的JobExecution。
	 * 只有在作业启动失败时才会引发异常。如果作业在处理过程中遇到一些错误，将返回JobExecution，并且需要检查状态。
	 *
	 * Start a job execution for the given {@link Job} and {@link JobParameters}
	 * . If a {@link JobExecution} was able to be created successfully, it will
	 * always be returned by this method, regardless of whether or not the
	 * execution was successful. If there is a past {@link JobExecution} which
	 * has paused, the same {@link JobExecution} is returned instead of a new
	 * one created. A exception will only be thrown if there is a failure to
	 * start the job. If the job encounters some error while processing, the
	 * JobExecution will be returned, and the status will need to be inspected.
	 *
	 * @param job the job to be executed.
	 * @param jobParameters the parameters passed to this execution of the job.
	 * @return the {@link JobExecution} if it returns synchronously. If the
	 * implementation is asynchronous, the status might well be unknown.
	 * 
	 * @throws JobExecutionAlreadyRunningException if the JobInstance identified
	 * by the properties already has an execution running.
	 * @throws IllegalArgumentException if the job or jobInstanceProperties are
	 * null.
	 * @throws JobRestartException if the job has been run before and
	 * circumstances that preclude a re-start.
	 * @throws JobInstanceAlreadyCompleteException if the job has been run
	 * before with the same parameters and completed successfully
	 * @throws JobParametersInvalidException if the parameters are not valid for
	 * this job
	 */
	public JobExecution run(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException,
			JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException;

}

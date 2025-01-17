/*
 * Copyright 2006-2013 the original author or authors.
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
package org.springframework.batch.core;

/**
 * 在作业生命周期的特定点提供回调。
 * 如果它们小心地确保线程安全，或者在每个作业使用一个侦听器实例(假设作业实例本身不被多个线程使用)，则实现可以是有状态的。
 *
 * Provide callbacks at specific points in the lifecycle of a {@link Job}.
 * Implementations can be stateful if they are careful to either ensure thread
 * safety, or to use one instance of a listener per job, assuming that job
 * instances themselves are not used by more than one thread.
 *
 * @author Dave Syer
 *
 */
public interface JobExecutionListener {

	/**
	 * 在作业执行之前回调。
	 *
	 * Callback before a job executes.
	 *
	 * @param jobExecution the current {@link JobExecution}
	 */
	void beforeJob(JobExecution jobExecution);

	/**
	 * 任务完成后回调。在成功执行和失败执行之后调用。要对特定状态执行逻辑，请使用"if (jobExecution.getStatus() == BatchStatus.X)"。
	 *
	 * Callback after completion of a job. Called after both both successful and
	 * failed executions. To perform logic on a particular status, use
	 * "if (jobExecution.getStatus() == BatchStatus.X)".
	 *
	 * @param jobExecution the current {@link JobExecution}
	 */
	void afterJob(JobExecution jobExecution);

}

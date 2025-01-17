/*
 * Copyright 2006-2018 the original author or authors.
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

import org.springframework.lang.Nullable;

/**
 * 表示作业的批处理域对象。作业是一个明确的抽象，表示由开发人员指定的作业的配置。需要注意的是，重新启动策略应用于整个作业，而不是一个步骤。
 *
 * Batch domain object representing a job. Job is an explicit abstraction
 * representing the configuration of a job specified by a developer. It should
 * be noted that restart policy is applied to the job as a whole and not to a
 * step.
 * 
 * @author Dave Syer
 * @author Mahmoud Ben Hassine
 */
public interface Job {

	String getName();

	/**
	 * 表示该作业至少在原则上是否可以重新启动的标志。
	 *
	 * Flag to indicate if this job can be restarted, at least in principle.
	 * 
	 * @return true if this job can be restarted after a failure
	 */
	boolean isRestartable();

	/**
	 * 运行JobExecution并根据需要更新元信息，如状态和统计信息。该方法执行失败时不应抛出任何异常。客户端应该仔细检查JobExecution状态，以确定成功或失败。
	 *
	 * Run the {@link JobExecution} and update the meta information like status
	 * and statistics as necessary. This method should not throw any exceptions
	 * for failed execution. Clients should be careful to inspect the
	 * {@link JobExecution} status to determine success or failure.
	 * 
	 * @param execution a {@link JobExecution}
	 */
	void execute(JobExecution execution);

	/**
	 * 如果客户端需要为序列中的下一次执行生成新参数，他们可以使用这个递增器。如果这个作业没有一个自然的顺序，返回值可能是null。
	 *
	 * If clients need to generate new parameters for the next execution in a
	 * sequence they can use this incrementer. The return value may be {@code null},
	 * in the case that this job does not have a natural sequence.
	 * 
	 * @return in incrementer to be used for creating new parameters
	 */
	@Nullable
	JobParametersIncrementer getJobParametersIncrementer();

	/**
	 * JobExecution作业参数的验证器。Job的客户端可能需要在执行之前或执行期间验证启动的参数。
	 *
	 * A validator for the job parameters of a {@link JobExecution}. Clients of
	 * a Job may need to validate the parameters for a launch, before or during
	 * the execution.
	 * 
	 * @return a validator that can be used to check parameter values (never
	 * {@code null})
	 */
	JobParametersValidator getJobParametersValidator();

}

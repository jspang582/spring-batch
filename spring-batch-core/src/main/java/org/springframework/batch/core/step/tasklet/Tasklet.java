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
package org.springframework.batch.core.step.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.lang.Nullable;

/**
 * 步骤处理的策略。
 *
 * Strategy for processing in a step.
 * 
 * @author Dave Syer
 * @author Mahmoud Ben Hassine
 * 
 */
public interface Tasklet {

	/**
	 * 以步骤贡献的形式给出当前上下文，执行任何必要的操作来在事务中处理此单元。
	 * 实现返回RepeatStatus。如果完成，实现者需返回RepeatStatus.FINISHED。如果不是，则返回RepeatStatus.CONTINUABLE。失败时抛出异常。
	 *
	 * Given the current context in the form of a step contribution, do whatever
	 * is necessary to process this unit inside a transaction. Implementations
	 * return {@link RepeatStatus#FINISHED} if finished. If not they return
	 * {@link RepeatStatus#CONTINUABLE}. On failure throws an exception.
	 * 
	 * @param contribution mutable state to be passed back to update the current
	 * step execution
	 * @param chunkContext attributes shared between invocations but not between
	 * restarts
	 * @return an {@link RepeatStatus} indicating whether processing is
	 * continuable. Returning {@code null} is interpreted as {@link RepeatStatus#FINISHED}
	 *
	 * @throws Exception thrown if error occurs during execution.
	 */
	@Nullable
	RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception;

}

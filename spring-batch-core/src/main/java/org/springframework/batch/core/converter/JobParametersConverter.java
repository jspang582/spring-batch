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

package org.springframework.batch.core.converter;

import java.util.Properties;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.lang.Nullable;

/**
 * JobParameters实例的工厂。作业可以使用许多可能的运行时参数来执行，这些参数标识了作业的实例。此转换器允许作业参数与属性之间进行转换。
 *
 * A factory for {@link JobParameters} instances. A job can be executed with
 * many possible runtime parameters, which identify the instance of the job.
 * This converter allows job parameters to be converted to and from Properties.
 * 
 * @author Dave Syer
 * @author Mahmoud Ben Hassine
 * 
 * @see JobParametersBuilder
 * 
 */
public interface JobParametersConverter {

	/**
	 * 获取一个新的JobParameters实例。如果给定null或空属性，将返回空JobParameters。
	 *
	 * Get a new {@link JobParameters} instance. If given null, or an empty
	 * properties, an empty JobParameters will be returned.
	 * 
	 * @param properties the runtime parameters in the form of String literals.
	 * @return a {@link JobParameters} properties converted to the correct
	 * types.
	 */
	JobParameters getJobParameters(@Nullable Properties properties);

	/**
	 * 反向操作:获取一个Properties实例。如果给出null或空的JobParameters，则应该返回空的Properties。
	 *
	 * The inverse operation: get a {@link Properties} instance. If given null
	 * or empty JobParameters, an empty Properties should be returned.
	 * 
	 * @param params the {@link JobParameters} instance to be converted.
	 * @return a representation of the parameters as properties
	 */
	Properties getProperties(@Nullable JobParameters params);
}

/*
 * Copyright 2006-2019 the original author or authors.
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

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.util.Assert;

/**
 * 用于创建JobParameters的Helper类。
 * 有用是因为所有JobParameter对象都是不可变的，必须单独实例化以确保类型安全。
 * 一旦创建，它就可以在java.lang.StringBuilder中使用(除了顺序无关)，添加各种参数类型并在完成后创建有效的JobParameters。
 *
 * 使用标识标志指示是否将该参数用于JobInstance的标识。该标志默认为true。
 *
 * Helper class for creating {@link JobParameters}. Useful because all
 * {@link JobParameter} objects are immutable, and must be instantiated separately
 * to ensure type safety. Once created, it can be used in the
 * same was a java.lang.StringBuilder (except, order is irrelevant), by adding
 * various parameter types and creating a valid {@link JobParameters} once
 * finished.<br>
 * <br>
 * Using the identifying flag indicates if the parameter will be used
 * in the identification of a JobInstance.  That flag defaults to true.
 *
 * @author Lucas Ward
 * @author Michael Minella
 * @author Glenn Renfro
 * @author Mahmoud Ben Hassine
 * @since 1.0
 * @see JobParameters
 * @see JobParameter
 */
public class JobParametersBuilder {

	private Map<String, JobParameter> parameterMap;

	private JobExplorer jobExplorer;

	/**
	 * Default constructor. Initializes the builder with empty parameters.
	 */
	public JobParametersBuilder() {
		this.parameterMap = new LinkedHashMap<>();
	}

	/**
	 * @param jobExplorer {@link JobExplorer} used for looking up previous job parameter information
	 */
	public JobParametersBuilder(JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
		this.parameterMap = new LinkedHashMap<>();
	}

	/**
	 * Copy constructor. Initializes the builder with the supplied parameters.
	 * @param jobParameters {@link JobParameters} instance used to initialize the builder.
	 */
	public JobParametersBuilder(JobParameters jobParameters) {
		this(jobParameters, null);
	}

	/**
	 * Constructor to add conversion capabilities to support JSR-352.  Per the spec, it is expected that all
	 * keys and values in the provided {@link Properties} instance are Strings
	 *
	 * @param properties the job parameters to be used
	 */
	public JobParametersBuilder(Properties properties) {
		this.parameterMap = new LinkedHashMap<>();

		if(properties != null) {
			for (Map.Entry<Object, Object> curProperty : properties.entrySet()) {
				this.parameterMap.put((String) curProperty.getKey(), new JobParameter((String) curProperty.getValue(), false));
			}
		}
	}

	/**
	 * Copy constructor. Initializes the builder with the supplied parameters.
	 * @param jobParameters {@link JobParameters} instance used to initialize the builder.
	 * @param jobExplorer {@link JobExplorer} used for looking up previous job parameter information
	 */
	public JobParametersBuilder(JobParameters jobParameters, JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
		this.parameterMap = new LinkedHashMap<>(jobParameters.getParameters());
	}

	/**
	 * Add a new identifying String parameter for the given key.
	 *
	 * @param key - parameter accessor.
	 * @param parameter - runtime parameter
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addString(String key, String parameter) {
		this.parameterMap.put(key, new JobParameter(parameter, true));
		return this;
	}

	/**
	 * Add a new String parameter for the given key.
	 *
	 * @param key - parameter accessor.
	 * @param parameter - runtime parameter
	 * @param identifying - indicates if the parameter is used as part of identifying a job instance
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addString(String key, String parameter, boolean identifying) {
		this.parameterMap.put(key, new JobParameter(parameter, identifying));
		return this;
	}

	/**
	 * Add a new identifying {@link Date} parameter for the given key.
	 *
	 * @param key - parameter accessor.
	 * @param parameter - runtime parameter
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addDate(String key, Date parameter) {
		this.parameterMap.put(key, new JobParameter(parameter, true));
		return this;
	}

	/**
	 * Add a new {@link Date} parameter for the given key.
	 *
	 * @param key - parameter accessor.
	 * @param parameter - runtime parameter
	 * @param identifying - indicates if the parameter is used as part of identifying a job instance
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addDate(String key, Date parameter, boolean identifying) {
		this.parameterMap.put(key, new JobParameter(parameter, identifying));
		return this;
	}

	/**
	 * Add a new identifying Long parameter for the given key.
	 *
	 * @param key - parameter accessor.
	 * @param parameter - runtime parameter
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addLong(String key, Long parameter) {
		this.parameterMap.put(key, new JobParameter(parameter, true));
		return this;
	}

	/**
	 * Add a new Long parameter for the given key.
	 *
	 * @param key - parameter accessor.
	 * @param parameter - runtime parameter
	 * @param identifying - indicates if the parameter is used as part of identifying a job instance
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addLong(String key, Long parameter, boolean identifying) {
		this.parameterMap.put(key, new JobParameter(parameter, identifying));
		return this;
	}

	/**
	 * Add a new identifying Double parameter for the given key.
	 *
	 * @param key - parameter accessor.
	 * @param parameter - runtime parameter
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addDouble(String key, Double parameter) {
		this.parameterMap.put(key, new JobParameter(parameter, true));
		return this;
	}

	/**
	 * Add a new Double parameter for the given key.
	 *
	 * @param key - parameter accessor.
	 * @param parameter - runtime parameter
	 * @param identifying - indicates if the parameter is used as part of identifying a job instance
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addDouble(String key, Double parameter, boolean identifying) {
		this.parameterMap.put(key, new JobParameter(parameter, identifying));
		return this;
	}

	/**
	 * Conversion method that takes the current state of this builder and
	 * returns it as a JobParameters object.
	 *
	 * @return a valid {@link JobParameters} object.
	 */
	public JobParameters toJobParameters() {
		return new JobParameters(this.parameterMap);
	}

	/**
	 * Add a new {@link JobParameter} for the given key.
	 *
	 * @param key - parameter accessor
	 * @param jobParameter - runtime parameter
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addParameter(String key, JobParameter jobParameter) {
		Assert.notNull(jobParameter, "JobParameter must not be null");
		this.parameterMap.put(key, jobParameter);
		return this;
	}

	/**
	 * Copy job parameters into the current state.
	 * @param jobParameters parameters to copy in
	 * @return a reference to this object.
	 */
	public JobParametersBuilder addJobParameters(JobParameters jobParameters) {
		Assert.notNull(jobParameters, "jobParameters must not be null");

		this.parameterMap.putAll(jobParameters.getParameters());

		return this;
	}

	/**
	 * Initializes the {@link JobParameters} based on the state of the {@link Job}.  This
	 * should be called after all parameters have been entered into the builder.
	 * All parameters already set on this builder instance will be appended to
	 * those retrieved from the job incrementer, overriding any with the same key (Same
	 * behaviour as {@link org.springframework.batch.core.launch.support.CommandLineJobRunner}
	 * with "-next" option and {@link org.springframework.batch.core.launch.JobOperator#startNextInstance(String)})
	 *
	 * @param job the job for which the {@link JobParameters} are being constructed.
	 * @return a reference to this object.
	 *
	 * @since 4.0
	 */
	public JobParametersBuilder getNextJobParameters(Job job) {
		Assert.state(this.jobExplorer != null, "A JobExplorer is required to get next job parameters");
		Assert.notNull(job, "Job must not be null");
		Assert.notNull(job.getJobParametersIncrementer(), "No job parameters incrementer found for job=" + job.getName());

		String name = job.getName();
		JobParameters nextParameters;
		JobInstance lastInstance = this.jobExplorer.getLastJobInstance(name);
		JobParametersIncrementer incrementer = job.getJobParametersIncrementer();
		if (lastInstance == null) {
			// Start from a completely clean sheet
			nextParameters = incrementer.getNext(new JobParameters());
		}
		else {
			JobExecution previousExecution = this.jobExplorer.getLastJobExecution(lastInstance);
			if (previousExecution == null) {
				// Normally this will not happen - an instance exists with no executions
				nextParameters = incrementer.getNext(new JobParameters());
			}
			else {
				nextParameters = incrementer.getNext(previousExecution.getJobParameters());
			}
		}

		// start with parameters from the incrementer
		Map<String, JobParameter> nextParametersMap = new HashMap<>(nextParameters.getParameters());
		// append new parameters (overriding those with the same key)
		nextParametersMap.putAll(this.parameterMap);
		this.parameterMap = nextParametersMap;
		return this;
	}
}

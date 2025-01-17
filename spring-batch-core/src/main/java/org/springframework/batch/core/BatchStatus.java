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

/**
 * 表示“执行”状态的枚举。
 *
 * Enumeration representing the status of an Execution.
 * 
 * @author Lucas Ward
 * @author Dave Syer
 * @author Michael Minella
 * @author Mahmoud Ben Hassine
 */
public enum BatchStatus {

	/**
	 * 状态值的顺序非常重要，因为它可以用于聚合一组状态值—结果应该是最大值。
	 * 因为COMPLETED在顺序中是第一位的，只有当执行的所有元素都是COMPLETED时，聚合状态才会是COMPLETED。
	 * 一个正在运行的执行预计将从STARTING移动到STARTED再到COMPLETED(通过upgradeTo(BatchStatus)定义的顺序)。
	 * 高于STARTED的值表示更严重的故障。ABANDONED用于已经完成处理但没有成功的步骤，并且在重新启动时应该跳过这些步骤(因此FAILED是错误的状态)。
	 *
	 * The order of the status values is significant because it can be used to
	 * aggregate a set of status values - the result should be the maximum
	 * value. Since COMPLETED is first in the order, only if all elements of an
	 * execution are COMPLETED will the aggregate status be COMPLETED. A running
	 * execution is expected to move from STARTING to STARTED to COMPLETED
	 * (through the order defined by {@link #upgradeTo(BatchStatus)}). Higher
	 * values than STARTED signify more serious failure. ABANDONED is used for
	 * steps that have finished processing, but were not successful, and where
	 * they should be skipped on a restart (so FAILED is the wrong status).
	 */
	COMPLETED, STARTING, STARTED, STOPPING, STOPPED, FAILED, ABANDONED, UNKNOWN;

	public static BatchStatus max(BatchStatus status1, BatchStatus status2) {
		return status1.isGreaterThan(status2) ? status1 : status2;
	}

	/**
	 * 决定一个状态是否表明工作正在进行。
	 *
	 * Convenience method to decide if a status indicates work is in progress.
	 * 
	 * @return true if the status is STARTING, STARTED
	 */
	public boolean isRunning() {
		return this == STARTING || this == STARTED;
	}

	/**
	 * 决定状态是否表明执行失败。
	 *
	 * Convenience method to decide if a status indicates execution was
	 * unsuccessful.
	 * 
	 * @return true if the status is FAILED or greater
	 */
	public boolean isUnsuccessful() {
		return this == FAILED || this.isGreaterThan(FAILED);
	}

	/**
	 * 用于在其逻辑进程中移动状态值，并用较严重的故障覆盖较轻的故障。
	 * 该值将与参数进行比较，并返回优先级更高的值。
	 * 如果两者都是STARTED或小于，则返回值为start, STARTED, COMPLETED序列中最大的。否则返回的值是两者中的最大值。
	 *
	 * Method used to move status values through their logical progression, and
	 * override less severe failures with more severe ones. This value is
	 * compared with the parameter and the one that has higher priority is
	 * returned. If both are STARTED or less than the value returned is the
	 * largest in the sequence STARTING, STARTED, COMPLETED. Otherwise the value
	 * returned is the maximum of the two.
	 * 
	 * @param other another status to compare to
	 * @return either this or the other status depending on their priority
	 */
	public BatchStatus upgradeTo(BatchStatus other) {
		if (isGreaterThan(STARTED) || other.isGreaterThan(STARTED)) {
			return max(this, other);
		}
		// Both less than or equal to STARTED
		if (this == COMPLETED || other == COMPLETED) {
			return COMPLETED;
		}
		return max(this, other);
	}

	/**
	 * @param other a status value to compare
	 * @return true if this is greater than other
	 */
	public boolean isGreaterThan(BatchStatus other) {
		return this.compareTo(other) > 0;
	}

	/**
	 * @param other a status value to compare
	 * @return true if this is less than other
	 */
	public boolean isLessThan(BatchStatus other) {
		return this.compareTo(other) < 0;
	}

	/**
	 * @param other a status value to compare
	 * @return true if this is less than other
	 */
	public boolean isLessThanOrEqualTo(BatchStatus other) {
		return this.compareTo(other) <= 0;
	}

	/**
	 * Converts the current status to the JSR-352 equivalent
	 * 
	 * @return JSR-352 equivalent to the current status
	 */
	public javax.batch.runtime.BatchStatus getBatchStatus() {
		if(this == ABANDONED) {
			return javax.batch.runtime.BatchStatus.ABANDONED;
		} else if(this == COMPLETED) {
			return javax.batch.runtime.BatchStatus.COMPLETED;
		} else if(this == STARTED) {
			return javax.batch.runtime.BatchStatus.STARTED;
		} else if(this == STARTING) {
			return javax.batch.runtime.BatchStatus.STARTING;
		} else if(this == STOPPED) {
			return javax.batch.runtime.BatchStatus.STOPPED;
		} else if(this == STOPPING) {
			return javax.batch.runtime.BatchStatus.STOPPING;
		} else {
			return javax.batch.runtime.BatchStatus.FAILED;
		}
	}

	/**
	 * 查找与给定值开头匹配的BatchStatus。如果没有找到匹配，则返回COMPLETED作为默认值，因为has优先级较低。
	 *
	 * Find a BatchStatus that matches the beginning of the given value. If no
	 * match is found, return COMPLETED as the default because has is low
	 * precedence.
	 * 
	 * @param value a string representing a status
	 * @return a BatchStatus
	 */
	public static BatchStatus match(String value) {
		for (BatchStatus status : values()) {
			if (value.startsWith(status.toString())) {
				return status;
			}
		}
		// Default match should be the lowest priority
		return COMPLETED;
	}
}

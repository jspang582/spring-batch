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

package org.springframework.batch.item;

import org.springframework.lang.Nullable;

/**
 *
 * 提供数据的策略接口。
 *
 * 实现应该是有状态的，并且对每个批处理将调用多次，每次调用read()都会返回不同的值，最后在耗尽所有输入数据时返回null。
 *
 * 实现不需要是线程安全的，ItemReader的客户端需要意识到这一点。
 * 更丰富的接口(例如提前查看或窥视)是不可行的，因为我们需要在异步批处理中支持事务。
 *
 * Strategy interface for providing the data. <br>
 * 
 * Implementations are expected to be stateful and will be called multiple times
 * for each batch, with each call to {@link #read()} returning a different value
 * and finally returning <code>null</code> when all input data is exhausted.<br>
 * 
 * Implementations need <b>not</b> be thread-safe and clients of a {@link ItemReader}
 * need to be aware that this is the case.<br>
 * 
 * A richer interface (e.g. with a look ahead or peek) is not feasible because
 * we need to support transactions in an asynchronous batch.
 * 
 * @author Rob Harrop
 * @author Dave Syer
 * @author Lucas Ward
 * @author Mahmoud Ben Hassine
 * @since 1.0
 */
public interface ItemReader<T> {

	/**
	 * 读取一段输入数据并前进到下一段。实现必须在输入数据集的末尾返回null。
	 * 在事务设置中，如果第一个调用位于回滚的事务中，调用方可能会从连续的调用(或以其他方式)中两次获得相同的项。
	 *
	 * Reads a piece of input data and advance to the next one. Implementations
	 * <strong>must</strong> return <code>null</code> at the end of the input
	 * data set. In a transactional setting, caller might get the same item
	 * twice from successive calls (or otherwise), if the first call was in a
	 * transaction that rolled back.
	 * 
	 * @throws ParseException if there is a problem parsing the current record
	 * (but the next one may still be valid)
	 * @throws NonTransientResourceException if there is a fatal exception in
	 * the underlying resource. After throwing this exception implementations
	 * should endeavour to return null from subsequent calls to read.
	 * @throws UnexpectedInputException if there is an uncategorised problem
	 * with the input data. Assume potentially transient, so subsequent calls to
	 * read might succeed.
	 * @throws Exception if an there is a non-specific error.
	 * @return T the item to be processed or {@code null} if the data source is
	 * exhausted
	 */
	@Nullable
	T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException;

}

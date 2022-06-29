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
package org.springframework.batch.core;

/**
 * 用于监听被跳过items的接口。
 * 回调函数将由Step实现在Step生命周期中的适当时间调用。
 * 此接口的实现者不应假定在遇到错误后会立即调用任何方法。
 * 因为稍后在处理块时可能会出现错误，所以直到提交之前才会调用此侦听器。
 *
 * Interface for listener to skipped items. Callbacks will be called by
 * {@link Step} implementations at the appropriate time in the step lifecycle.
 * Implementers of this interface should not assume that any method will be 
 * called immediately after an error has been encountered.  Because there 
 * may be errors later on in processing the chunk, this listener will not be
 * called until just before committing.
 * 
 * @author Dave Syer
 * @author Robert Kasanicky
 * 
 */
public interface SkipListener<T,S> extends StepListener {

	/**
	 * 在读取失败时的合法回调，因此不会被重新抛出。
	 * 如果事务回滚并重新读取项，则由于相同的原因，此回调将重复发生。这只会发生在读项没有被缓冲的情况下。
	 *
	 * Callback for a failure on read that is legal, so is not going to be
	 * re-thrown. In case transaction is rolled back and items are re-read, this
	 * callback will occur repeatedly for the same cause.  This will only happen
	 * if read items are not buffered.
	 * 
	 * @param t cause of the failure
	 */
	void onSkipInRead(Throwable t);

	/**
	 * 对于给定的异常，该项在写入时失败，因此调用了跳过操作。
	 *
	 * This item failed on write with the given exception, and a skip was called
	 * for. 
	 * 
	 * @param item the failed item
	 * @param t the cause of the failure
	 */
	void onSkipInWrite(S item, Throwable t);

	/**
	 * 该项在处理给定异常时失败，需要跳过。
	 *
	 * This item failed on processing with the given exception, and a skip was called
	 * for. 
	 * 
	 * @param item the failed item
	 * @param t the cause of the failure
	 */
	void onSkipInProcess(T item, Throwable t);

}

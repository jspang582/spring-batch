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

import java.util.List;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemWriter;

/**
 * 用于items写入时的侦听器接口。在write items时，在抛出任何异常之前、之后以及在抛出异常时，都将通知该接口的实现。、
 *
 * 注意:这个监听器被设计为围绕一个项目的生命周期工作。
 * 这意味着每个方法都应该在项目的生命周期中调用一次，在容错场景中，在这些方法中完成的任何事务工作都将回滚，而不会重新应用。因此，建议不要使用参与事务的侦听器执行任何逻辑。
 *
 * <p>
 * Listener interface for the writing of items.  Implementations
 * of this interface will be notified before, after, and in case
 * of any exception thrown while writing a list of items.
 * </p>
 *
 * <p>
 * <em>Note: </em> This listener is designed to work around the
 * lifecycle of an item.  This means that each method should be
 * called once within the lifecycle of an item and in fault
 * tolerant scenarios, any transactional work that is done in
 * one of these methods would be rolled back and not re-applied.
 * Because of this, it is recommended to not perform any logic
 * using this listener that participates in a transaction.
 *</p>
 *
 * @author Lucas Ward
 *
 */
public interface ItemWriteListener<S> extends StepListener {

	/**
	 * 在ItemWriter.write前调用。
	 *
	 * Called before {@link ItemWriter#write(java.util.List)}
	 *
	 * @param items to be written
	 */
	void beforeWrite(List<? extends S> items);

	/**
	 * 在ItemWriter.write()后调用。
	 * 这将在任何事务提交之前，在ChunkListener.afterChunk(ChunkContext)之前调用。
	 *
	 * Called after {@link ItemWriter#write(java.util.List)} This will be
	 * called before any transaction is committed, and before
	 * {@link ChunkListener#afterChunk(ChunkContext)}
	 *
	 * @param items written items
	 */
	void afterWrite(List<? extends S> items);

	/**
	 * 如果在尝试写入时发生错误，则调用。将在事务内部调用，但事务通常会回滚。没有办法从这个回调中确定是哪个items(如果有的话)导致了错误。
	 *
	 * Called if an error occurs while trying to write. Will be called inside a
	 * transaction, but the transaction will normally be rolled back. There is
	 * no way to identify from this callback which of the items (if any) caused
	 * the error.
	 *
	 * @param exception thrown from {@link ItemWriter}
	 * @param items attempted to be written.
	 */
	void onWriteError(Exception exception, List<? extends S> items);
}

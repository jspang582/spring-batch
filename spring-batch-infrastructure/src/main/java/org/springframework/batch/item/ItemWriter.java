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

package org.springframework.batch.item;

import java.util.List;

/**
 * 通用输出操作的基本接口。实现此接口的类将负责在必要时序列化对象。通常，实现类的职责是决定使用哪种技术进行映射以及应该如何配置它。
 *
 * write方法负责确保所有内部缓冲区都被刷新。如果事务是活动的，那么在后续回滚时通常也需要丢弃输出。写入器向其发送数据的资源本身通常应该能够处理这个问题。
 *
 * <p>
 * Basic interface for generic output operations. Class implementing this
 * interface will be responsible for serializing objects as necessary.
 * Generally, it is responsibility of implementing class to decide which
 * technology to use for mapping and how it should be configured.
 * </p>
 * 
 * <p>
 * The write method is responsible for making sure that any internal buffers are
 * flushed. If a transaction is active it will also usually be necessary to
 * discard the output on a subsequent rollback. The resource to which the writer
 * is sending data should normally be able to handle this itself.
 * </p>
 * 
 * @author Dave Syer
 * @author Lucas Ward
 */
public interface ItemWriter<T> {

	/**
	 * 处理提供的数据元素。将不会在正常操作中调用任何空项。
	 *
	 * Process the supplied data element. Will not be called with any null items
	 * in normal operation.
	 *
	 * @param items items to be written
	 * @throws Exception if there are errors. The framework will catch the
	 * exception and convert or rethrow it as appropriate.
	 */
	void write(List<? extends T> items) throws Exception;

}

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
 * item转换接口。给定一个条目作为输入，该接口提供了一个扩展点，允许在面向条目的处理场景中应用业务逻辑。
 * 需要注意的是，虽然可以返回不同于所提供的类型的类型，但这并不是严格必要的。此外，返回null表示不应该继续处理该项。
 *
 * Interface for item transformation.  Given an item as input, this interface provides
 * an extension point which allows for the application of business logic in an item 
 * oriented processing scenario.  It should be noted that while it's possible to return
 * a different type than the one provided, it's not strictly necessary.  Furthermore, 
 * returning null indicates that the item should not be continued to be processed.
 *  
 * @author Robert Kasanicky
 * @author Dave Syer
 * @author Mahmoud Ben Hassine
 */
public interface ItemProcessor<I, O> {

	/**
	 * 处理提供的item，返回可能修改的项或新项以继续处理。如果返回的结果为空，则假定该项的处理不应该继续。
	 *
	 * Process the provided item, returning a potentially modified or new item for continued
	 * processing.  If the returned result is null, it is assumed that processing of the item
	 * should not continue.
	 * 
	 * @param item to be processed
	 * @return potentially modified or new item for continued processing, {@code null} if processing of the
	 *  provided item should not continue.
	 *
	 * @throws Exception thrown if exception occurs during processing.
	 */
	@Nullable
	O process(I item) throws Exception;
}

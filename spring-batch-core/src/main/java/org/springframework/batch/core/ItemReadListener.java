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

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

/**
 * 围绕item读取的侦听器接口。
 *
 * Listener interface around the reading of an item.
 * 
 * @author Lucas Ward
 * @author Mahmoud Ben Hassine
 *
 */
public interface ItemReadListener<T> extends StepListener {

	/**
	 * 在ItemReader.read()前调用。
	 *
	 * Called before {@link ItemReader#read()}
	 */
	void beforeRead();
	
	/**
	 * 在ItemReader.read()后调用。
	 * 这个方法只对实际的items调用(即当读取器返回null时不调用)。
	 *
	 * Called after {@link ItemReader#read()}.
	 * This method is called only for actual items (ie it is not called when the
	 * reader returns null).
	 * 
	 * @param item returned from read()
	 */
	void afterRead(T item);
	
	/**
	 * 当尝试读取过程中出现异常时调用。
	 *
	 * Called if an error occurs while trying to read.
	 * 
	 * @param ex thrown from {@link ItemWriter}
	 */
	void onReadError(Exception ex);
}

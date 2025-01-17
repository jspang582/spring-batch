/*
 * Copyright 2006-2008 the original author or authors.
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
 * 作为所有步骤域侦听器的父级的标记接口。
 * 例如:StepExecutionListener, ChunkListener, ItemReadListener和ItemWriteListener
 *
 * Marker interface that acts as a parent to all step
 * domain listeners, such as: {@link StepExecutionListener}, 
 * {@link ChunkListener}, {@link ItemReadListener} and
 * {@link ItemWriteListener}
 * 
 * @author Lucas Ward
 * @author Dave Syer
 *
 */
public interface StepListener {

}

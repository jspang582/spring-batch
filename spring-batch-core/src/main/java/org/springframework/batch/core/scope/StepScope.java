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
package org.springframework.batch.core.scope;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * step上下文的作用域。这个范围中的对象使用Spring容器作为对象工厂，因此每个执行步骤只有一个这样的bean实例。
 * 这个范围内的所有对象都是(不需要修饰bean定义)。步骤上下文的范围。这个范围中的对象使用Spring容器作为对象工厂，因此每个执行步骤只有一个这样的bean实例。
 * 这个范围内的所有对象都是<aop:scoped-proxy/>(不需要修饰bean定义)。
 *
 * 此外，还支持使用spel表达式访问从JobContext引用的后期绑定。使用这个特性，可以从作业或作业执行上下文和作业参数中提取bean属性。
 *
 * JobContext是使用标准bean属性路径(根据BeanWrapper)引用的。示例都展示了Map访问器的使用，它为作业属性提供了便利。
 *
 * Scope for step context. Objects in this scope use the Spring container as an object factory, so there is only one instance of such a bean per executing step. All objects in this scope are <aop:scoped-proxy/> (no need to decorate the bean definitions).
 * Scope for step context. Objects in this scope use the Spring container as an
 * object factory, so there is only one instance of such a bean per executing
 * step. All objects in this scope are &lt;aop:scoped-proxy/&gt; (no need to
 * decorate the bean definitions).<br>
 * <br>
 *
 * In addition, support is provided for late binding of references accessible
 * from the {@link StepContext} using #{..} placeholders. Using this feature,
 * bean properties can be pulled from the step or job execution context and the
 * job parameters. E.g.
 *
 * <pre>
 * &lt;bean id=&quot;...&quot; class=&quot;...&quot; scope=&quot;step&quot;&gt;
 * 	&lt;property name=&quot;parent&quot; ref=&quot;#{stepExecutionContext[helper]}&quot; /&gt;
 * &lt;/bean&gt;
 *
 * &lt;bean id=&quot;...&quot; class=&quot;...&quot; scope=&quot;step&quot;&gt;
 * 	&lt;property name=&quot;name&quot; value=&quot;#{stepExecutionContext['input.name']}&quot; /&gt;
 * &lt;/bean&gt;
 *
 * &lt;bean id=&quot;...&quot; class=&quot;...&quot; scope=&quot;step&quot;&gt;
 * 	&lt;property name=&quot;name&quot; value=&quot;#{jobParameters[input]}&quot; /&gt;
 * &lt;/bean&gt;
 *
 * &lt;bean id=&quot;...&quot; class=&quot;...&quot; scope=&quot;step&quot;&gt;
 * 	&lt;property name=&quot;name&quot; value=&quot;#{jobExecutionContext['input.stem']}.txt&quot; /&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * The {@link StepContext} is referenced using standard bean property paths (as
 * per {@link BeanWrapper}). The examples above all show the use of the Map
 * accessors provided as a convenience for step and job attributes.
 *
 * @author Dave Syer
 * @author Michael Minella
 * @since 2.0
 */
public class StepScope extends BatchScopeSupport {

	private static final String TARGET_NAME_PREFIX = "stepScopedTarget.";

	private Log logger = LogFactory.getLog(getClass());

	private final Object mutex = new Object();

	/**
	 * Context key for clients to use for conversation identifier.
	 */
	public static final String ID_KEY = "STEP_IDENTIFIER";

	public StepScope() {
		super();
		setName("step");
	}

	/**
	 * This will be used to resolve expressions in step-scoped beans.
	 */
	@Override
	public Object resolveContextualObject(String key) {
		StepContext context = getContext();
		// TODO: support for attributes as well maybe (setters not exposed yet
		// so not urgent).
		return new BeanWrapperImpl(context).getPropertyValue(key);
	}

	/**
	 * @see Scope#get(String, ObjectFactory)
	 */
	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		StepContext context = getContext();
		Object scopedObject = context.getAttribute(name);

		if (scopedObject == null) {

			synchronized (mutex) {
				scopedObject = context.getAttribute(name);
				if (scopedObject == null) {

					if (logger.isDebugEnabled()) {
						logger.debug(String.format("Creating object in scope=%s, name=%s", this.getName(), name));
					}


					scopedObject = objectFactory.getObject();
					context.setAttribute(name, scopedObject);

				}

			}

		}
		return scopedObject;
	}

	/**
	 * @see Scope#getConversationId()
	 */
	@Override
	public String getConversationId() {
		StepContext context = getContext();
		return context.getId();
	}

	/**
	 * @see Scope#registerDestructionCallback(String, Runnable)
	 */
	@Override
	public void registerDestructionCallback(String name, Runnable callback) {
		StepContext context = getContext();
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Registered destruction callback in scope=%s, name=%s", this.getName(), name));
		}
		context.registerDestructionCallback(name, callback);
	}

	/**
	 * @see Scope#remove(String)
	 */
	@Override
	public Object remove(String name) {
		StepContext context = getContext();
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Removing from scope=%s, name=%s", this.getName(), name));
		}
		return context.removeAttribute(name);
	}

	/**
	 * Get an attribute accessor in the form of a {@link StepContext} that can
	 * be used to store scoped bean instances.
	 *
	 * @return the current step context which we can use as a scope storage
	 * medium
	 */
	private StepContext getContext() {
		StepContext context = StepSynchronizationManager.getContext();
		if (context == null) {
			throw new IllegalStateException("No context holder available for step scope");
		}
		return context;
	}

	@Override
	public String getTargetNamePrefix() {
		return TARGET_NAME_PREFIX;
	}
}


AbstractAutowireCapableBeanFactory.doCreateBean(String, RootBeanDefinition, Object[])

createBeanInstance() -> 实例化

populateBean() -> 属性赋值,dependency inject

initializeBean() -> 初始化
	
	invokeAwareMethods(beanName, bean);
	
	applyBeanPostProcessorsBeforeInitialization
	
	invokeInitMethods(beanName, wrappedBean, mbd);// 先InitializingBean，再自定义初始化方法
	
	applyBeanPostProcessorsAfterInitialization





涉及到的方法分类：
1.Bean自身的方法：比如构造函数、getter/setter以及init-method和destory-method所指定的方法等
2.Bean级生命周期方法：可以理解为Bean类直接实现接口的方法，比如一系列Aware方法、InitializingBean、DisposableBean等方法，这些方法只对当前Bean生效
3.容器级的方法(BeanPostProcessor一系列接口)：主要是后处理器方法，比如上图的InstantiationAwareBeanPostProcessor、BeanPostProcessor接口方法。这些接口的实现类是独立于bean的，并且会注册到Spring容器中。在Spring容器创建任何Bean的时候，这些后处理器都会发生作用。
4.工厂后处理器方法（BeanFactoryProcessor一系列接口）：包括AspectJWeavingEnabler、CustomAutowireConfigurer、ConfigurationClassPostProcessor等。这些都是Spring框架中已经实现好的BeanFactoryPostProcessor，用来实现某些特定的功能。









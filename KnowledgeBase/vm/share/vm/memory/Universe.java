package share.vm.memory;

public class Universe {
	
	public int universe_init(){
		
		return 1;
	}
	
	public void  universe2_init(){
		
	}
	
	public boolean universe_post_init(){
		return false;
	}
	
	// Java堆的初始化入口
	public static void initialize_heap(){}
	
	/**
	public static void initialize_heap(){
		GenCollectorPolicy *gc_policy;
		if (UseSerialGC) {
			gc_policy = new MarkSweepPolicy();
		}
		gc_policy->initialize_all();//调用父类方法，采用模板方法
		Universe::_collectedHeap = new GenCollectedHeap(gc_policy);
		ThreadLocalAllocBuffer::set_max_size(Universe::heap()->max_tlab_size());
		Universe::heap()->initialize();
		if (UseTLAB) {
			ThreadLocalAllocBuffer::startup_initialization();
		}
	}
	*/
}
package share.vm.memory;

@SuppressWarnings("unused")
public class GenerationSpec {
	
	private String _name;
	private int _init_size;
	private int _max_size;
	
	public GenerationSpec(String name, int init_size, int max_size){
		this._name = name;
		this._init_size = init_size;
		this._max_size = max_size;
	}
	
	public Generation init(String reservedSpace, int level, String genRemSet){
		return null;
	}
}
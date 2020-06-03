package kb.other.lintcode20200529;

import java.util.Stack;

public class MyQueue {
    
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;
    
    public MyQueue() {
        this.stack1 = new Stack<>();
        this.stack2 = new Stack<>();
    }

    /*
     * @param element: An integer
     * @return: nothing
     */
    public void push(int element) {
        stack1.push(element);
    }

    /*
     * @return: An integer
     */
    public int pop() {
    	if(stack2.isEmpty()){
    		while(!stack1.isEmpty()){
        		stack2.push(stack1.pop());
        	}
    	}
    	return stack2.pop();
    }
    
    /*
     * @return: An integer
     */
    public int top() {
    	if(!stack2.isEmpty()){
    		return stack2.peek();
    	}
    	while(!stack1.isEmpty()){
    		stack2.push(stack1.pop());
    	}
    	if(!stack2.isEmpty()){
    		return stack2.peek();
    	}else {
			return -1;
		}
    }
}
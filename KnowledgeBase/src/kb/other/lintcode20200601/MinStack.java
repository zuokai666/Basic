package kb.other.lintcode20200601;

import java.util.Stack;

public class MinStack {
	
	private Stack<Integer> dataStack = new Stack<>();
	private Stack<Integer> minStack = new Stack<>();
	
    public MinStack() {
        // do intialization if necessary
    }

    /*
     * @param number: An integer
     * @return: nothing
     */
    public void push(int number) {
    	dataStack.push(number);
    	if(minStack.isEmpty() || number < minStack.peek().intValue()){
    		minStack.push(number);
    	}else {
    		minStack.push(minStack.peek());
		}
    }

    /*
     * @return: An integer
     */
    public int pop() {
    	minStack.pop();
    	return dataStack.pop();
    }

    /*
     * @return: An integer
     */
    public int min() {
    	return minStack.peek().intValue();
    }
}
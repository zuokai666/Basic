package kb.other.lintcode20200403;

public class Solution2 {

	public static void main(String[] args) {
		new Solution2().findContinuousSeq(15);
	}
	
	public void findContinuousSeq(int sum){
		if(sum < 3){
			return;
		}
		int middle = sum >> 1;
		for(int i=1;i<=middle;i++){
			int a1 = i;
			int n = 2;
			int temp = 0;
			System.out.println("i = " + i);
			while((temp = (n * a1) + (n * (n - 1) >> 1)) <= sum){
				if(temp == sum){
					System.out.println("["+a1+", "+(a1 + (n - 1))+"]");
					break;
				}else {
					n = n + 1;
				}
			}
		}
	}
}
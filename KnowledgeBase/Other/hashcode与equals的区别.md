# hashcode与equals的区别

hashcode与equals都用于对象的比较

int hashcode()，优点：比较速度快，花费O(1)时间复杂度。缺点：缺乏精度，hash值相等，对象不一定相等；hash不同，对象一定不同；
boolean equals()，优点，精度准确，比较对象的最后一关，缺点：比较速度慢，例如字符串比较，会花费O(n)时间复杂度

应用1：

经典HashMap中有这么一段代码：
	
	if (p.hash == hash &&((k = p.key) == key || (key != null && key.equals(k)))){
		// do something.
	}
	
比较策略是，首先用比较速度快的hash筛选出可能相等的对象，然后用equals仔细比较。

应用2：在一个字符串中定位子串的位置.

这是对暴力匹配算法的优化。

Rabin-Karp 算法（也可以叫 Karp-Rabin 算法），由 Richard M. Karp 和 Michael O. Rabin 在 1987 年发表，它也是用来解决多模式串匹配问题的。

它的实现方式有点与众不同，首先是计算两个字符串的哈希值，然后通过比较这两个哈希值的大小来判断是否出现匹配。

	@see kb.other.lintcode20200321.Solution.strStr_v2(String, String)
	@author zuokai，分析HashMap的源码自己得出来的字符串匹配算法。






# 2020年3月21日23:44:40 夜晚与大佬杂谈

彤哥，和你分享个开心的事情，我从阅读HashMap中的感悟用到了算法中。

前段时间，阅读到HashMap中的一段代码时

	if (p.hash == hash &&((k = p.key) == key || (key != null && key.equals(k)))){
		// do something.
	}

我就在思考hashcode()与equals()的区别，同样都是对象比较是否相同，他们到底差在哪里呢？于是我分析出：

int hashcode()，优点：比较速度快，花费O(1)时间复杂度。缺点：缺乏精度，hash值相等，对象不一定相等；hash不同，对象一定不同；
boolean equals()，优点，精度准确，比较对象的最后一关，缺点：比较速度慢，例如字符串比较，会花费O(n)时间复杂度

然后，我今天刷算法题时，做到了这个题：在一个字符串中定位子串的位置，我了解的算法有暴力匹配算法，KMP算法。前者时间复杂度O(n * m)，后者太复杂不会写。

我就在想可不可以使用hashcode()先筛选一波，然后在针对的使用equals()在进行真正的比较。结果写完了，感觉还行，运行挺快的。

后来，我搜到历史上已经有这种算法了，叫Rabin-Karp算法，首先是计算两个字符串的哈希值，然后通过比较这两个哈希值的大小来判断是否出现匹配。

讲真的，之前从来没听过这个算法，居然自己运用了一波，贼开心。



package cn.edu.ahmu.cwc.test;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

/**
 * 使用连接复用策略，防止connection:close的情况给我关了。
 * 还是不行。
 * 
 * @author King
 *
 */
public class UsingConnectionReuseStrategy implements ConnectionReuseStrategy {
	
    public static final UsingConnectionReuseStrategy INSTANCE = new UsingConnectionReuseStrategy();

    public UsingConnectionReuseStrategy() {
        super();
    }
    
    @Override
    public boolean keepAlive(final HttpResponse response, final HttpContext context) {
        return true;
    }
}
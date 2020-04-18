
tomcat-embed-core-8.5.40.jar



	/**
     * Set the maximum number of Keep-Alive requests to allow.
     * This is to safeguard from DoS attacks. Setting to a negative
     * value disables the limit.
     *
     * 设置最大的存活请求次数
     *
     * @param mkar The new maximum number of Keep-Alive requests allowed
     */
    public void setMaxKeepAliveRequests(int mkar) {
        maxKeepAliveRequests = mkar;
    }

keepAliveTimeout：表示在下次请求过来之前，tomcat保持该连接多久。这就是说假如客户端不断有请求过来，且为超过过期时间，则该连接将一直保持。 
maxKeepAliveRequests：表示该连接最大支持的请求数。超过该请求数的连接也将被关闭（此时就会返回一个Connection: close头给客户端）。 

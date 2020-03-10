E:\software\redis>redis-cli -h 127.0.0.1 -p 6379
127.0.0.1:6379> hset user1 name zuokai
(integer) 1
127.0.0.1:6379> hset user1 age 25
(integer) 1
127.0.0.1:6379> hget user1 name
"zuokai"
127.0.0.1:6379> hget user1 age
"25"



string
list
hash
set
sorted set



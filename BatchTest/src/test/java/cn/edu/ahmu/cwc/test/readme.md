
cwc.ahmu.edu.cn
210.45.99.12:80

windows command:
netstat -ano | findstr "210.45.99.12:80"

突然关闭进程出现以下情况：
TCP    172.30.101.29:49761    210.45.99.12:80        TIME_WAIT       0
TCP    172.30.101.29:49767    210.45.99.12:80        TIME_WAIT       0
TCP    172.30.101.29:49772    210.45.99.12:80        TIME_WAIT       0
TCP    172.30.101.29:49847    210.45.99.12:80        TIME_WAIT       0
TCP    172.30.101.29:49854    210.45.99.12:80        TIME_WAIT       0
TCP    172.30.101.29:49855    210.45.99.12:80        TIME_WAIT       0
TCP    172.30.101.29:49865    210.45.99.12:80        TIME_WAIT       0


TIME_WAIT是什么情况？


netstat -ano | findstr "121.42.143.23"

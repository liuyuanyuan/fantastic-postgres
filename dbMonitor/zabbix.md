# Zabbix

##### reference



https://www.cnblogs.com/mysql-dba/p/5010902.html



##### Deployment

DB Server: agent(db, os, device,application)
Proxy(optional): help to gather data 
Zabbix Server:
       front process
       http API
       backend process(gather data from DB server to config db) 
       config DB(store config info and collected data)



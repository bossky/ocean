#!/usr/bin/env bash  
NAME=ocean
DESC=ocean-webapp-jetty
#set 
#jetty所有目录
JETTY_HOME=/home/bo/jetty
export JETTY_HOME=$JETTY_HOME
#运行程序的pid
JETTY_PID="/var/run/$NAME.pid"
export JETTY_PID=$JETTY_PID
#运行程序的用户
JETTY_USER=bo
export JETTY_USER=$JETTY_USER
#用户目录（web项目位置）
USER_DIR=/home/bo/ocean
#jetty日志位置
JETTY_LOGS="/home/bo/ocean/logs"
export JETTY_LOGS="$JETTY_LOGS"
#java选项参数
JAVA_OPTIONS="-Xloggc:$JETTY_LOGS/gc.log  -Duser.dir=$USER_DIR  -Dlogback.configurationFile=$USER_DIR/logback.xml"
export JAVA_OPTIONS="$JAVA_OPTIONS";
#jetty配置
export JETTY_CONF=" "
#start.jar的参数
JETTY_ARGS="OPTIONS=Server,jsp lib=$USER_DIR/webapp/WEB-INF/lib --ini= $USER_DIR/jetty.xml"
export JETTY_ARGS="$JETTY_ARGS"
#要运行的java命令
JAVA=/usr/jdk1.6.0_45/bin/java
export JAVA=$JAVA

case "$1" in
  start)
	ulimit -n 10000
	$JETTY_HOME/bin/jetty.sh -d start
	;;
  stop)
	$JETTY_HOME/bin/jetty.sh stop
	;;
  restart)
	$JETTY_HOME/bin/jetty.sh stop
	sleep 1
	ulimit -n 10000
	$JETTY_HOME/bin/jetty.sh -d start
	;;
  demo)
        ulimit -n 10000
        $JETTY_HOME/bin/jetty.sh -d demo
        ;;
 check)
        ulimit -n 10000
        $JETTY_HOME/bin/jetty.sh -d check
        ;;
  *)
	echo "Usage: $NAME {start|stop|restart|check|demo}" >&2
	exit 1
	;;
esac
exit 0

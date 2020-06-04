# Base image for linux/amd64 architectures
#FROM openjdk:11-jdk

# Base image for Raspberry-pi2 (armv6) architectures
FROM balenalib/raspberry-pi2-debian-openjdk:11-jre-buster

LABEL maintainer="Matteo Dri" \
      description="Owl Energy Monitor image"

COPY /target/owl-energy-monitor-*.jar /app/owl-energy-monitor.jar

ENV JAVA_OPTS -server -Dsun.net.inetaddr.ttl=90 \
    -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:7002

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom \
            -Djava.rmi.server.hostname=0.0.0.0 \
            -Dcom.sun.management.jmxremote \
            -Dcom.sun.management.jmxremote.port=${JMX_PORT:-7003} \
            -Dcom.sun.management.jmxremote.rmi.port=${JMX_PORT:-7003} \
            -Dcom.sun.management.jmxremote.local.only=false \
            -Dcom.sun.management.jmxremote.authenticate=false \
            -Dcom.sun.management.jmxremote.ssl=false \
            ${JAVA_OPTS} \
            -jar /app/owl-energy-monitor.jar

# 7001 - Web endpoint
# 7002 - JVM debug
# 7003 - JMX
# 22600 - UDP multicast
EXPOSE 7001 7002 7003 22600/udp
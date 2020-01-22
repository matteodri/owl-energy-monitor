FROM prom/prometheus:v2.14.0
LABEL maintainer="Matteo Dri" \
      version="0.1.0" \
      description="Prometheus image"

COPY ./configs/ /etc/prometheus/config/

EXPOSE 9090

FROM grafana/grafana:6.7.1
LABEL maintainer="Matteo Dri" \
      version="0.1.0" \
      description="Grafana image"

COPY ./datasources/ /etc/grafana/provisioning/datasources/
COPY ./dashboards/dashboards-conf.yml /etc/grafana/provisioning/dashboards/
COPY ./dashboards/*.json /var/lib/grafana/dashboards/
COPY ./conf/grafana.ini /etc/grafana/

EXPOSE 3000

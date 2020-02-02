FROM grafana/grafana:5.4.3
LABEL maintainer="Matteo Dri" \
      version="0.1.0" \
      description="Grafana image"

COPY ./datasources/ /etc/grafana/provisioning/datasources/
COPY ./dashboards/dashboards-conf.yml /etc/grafana/provisioning/dashboards/
COPY ./dashboards/*.json /var/lib/grafana/dashboards/

EXPOSE 3000

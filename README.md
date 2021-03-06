# Owl Energy Monitor

[![jdk11](https://img.shields.io/badge/java-11-blue.svg)](http://jdk.java.net/11)
[![CircleCI](https://circleci.com/gh/matteodri/owl-energy-monitor.svg?style=svg&circle-token=9009dea82ca7bb9943613a638e673ce172917f8c)](https://circleci.com/gh/matteodri/owl-energy-monitor)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/858e6fe4bd41487fb8dcfef3450dfc6c)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=matteodri/owl-energy-monitor&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/858e6fe4bd41487fb8dcfef3450dfc6c)](https://www.codacy.com?utm_source=github.com&utm_medium=referral&utm_content=matteodri/owl-energy-monitor&utm_campaign=Badge_Coverage)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Purpose
Java application that listens to multicast UDP messages generated by the Owl Intuition home energy monitor, saves them in Prometheus and makes the data available through Grafana.
The application must run on the same network the Owl Intuition is connected to.
This has been tested using a [Owl Intuition-PV](http://www.theowl.com/index.php/energy-monitors/solar-pv-monitoring/intuition-pv/).

Example:
![Energy monitor metrics example](sample_owl_monitor_metrics.png)

## OWL Intuition
[OWL Intuition](http://www.theowl.com/index.php/owl-intuition/) is an electricity monitoring system that tracks electricity consumption and, in the PV version, 
records electricity produced from photovoltaic panels. Their software provides a way to monitor system electricity flows, 
keeping historical data that can be accessed, with some limitations, from their website.
Measures are constantly published over multicast messages on the local network.

Because of the limitations with the search functionality and the short time period consumption stats are available on theowl.com, 
I decided to write my own tool to store, display and analyse consumption data.

## Owl multicast messages
The	Network	OWL	sends	UDP	packets	to the	following	multicast	group:

    Address: 224.192.32.19
    Port: 22600

Information on message format can be found here: [Multicast and UDP API information](https://theowl.zendesk.com/hc/en-gb/articles/201284603-Multicast-UDP-API-Information).

## Build
Check if code complies with formatting template:

`mvn spotless:check`

Apply Spotless code formatting:

`mvn spotless:apply`

Build the Docker image

`mvn clean package`

## Required applications on host
* Docker: version 17.09.0+
* Docker Compose: version 1.17.0+
* Iperf

## Run
Run owl-energy-monitor Spring Boot application only with Java command:

`java -jar target/owl-energy-monitor-<version>.jar`

Build, create, start, and attach to the containers of the Owl Energy Monitor service with Docker Compose.

`docker-compose -f docker/docker-compose.yml up`

Build, create and start a specific container.

`docker-compose -f docker/docker-compose.yml up -d prometheus`

On the host this also needs to be running:

`iperf -s -u -B 224.192.32.19 -p 22600 -i 2`

This runs _iperf_ in server mode binding to Owl multicast address. 
Allows the owl-energy-monitor to receive multicast packets while on the docker _bridge_ network.

## Default endpoints
Service ports as set in _docker-compose.yml_.

**Owl Energy Monitor**

* 7001 - Web endpoint
* 7002 - JVM debug
* 7003 - JMX

**cAdvisor**

* 8001 - Web endpoint

**Prometheus**

* 9090 - Web endpoint

**Grafana**

* 6001 - Web endpoint

E.g. Grafana web console when running in localhost: <http://localhost:6001/>

## License

>MIT License
>
>Copyright (c) 2019 Matteo Dri
>
>Permission is hereby granted, free of charge, to any person obtaining a copy
>of this software and associated documentation files (the "Software"), to deal
>in the Software without restriction, including without limitation the rights
>to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
>copies of the Software, and to permit persons to whom the Software is
>furnished to do so, subject to the following conditions:
>
>The above copyright notice and this permission notice shall be included in all
>copies or substantial portions of the Software.
>
>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
>IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
>FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
>AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
>LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
>OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
>SOFTWARE.

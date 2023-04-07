FROM jenkins/ssh-agent:jdk17

RUN apt update && apt-get install -y curl iptables lvm2 systemctl socat
    # Maven
RUN curl -L https://dlcdn.apache.org/maven/maven-3/3.8.8/binaries/apache-maven-3.8.8-bin.tar.gz --output maven.tgz \
    && tar xzvf maven.tgz
RUN cp -r apache-maven-*/* /usr/local/ \
    && rm -r apache-maven-*/ maven.tgz
    #Docker
RUN curl -L https://download.docker.com/linux/debian/dists/bullseye/pool/stable/amd64/docker-compose-plugin_2.16.0-1~debian.11~bullseye_amd64.deb --output docker-compose.deb
RUN curl -L https://download.docker.com/linux/debian/dists/bullseye/pool/stable/amd64/docker-ce_23.0.1-1~debian.11~bullseye_amd64.deb --output docker-ce.deb
RUN curl -L https://download.docker.com/linux/debian/dists/bullseye/pool/stable/amd64/docker-buildx-plugin_0.10.2-1~debian.11~bullseye_amd64.deb --output docker-buildx.deb
RUN curl -L https://download.docker.com/linux/debian/dists/bullseye/pool/stable/amd64/containerd.io_1.6.9-1_amd64.deb --output containerd.deb
RUN curl -L https://download.docker.com/linux/debian/dists/bullseye/pool/stable/amd64/docker-ce-cli_23.0.1-1~debian.11~bullseye_amd64.deb --output docker-ce-cli.deb

RUN dpkg -i docker-compose.deb docker-ce-cli.deb docker-ce.deb docker-buildx.deb containerd.deb
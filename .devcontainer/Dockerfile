FROM mcr.microsoft.com/devcontainers/java:21

RUN apt-get update && apt-get install -y \
    maven \
    git \
    curl \
    docker.io \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /workspace
FROM mcr.microsoft.com/devcontainers/java:1-21-bullseye

# Instala Maven
RUN apt-get update && apt-get install -y maven

# Ferramentas úteis
RUN apt-get install -y git curl wget

# Diretório de trabalho
WORKDIR /workspace

# Verificação do ambiente
RUN java -version
RUN mvn -version
RUN git --version
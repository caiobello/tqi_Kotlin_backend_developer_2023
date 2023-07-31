# Use a imagem base do OpenJDK 11 para executar a aplicação
FROM openjdk:11-jre-slim

# Configuração da pasta de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR da sua aplicação (já compilado) para a pasta de trabalho do contêiner
COPY build/libs/nome-da-sua-aplicacao.jar /app/app.jar

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "app.jar"]

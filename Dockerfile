FROM ubuntu:oracular AS ubuntu-jre21-imagemagic
RUN apt-get update -y
RUN apt-get install -y openjdk-21-jre-headless
COPY script/install_imagemagic_with_heic.sh .
RUN chmod +x ./install_imagemagic_with_heic.sh
RUN ./install_imagemagic_with_heic.sh

FROM ubuntu-jre21-imagemagic
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# docker build . -t cbidici/file-service --no-cache --network=host
# docker run --restart always -v ~/Desktop/calis:/media/file-base -v ~/Desktop/system:/media/system -p 8080:8080 --detach cbidici/file-service

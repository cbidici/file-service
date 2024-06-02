FROM ubuntu:noble AS ubuntu-jre21-imagemagic
RUN apt-get update -y
RUN apt install openjdk-21-jre -y
COPY script/install_imagemagic_with_heic.sh .
RUN chmod +x ./install_imagemagic_with_heic.sh
RUN ./install_imagemagic_with_heic.sh

FROM ubuntu-jre21-imagemagic
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# dockebuild -t cbidici/file-service .
# docker run -v ~/Desktop/calis:/media/file-base -v ~/Desktop/system:/media/system -p 8080:8080 cbidici/file-svice
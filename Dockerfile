# Используем официальный образ OpenJDK
FROM openjdk:17-oracle

# Устанавливаем рабочую директорию
WORKDIR /BackendAlertApi

# Копируем JAR-файл из сборки проекта в контейнер
COPY target/backendSiteDiplom-0.0.1-SNAPSHOT.jar /BackendAlertApi

# Экспонируем порт, на котором работает ваше приложение
EXPOSE 8080

# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "backendSiteDiplom-0.0.1-SNAPSHOT.jar"]

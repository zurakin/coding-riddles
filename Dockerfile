# ---- Frontend Build Stage ----
FROM node:20-alpine as frontend-build
WORKDIR /frontend
COPY frontend ./
RUN npm install && npm run build

# ---- Backend Build Stage ----
FROM eclipse-temurin:23-jdk as build
WORKDIR /build
COPY backend ./
# Copy built frontend to backend resources
COPY --from=frontend-build /frontend/dist ./src/main/resources/public
RUN ./mvnw clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:23-jre
WORKDIR /app
COPY --from=build /build/target/coding-riddles.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

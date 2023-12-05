stages:
  - build
  - analyze
  - docker-registry

services:
  - name: postgres:latest
    alias: postgresql

variables:
  POSTGRES_DB: reservation
  POSTGRES_USER: postgres
  POSTGRES_PASSWORD: maroc123
  POSTGRES_HOST_AUTH_METHOD: trust
  DATABASE_HOST: postgresql  # PostgreSQL service alias
  DATABASE_PORT: 5432
  MAVEN_CLI_OPTS: "--batch-mode"  
  CI_REGISTRY_IMAGE: noureddinelachgar/product-api
  SONAR_URL: http://172.174.163.92:9000

cache:
  paths:
    - .m2/repository

image: maven:3.8.6-openjdk-18

clean-and-test:
  stage: build
  script:
    - cd reservation-api
    - mvn $MAVEN_CLI_OPTS -B clean verify

compile:
  stage: build
  script:
    - cd reservation-api
    - mvn $MAVEN_CLI_OPTS compile

install:
  stage: build
  script:
    - cd reservation-api
    - mvn $MAVEN_CLI_OPTS install

test-coverage:
  stage: analyze
  script:
    - cd reservation-api
    - mvn jacoco:report
  artifacts:
    when: always
    paths:
      - reservation-api/target/site/jacoco/jacoco.xml
    reports:
      coverage_report:
        coverage_format: cobertura
        path: reservation-api/target/site/jacoco/jacoco.xml

docker:
  stage: docker-registry
  image: docker:latest
  services:
    - docker:dind
  script:
    - docker login -u "$CI_REGISTRY_USER" -p "$CI_REGISTRY_PASSWORD" $CI_REGISTRY
    - docker build -t $CI_REGISTRY_IMAGE/reservation-api:latest .
    - docker push $CI_REGISTRY_IMAGE/reservation-api:latest

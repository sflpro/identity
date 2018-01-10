#!/usr/bin/env bash


if [ $TRAVIS_BRANCH == 'develop' ] || [ $TRAVIS_BRANCH == 'master' ]
then
    mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent verify sonar:sonar -B \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.organization=sfl \
    -Dsonar.login=$SONARCLOUD_KEY
else
    mvn clean verify -B
fi
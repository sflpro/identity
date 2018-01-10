#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" == "develop" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]
then
    echo "Running develop branch build and analysis. Snapshots will be published. All issues/stats will be saved to Sonar database."
    mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent verify sonar:sonar -B \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.organization=sfl \
    -Dsonar.login=$SONARCLOUD_KEY
elif [ "$TRAVIS_PULL_REQUEST" != "false" ]
then
    echo "Running Github PR build and analysis. Sonar will run in preview mode."
    mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent verify sonar:sonar -B \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.organization=sfl \
    -Dsonar.login=$SONARCLOUD_KEY \
    -Dsonar.github.repository=sflpro/identity \
    -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST \
    -Dsonar.github.oauth=$SONAR_GITHUB_OAUTH_TOKEN
else
    echo "Running build without sonar analysis."
    mvn clean verify -B
fi
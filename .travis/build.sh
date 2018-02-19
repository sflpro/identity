#!/usr/bin/env bash

docker login -u yervand -p $DOCKERHUB_PASS

echo "Extract secrets"
tar -jxvf secret.tar.bz2
mv secret/travis-gpg-key.asc ./travis-gpg-key.asc
mv secret/settings.xml ./settings.xml
echo "Import gpg key"
gpg --import travis-gpg-key.asc
sleep 1

echo "Proceeding to compilation/check"


if [ "$TRAVIS_BRANCH" == "develop" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]
then
    echo "Running develop branch build and analysis. Snapshots will be published. All issues/stats will be saved to Sonar database."
    mvn -P snapshot -P central -s settings.xml clean org.jacoco:jacoco-maven-plugin:prepare-agent deploy sonar:sonar -B \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.organization=sfl \
    -Dsonar.login=$SONARCLOUD_KEY
elif [ $TRAVIS_BRANCH == 'master' ] && [ "$TRAVIS_PULL_REQUEST" == "false" ]
then
    init_central_auth
    echo "Running master branch build and analysis. Release artifacts will be published.. Sonar run will be skipped."
    mvn -P release -P central -s settings.xml clean org.jacoco:jacoco-maven-plugin:prepare-agent deploy sonar:sonar -B \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.organization=sfl \
    -Dsonar.login=$SONARCLOUD_KEY \
    -Dgpg.passphrase=$TRAVIS_GPG_KEY_PASS
elif [ "$TRAVIS_PULL_REQUEST" != "false" ]
then
    echo "Running Github PR build and analysis. Sonar will run in preview mode."
    mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent verify sonar:sonar -B \
    -Dsonar.host.url=https://sonarcloud.io \
    -Dsonar.organization=sfl \
    -Dsonar.login=$SONARCLOUD_KEY \
    -Dsonar.analysis.mode=preview \
    -Dsonar.github.repository=sflpro/identity \
    -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST \
    -Dsonar.github.oauth=$SONAR_GITHUB_OAUTH_TOKEN
else
    echo "Running build without sonar analysis."
    mvn clean verify -B
fi
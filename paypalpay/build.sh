#!/usr/bin/env bash
mvn clean
mvn package -Dmaven.test.skip=true
docker build -t ruanxingke/paypalpay .
docker push ruanxingke/paypalpay

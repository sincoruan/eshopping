#!/usr/bin/env bash
mvn clean
mvn package -Dmaven.test.skip=true
docker build -t ruanxingke/bankpay .
docker push ruanxingke/bankpay

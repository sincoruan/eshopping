#!/usr/bin/env bash
kubectl delete -n default deployment zuul
kubectl delete -n default service zuul
kubectl apply -f deploy.yml

#!/usr/bin/env bash
kubectl delete -n default deployment auth
kubectl delete -n default service auth
kubectl apply -f deploy.yml

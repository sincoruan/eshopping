#!/usr/bin/env bash
kubectl delete -n default deployment product
kubectl delete -n default service product
kubectl apply -f deploy.yml

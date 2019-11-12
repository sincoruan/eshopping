#!/usr/bin/env bash
kubectl delete -n default deployment order
kubectl delete -n default service order
kubectl apply -f deploy.yml

#!/usr/bin/env bash
kubectl delete -n default deployment account
kubectl delete -n default service account
kubectl apply -f deploy.yml

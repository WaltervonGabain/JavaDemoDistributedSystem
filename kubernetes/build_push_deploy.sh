#!/bin/bash

cd ..

cd inputhandler/src/
if podman build -t $(minikube ip):5000/inputhandler . ; then
  podman push $(minikube ip):5000/inputhandler
fi

cd ..
cd ..

cd inputsender/src/
if podman build -t $(minikube ip):5000/inputsender . ; then
  podman push $(minikube ip):5000/inputsender
fi

cd ..
cd ..

cd kubernetes
kubectl apply -f .
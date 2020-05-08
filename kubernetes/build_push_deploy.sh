#!/bin/bash

cd inputsender
if podman build -t $(minikube ip):5000/inputsender . ; then
  podman push $(minikube ip):5000/inputsender
fi

cd ..

cd inputhandler
if podman build -t $(minikube ip):5000/inputhandler . ; then
  podman push $(minikube ip):5000/inputhandler
fi

kubectl apply -f .
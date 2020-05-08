#!/bin/bash

cd ..

cd out/production/InputHandler
if podman build -t $(minikube ip):5000/inputhandler . ; then
  podman push $(minikube ip):5000/inputhandler
fi

cd ..

cd inputsender
if podman build -t $(minikube ip):5000/inputsender . ; then
  podman push $(minikube ip):5000/inputsender
fi
#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"
docker stop sonnim-server || true
docker rm sonnim-server || true
docker pull 058264214897.dkr.ecr.ap-northeast-2.amazonaws.com/sonnim-server:latest
docker run -d --name sonnim-server -p 8080:8080 058264214897.dkr.ecr.ap-northeast-2.amazonaws.com/sonnim-server:latest
echo "--------------- 서버 배포 끝 -----------------"
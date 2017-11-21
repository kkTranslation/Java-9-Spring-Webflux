#!/usr/bin/env bash

mkdir out

javac -d out --module-source-path src --module dockerx.module.starter

mkdir mods

jar --create --file mods/dockerx.module.starter.jar --main-class com.example.Main --module-version 1.0 -C out/dockerx.module.starter .

java --module-path mods --module dockerx.module.starter


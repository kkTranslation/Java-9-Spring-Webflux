#!/usr/bin/env bash

javac -d out --module-source-path src --module dockerx.module.starter

mdkdir out

jar --create --file mods/dockerx.module.starter.jar --main-class com.example.Main --module-version 1.0 -C out/dockerx.module.starter .

mdkdir mods

java --module-path mods --module dockerx.module.starter


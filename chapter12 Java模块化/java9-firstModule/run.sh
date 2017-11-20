#!/usr/bin/env bash

javac -d out --module-source-path src --module dockerx.firstModule

jar --create --file mods/dockerx.firstModule.jar --main-class com.example.Main --module-version 1.0 -C out/dockerx.firstModule .

java --module-path mods --module dockerx.firstModule


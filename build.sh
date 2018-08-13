#!/bin/sh

cp ~/eclipse-workspace/de.idnow.example/app/models/* app/models/ 
cp ~/eclipse-workspace/de.idnow.example/app/controllers/* app/controllers/ 
cp ~/eclipse-workspace/de.idnow.example/test/controller/* test/controller/
./activator test



#!/bin/bash

# Download jdk
wget https://github.com/ojdkbuild/contribjdk11u-ci/releases/download/jdk-11.0.8%2B10/jdk-11.0.8-ojdkbuild-linux-x64.zip -O java11build.zip
# Unzip jdk to /usr/local/
sudo unzip java11build.zip -d /usr/local/
# Remove zipped download
rm java11build.zip
# Add jdk to path
sudo echo "export PATH=\"$PATH:/usr/local/jdk-11.0.8-ojdkbuild-linux-x64/bin\"" >> ~/.profile
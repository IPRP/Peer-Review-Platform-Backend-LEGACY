#!/bin/bash

# Check target distro
distro=$(awk -F= '/^NAME/{print $2}' /etc/os-release) 
if [ $distro != "\"Ubuntu\"" ]
then
    echo "ERROR: This scripts only supports Ubuntu at the moment"
    exit 1
fi

# Install dependencies
sudo apt-get install -y gnupg
# Import public key used by the package management system
wget -qO - https://www.mongodb.org/static/pgp/server-4.4.asc | sudo apt-key add -

# Get Ubuntu Version
version=$(lsb_release -d | sed -n -e 's/^.*Ubuntu //p' | sed 's/.\{6\}$//')

# Create a list file for MongoDB
case $version in

    20.04)
        echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/4.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.4.list
        ;;

    18.04)
        echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu bionic/mongodb-org/4.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.4.list
        ;;

    16.04)
        echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu xenial/mongodb-org/4.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.4.list
        ;;

    *)
        echo "ERROR: Unsupported Ubuntu version"
        exit 1
        ;;
esac

# Reload local package database
sudo apt-get update

# Install the MongoDB packages
sudo apt-get install -y mongodb

echo "MongoDB successfully installed!" 

# Configure service so that password is not needed when starting MongoDB
user=$(whoami)
sudo bash -c "echo '$user ALL=(ALL:ALL) NOPASSWD:/usr/sbin/service mongodb start' >> /etc/sudoers"
sudo bash -c "echo '$user ALL=(ALL:ALL) NOPASSWD:/usr/sbin/service mongodb stop' >> /etc/sudoers"
echo "MongoDB can now be started via 'sudo service mongodb start', password is not needed."
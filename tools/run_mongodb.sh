#!/bin/bash

if [[ $(service mongodb status | grep fail) ]]
then 
    echo MongoDB not running...
    echo Starting it now...
    sudo service mongodb start
fi
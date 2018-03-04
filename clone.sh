#!/usr/bin/env bash
#this script can be helpful in cloning the git directory to specified location

# run command in following format
#sh clone.sh https://github.com/lodash/lodash.git /home/mujahidmasood/Masters/DSS/Semester5/PTAA/nested-loop-vulnerabilities/node_modules

#first argument is git directory
#second argument is location where repository needs to be cloned.

mkdir $2'/'${1##*/}
git clone $1 $2'/'${1##*/}



#!/usr/bin/bash
#   POST INSTALL SCRIPT
#
echo "installation of jquery highlighter module, testcentJS module, chromeapiinject module ..."
basepath=$(pwd)
echo "working directory is $basepath" 
echo " EC webgate user"
read ecuser
echo "EC wegate cred"
read pas
### init
npm init --yes


#### install of jquery highlighter
#
npm i git+https://${ecuser}:${pas}@webgate.ec.europa.eu/CITnet/stash/scm/tcperf/highlighter.git --verbose
#
#install of module testcenter
npm i git+https://${ecuser}:${pas}@webgate.ec.europa.eu/CITnet/stash/scm/tcperf/testcentrejs.git --verbose

####install of chrome api injector
#
npm i git+https://${ecuser}:${pas}@webgate.ec.europa.eu/CITnet/stash/scm/tcperf/chromeapiinject.git --verbose
wait $!

wait $!

npm install --save  
wait $!
cd $basepath


##copy custom-param.js in testcentreJS module
cp custom-param.js   node_modules/testcentrejs/custom-param.js


cd node_modules/chromeapiinjector/
npm install --save 
wait $!
cd $basepath

cd node_modules/jqueryhiglighter/
npm install --save 
wait $!
cd $basepath
#!/bin/bash


WEBPACK_PATH="./node_modules/webpack/bin/webpack.js"
NODE="node"

nodeVersion=$(${NODE} --version)
echo Using ${NODE} ${nodeVersion}
echo Using webpack path ${WEBPACK_PATH}
if [ ! -z "${1}" ]; then
    echo Using $1
fi

if [[ "$nodeVersion" < "v6" ]]; then
    echo "!! Warning: node version too low (expected v6+)"
    echo "            try installing and using 'nvm'"
fi

if [ ! -e ${WEBPACK_PATH} ]; then
    echo "Warning: webpack does not exist, make sure to run `npm install` first"
fi

${NODE} ${WEBPACK_PATH} $1
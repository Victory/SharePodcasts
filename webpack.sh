#!/bin/bash


WEBPACK_PATH="./node_modules/webpack/bin/webpack.js"

nodeVersion=$(node --version)
echo Using node ${nodeVersion}
echo Using webpack path ${WEBPACK_PATH}

if [[ "$nodeVersion" < "v6" ]]; then
    echo "!! Warning: node version too low (expected v6+)"
    echo "            try installing and using 'nvm'"
fi

if [ ! -e ${WEBPACK_PATH} ]; then
    echo "Warning: webpack does not exist, make sure to run `npm install` first"
fi

node ./node_modules/webpack/bin/webpack.js --optimize-minimize
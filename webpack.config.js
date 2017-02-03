const webpack = require('webpack');
module.exports = {
    entry: {
        bundel: __dirname + "/src/main/javascript/entry.js",
        //vendor: __dirname + "/src/main/javascript/vendor/jquery.js",
    },
    resolve: {
        alias: {
            jquery: __dirname + "/src/main/javascript/vendor/jquery.js",
        }
    },
    output: {
        path: __dirname + "/src/main/resources/public/js",
        filename: "bundle.js"
    },
    module: {
        loaders: [
            {
                test: /.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/,
                query: {
                    presets: ['es2015']
                }
            },

        ],
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery'
        })
    ],

    devtool: "source-map"
};
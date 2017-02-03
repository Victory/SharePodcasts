const webpack = require('webpack');
module.exports = {
    entry: {
        bundle: __dirname + "/src/main/javascript/entry.js",
        //vendor: __dirname + "/src/main/javascript/vendor.js",
    },
    resolve: {
        alias: {
            //jquery: __dirname + "/node_modules/jquery/dist/jquery.js",
            //bootstrap: __dirname + "/node_modules/bootstrap/dist/js/bootstrap.js",
        }
    },
    output: {
        path: __dirname + "/src/main/resources/public/js",
        filename: "[name].js"
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
        noParse: /bootstrap|jquery/
    },
    plugins: [
        /*
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery'
        }),
        new webpack.optimize.CommonsChunkPlugin({
            name: 'vendor' // Specify the common bundle's name.
        })
        */
    ],

    devtool: "source-map"
};
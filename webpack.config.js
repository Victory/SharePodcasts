module.exports = {
    entry: {
        bundle: __dirname + "/src/main/javascript/entry.js",
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
    },
    devtool: "source-map"
};
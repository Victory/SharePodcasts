# Share Podcasts

An online tool to easily shared podcast episodes.

This is currently a work in progress see [todos](#todos)

# Building
First run npm upgrade to  install the webpack tools

    npm upgrade

Its highly recommended to use `nvm` to get version 6.

You must have the Rocker Gradle Plugin

Javascript is compiled with webpack.

    ./gradlew -t compileWebpack


#### Todos {#todos}

In no particular order

 - Build the feed updater thread pool and executor
 - Privacy Policy and Terms of Service
 - Add browse feeds and episodes pages
 - Add a field to episodes collection to search text and index on that
 - Add text explaining the site on the homepage
 - Web Analytics
 - Top lists



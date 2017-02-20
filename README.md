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

[![Build Status](https://travis-ci.org/Victory/SharePodcasts.svg?branch=master)](https://travis-ci.org/Victory/SharePodcasts)

#### Todos

In no particular order

 - Build the feed updater thread pool and executor
 - Terms of Service
 - Add browse feeds and episodes pages
 - Add a field to episodes collection to search text and index on that
 - Add text explaining the site on the homepage
 - Web Analytics
 - Top lists
 - Add a field to shows to store originating ip (i.e. who put in the ip)
 - Add collection for seeing how often people add already existing shows
 - Contact form
 - More informative 404 pages

# Share Podcasts

An online tool to easily shared podcast episodes.

This is currently a work in progress see [todos](#todos)

# Building [![Build Status](https://travis-ci.org/Victory/SharePodcasts.svg?branch=master)](https://travis-ci.org/Victory/SharePodcasts)

### npm
First run npm upgrade to install the webpack and other js/css depenencies (Here using nvm to get the correct version of npm)

    nvm install 7.5.0
    npm upgrade
    
### Rocker Gradle Plugin
You must have the Rocker Gradle Plugin which is not yet in maven central, so has to be built from source

    # cd to same directory where cloned this (SharePodcast) repo. 
    #   `rocker` and `SharePodcasts` should be sibilings in file structure
    
    git clone -b rocker-gradle-plugin https://github.com/Victory/rocker.git
    cd rocker/rocker-gradle-plugin
    ../../SharePodcasts/gradlew build
    ../../SharePodcasts/gradlew uploadArchives
    
### Compiling javascript
Javascript is compiled with webpack.

    ./gradlew -t compileWebpack

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
 - Add skip step button to create-share-link page

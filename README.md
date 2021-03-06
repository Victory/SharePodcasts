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
    
You can then compile rocker with
     
     ./gradlew rockerCompile
     
### Testing

Tests are in `src/main/test` and can be run with gradle

     ./gradlew rockerCompile build test

### Compiling javascript
Javascript is compiled with webpack.

    ./gradlew -t compileWebpack
    
### nginx config

Normally this application would be run behind a proxy. 
For Nginx, you will have to foward the users real ip.

    proxy_set_header X-Real-IP       $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    
    location / {
        proxy_pass http://127.0.0.1:4567;
    }


#### Todos

In no particular order

 - Terms of Service
 - Add a field to episodes collection to search text and index on that
 - Add text explaining the site on the homepage
 - Web Analytics frontend
 - Top lists
 - Add collection for seeing how often people add already existing shows
 - More informative 404 pages
 - Show suggestions tooltip only if disabled
 - Make an application test that starts Spark
 - robots.txt
 - [fix search](https://github.com/Victory/SharePodcasts/issues/1)
 - db.episodes.dropIndex("$**_text") - run on next deployment


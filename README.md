# Web Crawler Spike

## Introduction

    This is written in java and is a basic version of a web crawler. It prints the pages that it finds to the System.out.
    It starts on a given %startingUrl% then iterates around the page finding links and following links if they are still within the
    same domain.

## Useage

    Run the following:-

    mvn clean package shade:shade

    This will create an executable jar file.

    Then run the following:-

    java -jar target/web-crawler-1.0.0-SNAPSHOT.jar %startingUrl% %maxDepth%

    where

    %startingUrl% = the home page to start web crawler
    %maxDepth% = Optionally, the no of levels to follow links down to, default is 3. This was added as it can take some time to execute.


## Improvements that could be made

    Use some form of parallelism to improve performance. ie spin up workers in their own threads which could be done to lookup the pages.
    This would probably work best on some sort of distributed model or use a pool of Akka actors or Futures.

    This is a classic recursive algo and as such would have a good fit to be implemented in Scala rather than Java.
    I tried to make the data structures immutable so that it would support this notion by minimise side effects etc.

    Improve the output, currently it just outputs the full address to the system.out. However as there is an object model around this it would
    be quite easy to make some more user friendly output.

    Improve the classification of the pages, currently there is only images / pages and external links. This should also be built out to
    capture javascript / css files.

    I deliberately tried to keep it simple by not using any other libraries (apart from logging and testing), but using Jsoup could be an option
    worth investigating.
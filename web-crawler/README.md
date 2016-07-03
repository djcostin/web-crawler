Web Crawler Spike

This is written in java and is a basic version of a web crawler.



Improvements that could be made

Use some form of parallelism to improve performance. ie spin up workers in their own threads which could be done to lookup the pages.

This would probably work best on some sort of distributed model or use a pool of Akka actors.
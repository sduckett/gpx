gpx
===
[![Circle CI](https://circleci.com/gh/sduckett/gpx/tree/master.svg?style=svg)](https://circleci.com/gh/sduckett/gpx/tree/master)

This is a simple Clojure application to parse a GPX file and display some
information about it.

* Total distance, in meters
* Elapsed time, in seconds
* Average speed, in kilometers / hour

Usage
-----

With [Leiningen][1]:

    $ lein run <your gpx file here>

Example output:

    Distance:      3.61 km
    Elapsed time:  470 s
    Average speed: 27.65 km/h

[1]: http://leiningen.org/

TODO
----

- namespaces for context-narrowing
- write tests around parsing functions
- format time as HH::MM::SS
- plot an elevation profile of the track
- output distance / average speed as metric or imperial units

License
-------

BSD, short and sweet

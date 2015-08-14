gpx
===

This is a simple Clojure application to parse a GPX file and display some
information about it.

* Total distance
* Elapsed time
* Average speed

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

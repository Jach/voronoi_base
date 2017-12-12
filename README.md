# Voronoi Base

Base code for conducting a new interview experiment: let the interviewer and the candidate try to
come up with and analyze algorithm(s) for generating Voronoi diagrams!

The simple frontend lets the user specify points. Clicking either of the two algorithm buttons
will send the point list up to the server. The server should respond with a VoronoiGraph data structure
stringified to JSON. The structure contains at a minimum a set of edges specified as lines from [[x0, y0], [x1, y1]].

Included is an old implementation of the classic Fortune's algorithm to check against. The idea is that using a similar
interface, the interviewer + candidate should be able to work on algorithm(s) generating the same data. Maybe even work
up to an independent implementation of Fortune's Algorithm itself!

# Usage

After checking out this repo and changing directories into it:

```
cd simplevoronoi
mvn clean install
cd ..
mvn clean package
```

Then you can launch the application with

`mvn exec:java`

or just running

`java -jar target/voronoi_base-1.0-SNAPSHOT-jar-with-dependencies.jar`

# License

Copyright (c) 2017 Kevin Secretan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 

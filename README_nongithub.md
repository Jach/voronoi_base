Note: This is the intended README to be distributed with the RPT exercise, you
should remove this statement and replace the other README. The differences are
basically that this one is just about build steps and licensing info.

# RPT Instructions

Please see the included rpt\_exercise.pdf file for your task. But before you
dive in, you should make sure you have set up the application first.

This RPT can be completed with JavaScript and the browser alone should you wish,
but we encourage you to use Java.

## Java Setup

The Java project here is voronoi\_base. It's a maven project.

### Eclipse User

If you use Eclipse (recommended) and have the m2e plugin installed (most people
do, but if you don't you should get it from the marketplace), then set up the
project:

`File -> Import -> Maven -> Existing Maven Projects -> Select the Voronoi Base
folder`

Once you are setup you should be able to open and run Application.java.

### Command Line Maven

If you want to use the command line only, you need to run:

```
cd voronoi_base # where you extracted this to
mvn clean package
```

Then you can launch the application with

`mvn exec:java`

and further change-run cycles with `mvn compile exec:java`, alternatively you
can just run the uberjar that was packaged:

`java -jar target/voronoi_base-1.0-SNAPSHOT-jar-with-dependencies.jar`

Then as the output states, you can visit http://localhost:7171/ui.html to see
the application front end.

## JavaScript Setup

If you end up going the JS route, you can simply open the ui.html file directly
in your web browser. It will detect that it is offline and so you should see the
"offline" checkbox already checked for you.

# License

This project with the exception of rhill-voronoi-core.js and the
be.humphreys.simplevoronoi folder is hereby released under the following terms.

Copyright (c) 2017-2018 Kevin Secretan

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 

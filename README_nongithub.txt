Note: This is the intended README to be distributed with the RPT exercise, you
should remove this statement and replace the other README. The differences are
basically that this one is just about build steps and licensing info.

====================
====================
RPT Instructions
====================
====================

Please see the included "rpt_exercise.pdf" file for your task. But before you
dive in, you should make sure you have set up the application first!

This RPT can be completed with JavaScript and the browser alone should you wish,
but we encourage you to use Java if you can as much of our work is in that
language. (Java 8 or later is preferred, but this project should work with
Java 7.)

Find your preferred setup section below, and once you have completed your setup,
you should open the PDF and begin.

====================
====================
Java Setup
====================
====================

The Java project here is called "voronoi_base". It's a maven project.

====================
For Eclipse Users
====================

Eclipse is especially recommended if you don't usually have a Java environment
setup and need one for this RPT. Please download the latest version of
the "Eclipse IDE for Java and DSL Developers" from https://www.eclipse.org/downloads/packages/

Once that is done (or if Eclipse is already installed) then import this project:

`File -> Import -> Maven -> Existing Maven Projects -> Select the Voronoi Base
folder you saved this file in`

Once you are setup you should be able to open and run "Application.java".
It is in the "com.thejach.voronoi" package.

Check the Console output and verify that you can open
"http://localhost:7171/ui.html"

If you can, then you are ready to go! Please open the PDF file, good luck, and
try to have fun!

Note: you need the "m2e" plugin for Eclipse to import it this way. Newer
versions of Eclipse should have it by default, but if you don't, please
install it via the marketplace and try again.

====================
Command Line Maven Users
====================

If you want to use the command line only, you need to run:

```
cd voronoi_base # where you extracted this to
mvn clean package
```

Then you can launch the application with

`mvn exec:java`

and further change-run cycles with `mvn compile exec:java`.

You alternatively can just run the uberjar that was packaged:

`java -jar target/voronoi_base-1.0-SNAPSHOT-jar-with-dependencies.jar`

Then as the output states, you can visit http://localhost:7171/ui.html to see
the application front end.

====================
====================
JavaScript Setup
====================
====================

If you end up going the JS route, you can simply open the "ui.html" file directly
in your web browser. It will detect that it is offline and so you should see the
"offline" checkbox already checked for you. The PDF will contain further
instructions for where you should make your modifications, which you can test
by just refreshing the browser.

====================
====================
License
====================
====================

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

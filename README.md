# Two Points Problem Statement Notes

This branch is meant for a "phone screen" type of situation where you only have
30-45 minutes to evaluate the candidate technically. This seems to be a better
base than asking them to do something like reverse string or whatever, and for
fast candidates (or more time, like an hour) can discuss trying to solve 3 or n
points or any of the other ideas mentioned in the original readme and PDF.

What I do is have them join a video conferencing call, do intros and maybe some soft
resume questions to warm them up, then I make sure that we are synced with a shared
text editor like https://codeshare.io/ and then I share my screen.

I have this project running locally, and with screen share I show the application and
explain what voronoi diagrams are, then give them the problem statement of generating
a voronoi diagram with only two points -- showing them that it looks like a line that bisects
the bounded plane between the two points.

If you're feeling trusting, you could have them host their screen instead.. A fun
project extension would be to include a JS code editor below the graph so that edits can be made
inline for "offline" mode (like js-fiddle) and then you could send them to
https://htmlpreview.github.io/?https://github.com/Jach/voronoi_base/blob/master/ui.html
and let them at it without forcing them to use the dev console for changes.

Then I ask if they prefer JS or Java, and open the code for half of the screen, and paste
the code for the file they need to edit into the shared text editor.
(The diff of those files is that I've added the problem statement and useful formulas in comments rather than referring to a PDF.)
I walk through the demos, tell them to type in the shared
editor, and any time they are ready they can tell me to paste their code back into my running application
and try running their custom algorithm to see if the output is good. Also run the Fortune solution for comparison.
Problem explanation and setup takes about 5 minutes tops.

I can debug with them, add printlns, add junit tests, whatever, if they request it. If there are trivial syntax issues
I try to catch and fix them myself in the shared editor, or let the IDE handle it, though some
I'll leave to the candidate for a moment to spot themselves.

Most candidates get the midpoint x and y quickly. Using algebra to solve for some intersects with the bounding box can be a bit tricky,
so try to follow them closely and ask them to put their work in the code comments if they're using a pencil once they're done.

Scoring-wise, producing one edge is 2 points, and producing two edges is 1 point. Producing a bounded edge is 3 points. Any unit test is 1 point,
subsequent tests at your discretion for how valuable they are. You may find it useful to write a unit test yourself ahead of time to verify
the several edge cases automatically.

If they properly handle the vertical and horizontal cases correctly without you pointing them out, 0.5 extra points.

Subjective factors like exceptional code quality or advancing into more complex problem derivatives (3 point case, n point case...)
can merit extra fractional points.

A successful brute-force approach that just draws points instead of an edge is at least 1 point,
but automatic 5.5 points if they can generalize it to the n point case.

A successful single-edge approach using circle intersects is cool enough to merit an automatic 4 points.

Thus the "max" score is around 6.5. To me, an acceptable score is 1 point over 30 minutes, 2 points over 45 minutes, and 3 points over an hour.
Your standards may differ, think it through ahead of time. If 2 points is reached (e.g. one edge, forgot the horiz/vert cases, no test, no bounds)
and there is still time left, you can see how far they get covering the things they missed, or try to do something new and add points for that.
Graph coloring may be fun and for the right candidate easy to show off they are familiar with the obvious backtracking approach.

# License

This project with the exception of rhill-voronoi-core.js and the
be.humphreys.simplevoronoi folder is hereby released under the following terms.

Copyright (c) 2017-2018 Kevin Secretan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 

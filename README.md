# Two Points Problem Statement Notes

The problem statement and equations are given in the CustomAlgorithm.java file.

This branch is meant for either a "phone screen" interview or an on-site
interview where you need to evaluate the candidate technically in a short
amount of time. Basically I believe it provides some useful signals despite
only having 30-45 minutes for the candidate to solve it. At least this seems to
be a better base than asking them to do something like reverse string or
whatever, and for fast candidates, candidates who show a 'suspicious' level of
preparedness for this specific problem (hi! :)) or even just for cases where
you have more time, there are many jump-off points like discussing the 3-point
case, n-point case, handling all edge cases, or any of the other ideas
mentioned in the master branch readme and PDF.

The whole problem setup and explanation should take only around 5 minutes,
tops, but it's important to put it in the context of Voronoi cells rather than
give the problem in a direct "find the line segment" form. You can say that's
basically what it is, but doing so indicates they're committed to using the
equation of a line formulas when there are alternative solutions that don't
use them they might miss without hearing the overall context.

## Administration

What I do for the remote situation, assuming I have an hour, is have them join
a video conferencing call, do intros and maybe some soft resume questions /
other inquiries to warm them up for up to 10-15 minutes, then I make sure that
we are synced with a shared text editor like https://codeshare.io/ and then I
share my screen.

I have this project running locally, and with screen share I show the
application and explain what voronoi diagrams are, then give them the problem
statement of generating a voronoi diagram with only two points -- showing them
that it looks like a line that bisects the bounded plane between the two
points.

I ask if they prefer JS or Java, especially if they have JS experience, but so
far only one person has taken up the offer to code in JS...

Whatever it is I open the code for half of my screen, and paste the code for
the file they need to edit into the shared text editor that's taking up half of
the other screen half. (The actual application is in another browser window on
the last free space of the screen.) I walk through the examples, tell them to
type their solution in the shared editor, and any time they are ready they can
tell me to paste their code back into my running application to try running
their custom algorithm to see if the output is good. I'll frequently run the
Fortune solution for comparison even if they don't request it.

I can debug with them, add printlns, add junit tests, whatever, if they request
it. If there are trivial syntax issues I try to catch and fix them myself in
the shared editor, or let the IDE handle it, though some I'll leave to the
candidate for a moment to spot themselves...

When the candidate is in-person, I just let them use my laptop. This doesn't
seem to provide any advantages in practice.

## Solution guiding

Anyway now I'm going to add my notes about some solutions, before getting to
the scoring rubric. If you haven't solved this problem yourself yet, you should
take some time to do so before you give it. My own rule of thumb is to only
give a problem to a candidate that I myself have solved in less than half the
time the candidate gets.

Most candidates quickly see an approach to the problem that depends on the
midpoint. Some struggle with the concept of line slope after that, so be
prepared to give a simplified 7th grade algebra lesson to show how you would
graph e.g. f(x) = y = 2x + 1 and how that 2 fits the slope, 1 the y-intercept.
Though I haven't done it you might want to also show that you can give a random
point on the line and reduce it via the point-slope form to the y-intercept
form.

The distance formula is included as 1) a mental bump of something to ignore if
they're going for the midpoint approach and 2) some interesting solutions
involve only the distance formula. e.g. loop around the points of the bounding
box, if you find one that is equidistant (within some delta) to the site points
then you've found one of the endpoints of the graph's line segment. I don't think
this idea would come to someone without the overall context of the problem being
stated at first in terms of Voronoi cells.

Once the candidates have the midpoints and the perpendicular slope, they reach
another struggle zone. At this point I tend to point out that the code is
robust to drawing line segments that start/end out of bounds. With more
struggling I'll encourage the idea of outputting multiple line segments by
noting that they have a point that could be used for half of the final line at
least. More struggling, I point out that in one particular problem instance the
line is guaranteed to hit some point where x = 0, so what's y? But they need to
ultimately come to the final conclusion here on their own, and write the code,
and ideally realize they don't need two edges if they went down that route.

Using algebra to solve for some intersects with the bounding box can be a bit
tricky, so try to follow them closely and ask them to put their work in the
code comments if they're using a pencil once they're done. Being able to draw
on top of your own screen (with a wacom tablet or your mouse) is also useful,
in person you can just use a whiteboard or paper pad. But note that they can
just use the point-slope form as-is, if they start solving for x without a plan
to develop that into the necessary calculations to produce bounded edges, you
can perhaps suggest it's not needed yet or unless that's the goal.

I include the slope-intercept line equation since I think US-raised candidates
will remember it more, but for many candidates they have an equal non-memory of
either so I guide them to the simpler point-slope form.

If they are really struggling, see if you can guide them to solving the case
when the site points are on the same x or the same y. I forget about this
though since I normally don't try and bring them up until the end when we're
discussing edge cases. (i.e. "Are there any edge cases you'd like to test
or think you might not have accounted for?")

## Scoring

I'm developing a little script to auto-score based on test case results, but in
the meantime I'll document the rules the script will encapsulate. Maybe
sometime I'll post a histogram of scores now that I've given this problem to a
double-digit number of people.

If you're looking at commit history, this section was less tight earlier, I
realized recently after some more interviews that I need to tighten it up.  The
tightened version is consistent with the former metric in that it would rank
people the same, just more explicit on what's what. The final score is a number
of points where a higher number implies a better performance.

score = basic\_test\_cases * output\_format\_modifier + edge\_cases + bonus.

```
+-------------------------------------------------------------+--------+
| Basic test cases                                            | Points |
+=============================================================+=======:+
| Handles positive slope lines                                |      1 |
+-------------------------------------------------------------+--------+
| Handles negative slope lines                                |      1 |
+-------------------------------------------------------------+--------+
| Handles neither of the above fully but has at least         |        |
| half of a correct line edge extending out from the midpoint |    0.5 |
+-------------------------------------------------------------+--------+
| Handles same-y site points                                  |   0.25 |
| Handles same-x site points                                  |   0.25 |
+-------------------------------------------------------------+--------+
```

The meat of the score is made up of passing the basic test cases, i.e. solving
the problem statement. Failure to solve it at all will result in 0 points.
Almost solving it, e.g. only having half of the expected edge, nets 0.5.
The best score here is handling both slopes and the two edge cases for a total
of 2.5 points.

Given about 30 minutes, 1 point is the minimum I expect for any role.
Given about 45 minutes, 2 points is the minimum.

For higher roles, e.g. "senior" or "lead" titles, I don't have any hard
expectations but I don't think it's unreasonable to demand at least 2.25 points
over around 40 minutes. Anything less should make one wary.

I don't have solid thoughts on expectations for longer durations. I theorize
that given two hours someone could derive or find, understand, and implement a
brute-force solution for generating the diagram over N points by drawing pixels
or some other method, but have yet to have that theory tested.

```
+------------------------------------+----------+
| Output format modifiers            | Modifier |
+====================================+==========+
| Graph drawn with one output edge   | 1        |
| Graph drawn with two+ output edges | 0.5      |
| Graph drawn with pixels            | 0.5      |
+------------------------------------+----------+
```

If the person gets the full 2.5 points above for the basic solution, but does
so by outputting two edges from the midpoint instead of one edge, their score
is reduced to 1.25 points. Similarly if they just output the pixels of the
line. If they only get the partial half-edge, then whether you give them 0.5
points or 0.25 points depends on whether that's the only edge they output or
not.

```
+-------------------------------------------+--------+
| Bounding Box Edge Cases                   | Points |
+===========================================+=======:+
| Edge from Top to Right (TR) is bounded    |    0.1 |
| Edge from Bottom to Right (BR) is bounded |    0.1 |
| BL                                        |    0.1 |
| TL                                        |    0.1 |
| TB                                        |    0.1 |
| TB with same-y site points                |    0.1 |
| LR                                        |    0.1 |
| LR with same-x site points                |    0.1 |
| TL corner -> BR corner                    |    0.1 |
| BL corner -> TR corner                    |    0.1 |
+-------------------------------------------+--------+
```

The above covers the bounding box cases. Yes the same-x/same-y get counted
twice, this is just so the total of bounding box cases neatly adds up to 1
point. The max score now is 3.5 points.

```
+------------------------------------------------------------+--------+
| Bonus                                                      | Points |
+============================================================+=======:+
| Writes any unit test                                       |      1 |
| Handles bounding box being different from 0,0 to 800,600   |   0.25 |
| Handles 3 points                                           |      7 |
| Handles N points                                           |    inf |
| Clean code                                                 |      ? |
| Knowledge about language trivia like (x+y)/2 overflowing   |      ? |
| Verbal identification of edge cases and solutions for them |     ?? |
+------------------------------------------------------------+--------+
```

The bonus covers a few optional things that can affect the final score. I like
unit tests, so if the candidate writes one on their own accord that's worth a
point. If they don't hardcode the given boundary values but use the input
bounding box values, that's worth a bit. If they can extend their code to
handle 3 points, even if their two point code isn't fully complete, that's
worth 7 points aka it's basically just as good as handling the 2 point case and
its edge cases. If they can extend their code to handle N points, no matter the
strategy, that's basically an instant-win on this test.

Finally a handful of subjective or impossible-to-verify-later attributes are
highlighted that you might want to assign a value to if they are the sorts of
things that could make you reconsider the difference between hire/no-hire. For
example some people might verbalize the bounding box cases and a strategy to
address them, but if they don't put that in code, it's only worth like 0.001
points just to denote the fact that all else equal with a candidate who got the
same base score but didn't tackle (verbally or otherwise) the bounding box
cases I'll take the verbalizer. You might think that verbalizing is almost as
good as code though and thus award 0.8 points or something.

## Philosophical

So apart from having an objective point score to rank multiple people against
and that can be recalculated after-the-fact, what extra subjective signals do I
think this test can tell me about? Nothing very strongly, but if the person
doesn't have any code I can go review directly (and know they wrote it) and I
only have an hour with them then I think this gives me the most bang for
buck...

If I could just get away with telling about my/my team's product, gauging
whether the person can start immediately helping us with some pressing issues
or feature build out, and just chatting over a casual lunch before making the
call, that'd be just as fine.  But no, BigCo mentality necessitates this sort
of thing. And they're totally justified. For example, I had someone with a
Masters in Physics make it to an on-site interview where I gave this problem,
they got a 0 because they could not do anything. "This math level is above me."
Ok... When you don't control the whole process, you *need* to make sure the
candidate has been tested for ability to code *anything* at some point in the
process. The advice around FizzBuzz and
https://sites.google.com/site/steveyegge2/five-essential-phone-screen-questions
is still very relevant.

So this tells me if the candidate can code something at all. That's the big thing.

It tells me a bit how well they can handle jumping into code they didn't write
the entirety of and so don't (or can't) have all loaded in their head. They
need to reason about such code locally and know to ignore a lot of things.
This is a crucial skill for succeeding in large orgs with a lot of old
and legacy code.

It tells me a bit about their ability to take a visual problem and convert it
to code. Lots of business problems are like this.

It tells me a bit about their long-term memory. Come on everyone, it's
literally 7th-8th grade math concepts here, I'm not quizzing on equation recall
either since I give everything... That might have been a long time ago, but
come on... https://www.youtube.com/watch?v=IL3UCuXrUzE (This is job-relevant
since ideally you'll be at the company for a few years, it's valuable to the
company if you can remember relevant details of stuff you haven't touched in
years.)

It tells me a bit about what it'd be like to work with them. Could we pair
program on something even informally, like on a hackday project?

It gives me a sense of their inclination to math. Dijkstra wrote: "Besides a
mathematical inclination, an exceptionally good mastery of one's native tongue
is the most vital asset of a competent programmer."

While yes many programmers on the job don't ever use any math beyond boolean
logic, and math ability (never mind inclination) is not always a valid filter
(sometimes it is), I nevertheless think that if people have an "eww math"
reaction in general they're not going to be very good programmers and I don't
particularly want to work with them directly if I'm given any say. Some people
will die on the hill of awareness of the SOLID principles, Dijkstra's quote is
currently my hill. I'm not even *that* inclined to math, or that good at it,
but I'd rather work with those who are than those who aren't, all else equal.

I've written way too much about the topic of interviewing on HN, if you want
more thoughts. It mostly boils down to "As an interviewer I want to make the
process better than baseline, encourage others to think on how to do that too,
but also at least end up with something semi-helpful in determining the most
valuable signals in the time I have to make the correct hire/no-hire decision
or vote."

This project was also just kind of fun to make. :) And at least some people
I've given it to have expressed finding it interesting and not what they
expected but not in a bad way.

# License

This project with the exception of rhill-voronoi-core.js and the
be.humphreys.simplevoronoi folder is hereby released under the following terms.

Copyright (c) 2017-2019 Kevin Secretan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

The Software shall be used for Good, not Evil.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

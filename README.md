Source code for the *eTable* project for the Next Generation User Interfaces
course at the Vrije Universiteit Brussel (VUB). Using a kinect and a projector
hanging above the desk, we allow interaction on the desk.

# Prerequisites

* Java 8 (with JavaFX 8, should be included)
* Maven, to handle the libraries
* KinectTouch program to create events TUIO understands. Ensure KinectTouch is
  calibrated to match up with the projector output.

# Installing Libraries

This project uses maven, so the libraries are described in `pom.xml`. Install
them the usual way.

# Other Stuff

You'll want to update `twitter4j.properties` with your own API values to see
your own Twitter stream.

# Running the Program

We will here assume that you are using Eclipse. Start up KinectTouch and ensure
it has correctly started by checking the "Depth" window (when there is nothing
on the desk, there should be no white/red to be seen. Run the `main()` function
from the `Main` class in the `application` package.

Use the authorize button to handle the GMail login, this is best done in
advance. Afterwards, drag the window to the second screen (that is, the one
projected by the projector).

Use away!

# In Action

eTable can be seen in action at
[https://www.youtube.com/watch?v=6eGFGI88Ofg](https://www.youtube.com/watch?v=6eGFGI88Ofg).
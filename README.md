BSvsZP-Common-Java
==================

04-15-2014
==================================
4/18/2014 @ 9:00 pm
==================================
1) Download Greg's java library
2) Make sure that it is works properly
3) Remove all classId attribute from all the classes
4) Clean up the code
5) Add the previous modifications to this version
6) Test the library 
7) Change AgentInfo so strength cannot be negative
8) Added one more constructor to GetResources class
9) Added StatusReplyTester and ReadyReplyTester testers
10) Added one constructor to the AckNak class
11) Created EndGameTester
12) Added MessageEncodingSamples to test the message encoding in the MessageTester




==================================
4/17/2014
==================================
Error:
1) Updated StandardErrorNumbers
2) Updated the constructor

GameInfo
1) Update GameStatus by adding STARTING and STOPPING status

GameConfiguration
1) Added attributes NumberOfTicksRequiredToBuildAnExcuse, NumberOfTicksRequiredToBuildTwine, RefereeRegistrationMin and RefereeRegistrationMax.
2) Updated the constructor
3) Updated the value of some parameters
4) Update Encode and Decode methods


AgentInfo
1) Added Referee

EndGame
1) Added Winners
2) Update Encode and Decode methods

EndPoint
1) Update Match methods

AckNak
1) Added one constructor AckNak(Error error)

Reply
1) Update Create method


Tick class
1) Added forAgentId to the Tck.java class
2) Updated Tick's constructors and ComputeHashCode() method
3) Updated Tick.encode method 
4) Updated MinimumEncodingLength
5) Update Decode method
TickDelivery class
1) Updated MessageTypeId
   
==================================
4/15/2014
==================================
1) Added a "TryingToJoin" Status for AgentInfo.java
2) Added toString method to FieldLocation.java

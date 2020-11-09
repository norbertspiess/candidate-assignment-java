# Political and Postal communities in Switzerland
3AP assignment that evaluates basic java skills of a candidate.

##  Prerequisites :school_satchel:
To fulfil this assignment you will need Java 11 and a GitHub Account. You can use an IDE or source editor of your choosing.

## Requirements :mortar_board:
### Input data
This assignment source provides two .csv files in the [resource folder](src/main/resources/):
* __GDE.csv__
Lists all political communities in Switzerland.
Source: [Amtliches Gemeindeverzeichnis der Schweiz](https://www.bfs.admin.ch/bfs/de/home/grundlagen/agvch.html) 

* __PLZ6.csv__
Lists all postal communities in Switzerland.
Source: [Eidgenössisches Gebäude- und Wohnungsregister - Umwandlung von PLZ in BFS-Gemeindenummer](https://www.bfs.admin.ch/bfs/de/home/grundlagen/agvch/gwr-korrespondenztabelle.assetdetail.7226419.html) 

### :memo: Assignment 1
Create a fork of this repository

### :memo: Assignment 2
Analyze the data in the two .csv files and implement the model ``Application.java - initModel()`` that represents the consolidated data.

![Model](model.png)

Proposal of Java interfaces (Note: Relations are not yet specified, use this interfaces as a starting point)

:bulb: How you can easy correlate the 2 data sets? Use a unique key / id available in both data sets :smirk:

### :memo: Assignment 3
Implement all the other methods in ``Application.java`` that throw a runtime exception using your model / data structure that you have created in Assignment 1

### :memo: Assignment 4
Fromat your source code additions according [Google Style Guide for Java](https://github.com/google/styleguide)

### :memo: Assignment 5
When looking at the 2 CSV's, describe some other "interesting" facts you get out of your data set's.
- the plz 1004-0 has the most postal contribution to the political community of Lausanne
- Lausanne has 18 plz in total
- the district of "Bezirk Affoltern" has 21 postal communities, which one 
- the district "Verwaltungskreis Bern-Mittelland" has the most postal communities overall with 166
- ...

## Hand in the assignment :shipit:
If you are happy with your solution and all provided tests are passing please create a new pull request. Please put your full name in the PR comment, so we can map it to your application.

## Questions :question:
If you have any need help or have have any questions at all please don’t hesitate to contact us. We are here to help! :mailbox: <devs@3ap.ch>

# :tada: Good luck!! :tada:

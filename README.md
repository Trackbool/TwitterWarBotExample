# Twitter War Bot
Twitter cross-platform war bot coded in Java using the twitter4j library. Allows custom time intervals in which war status will be updated.
There is a compiled executable version for Windows (WarBotCompiled.zip) and the JAR for all platforms.

![alt text](https://i.gyazo.com/0c8e64ab50349dbbad52095b701072b6.png)

## Features

* You will be prompted to specify the name of the war.
* You will be prompted to specify the rate interval between turns (in seconds).
* You can specify intervals of time in which the bot will update the war status.
* You can specify a custom footer message for your bot (or leave it empty).
* [DEVELOPING - NOT YET AVAILABLE] You can specify custom quotes and add new translations without changing the source code directly.

## Usage

* Is required to install Java to make it work: https://www.java.com/es/download/
* You have to put your own Twitter tokens in the twitter4j.properties file.
* The "files/players.txt" contains the list of the players. The format of the file is: "Name;Nickname".
* The "files/time-intervals.txt" contains the list of the time intervals in which the bot is going to update the status. The format of the file is "19:56:33;19:58:00" (each interval in a new line and empty to execute all time).
* The "files/config.txt" contains the global config for the bot. The format of the file is "propertyName=value"

##
Adrián Fernández Arnal (@adrianfa5)<br />
Twitter: https://twitter.com/adrianfa5

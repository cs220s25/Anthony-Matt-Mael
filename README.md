


## Project Description

`This project is a personality questionnaire Discord bot designed to help 
form balanced and effective coding teams. Inspired by roles from the CSCI 120 course, 
the bot evaluates users’ strengths and preferences to assign them one of four 
team roles: Manager, Strategist, Debugger, or Speaker.
 These roles ensure that each team has a diverse skill set for efficient collaboration 
 and problem-solving. By using this bot,  users can create well-rounded teams for coding 
 projects in an engaging and interactive way.`

## System Diagram

![description](TeamWorkBotUML.png)

`	BotResponder handles interactions with users on Discord.
	QuestionManager manages the personality questionnaire, including presenting questions and collecting responses.
	RoleEnu represents the defined team roles (Manager, Strategist, Debugger, Speaker).
	SessionManager tracks users’ questionnaire sessions to ensure smooth and organized data flow.
	Storage utilizes RedisManager for efficient data storage and retrieval.`


## Usage

`Users interact with the bot using intuitive commands.
 The !start command begins the questionnaire,
  prompting users to answer four questions to determine their role. 
  Once completed, users can retrieve their assigned role anytime with !myrole or delete their questionnaire data using !delete. 
The !help command provides an overview of all available commands for easy navigation.`


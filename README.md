# MapMe
An android application which takes JSON file input from url, saves the information into SQLite database, shows location on map, implements job scheduling features using AlarmManager.

## Requirements / Features of the Application <br>
1: Create a list of emloyees by fetching data from server using the (http://anontech.info/courses/cse491/employees.json). 

2: Create a database of employees and store the data in database. Store a flag in persistence storage so the application doesn't fetch data again from server. Finally, setup your listview to read data from database.

3: Create an activity for each employee where employee's location will be shown in map. If location of an employee is not available in database, a dialog with yes no button will be shown to user for submitting emloyee's location. If the user clicks on yes current location of the device will be shown in map with a button to save the co-ordinates in database. Otherwise the activity will just show employee's location in map.  

4: Create a Marker with employee's name in map activity at employee's location (from DB). On click of that marker setup a timer using Alarm Manager by taking input of time (in seconds) from user. Show a toast with a message when the time is over.

Project Name:   Mp3 File Renamer

Author:         Morgan Farmer
GitProfile:     https://github.com/mFarme2
email:          morganFarmer2475@gmail.com

Language:       Java
Library used:   jid3lib-0.5.4 
                http://javamusictag.sourceforge.net/download.htm

Description:
The Mp3 File Renamer application was created by me out of necessity to have a uniform file naming scheme for all my songs. I decided to pour my efforts into an app that could do it for me instead of renaming all the songs myself.

Although I’m sure other file renamers are probably on the market and probably free, I figured it would be a good opportunity to exercise my coding skills. I decided to implement a MVC architecture because I wanted the application to be flexible and extensible. I wanted all the actions between the model and the view to go through the controller. 

############# VIEW ################
Uses an interface called Mp3Viewable to allow multiple types of JFrames
Current view available is Mp3NameChangerView.java. The view is coded in swing using a group layout. All the actionListeners are in the view as of right now but I may change that if possible.

########### CONTROLLER ############
The controller is all the business logic. The controller processes the files and sends updates too and from the model and views. The method update() sends an updateObject to the view and the view should update itself. I encapsulated the update information into one object so that it would be easy to extend or change. 

############# MODEL ###############
The model is where all the hard data is stored. The location of the rootDirectory file is stored in a text file so uppon opening we know where the user stores his Mp3 files. An arrayList of mp3 files are added from the rootDirectory for processing if needed. 

############ FORMATS ##############

## - NAME.fex    (Default)
### - NAME.fex
Name.fex

         Legend 
#      <=   track number digit
.fex   <=   file extention (usually mp3)
NAME   <=   track name

########### PROBLEMS ##############
Having trouble with the mp3 library being used as it only supports 1 or 2 ID3 formats.
Java Swing is not thread safe which may cause problems but is out of the scope of my initial project.
Multiple controllers may be made and try to manipulate data at the same time. May think about making controller or model a singleton.

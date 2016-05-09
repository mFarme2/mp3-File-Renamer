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

############ VIEW #################
The view is coded in swing using a group layout. All the actionListeners are in the view as of right now but I may change that if possible.

########## CONTROLLER #############
The controller is all the business logic. The controller processes the files and sends updates too and from the model and views.

############ MODEL ################
The model is where all the hard data is stored. The location of the rootDirectory file is stored in a text file so uppon opening we know where the user stores his Mp3 files. An arrayList of mp3 files are added from the rootDirectory for processing if needed. 

Mp3Renaming formats so far:

## - NAME.fex    (Default)
### - NAME.fex

         Legend 
#      <=   track number
.fex   <=   file extention (usually mp3)
NAME   <=   track name
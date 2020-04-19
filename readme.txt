Swapping from Internal Development to External (SPRR) Development
	
	Run Ant target 'refresh_files' to copy the most recent source files
	from the SRS project. This will also compile so be sure to check for
	any errors: there should be none in the SRSEXT project. If there are,
	you may need to add any new files and resolve dependencies.
adding another line
	
	Adding this line

 	CLOSE the SRS project to avoid class collisions.

 	Do a clean build of SRSEXT again from eclipse in case you need to debug

	Set SPRR to 'external' by modifying srsrcIXConstants.java to reflect
	the external settings.
	
	In Tomcat's /common/classes directory, swap the internal 'com' library
	for the external 'com' library
	
 	Set the appropriate context(s) in Tomcat's server.xml

 	Adding another line
 	
 	
 	
Swapping from External (SPRR) Development to Internal Development

	UNDO THE ABOVE but be sure to close the SRSEXT project
 	
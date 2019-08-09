
# JShot
JShot is a private image upload service which can not be crawled through easily like some others you can host yourself!  
It is easy to create an upload website for it and to secure it so only you can upload images to it.

### How to use
#### Setup
Use ```java -jar JShot.jar setup``` to create a security token and set the url of your webserver so JShot knows where to upload the images. You'll also get the upload.php created automatically to work only with your security token!  
Use ```java -jar JShot.jar clear``` to clear the current configuration and create a new security token and php file.
#### Upload an image
To upload an image simply open the jar file without any arguments. The program will automatically upload the image and put the url to view it in your clipboard.

### Using the same page on multiple computers
If you want to use the same JShot page on multiple computers simply copy the ```jshot.cfg``` in your user folder to the other computer and you are ready to go!

### Contribute
If you want to contribute keep the added code in the same style as the existing one and mark changes so they can be easily identified.

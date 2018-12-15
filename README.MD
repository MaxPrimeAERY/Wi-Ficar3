#Hello! This is the tutorial how to install this app on your Android Smartphone.<br>
#Let's get started!

#1. You need to download and install Android Studio for your PC<br>
#2. Open this project from a correct path.<br>
#3. Plug in your phone or install emulator(Nexus 5X recommended)<br>
#4. Select Run/Debug Configuration and in open window choose the Module app.<br>
#5. Run the program<br>
#6. Choose device on which the program will be installed<br>
#7. Wait for installing<br>
#8. ENJOY!<br>

[![Build Status](https://travis-ci.org/MaxPrimeAERY/Wi-Ficar3.svg?branch=master)](https://travis-ci.org/MaxPrimeAERY/Wi-Ficar3)
[![license](https://img.shields.io/github/license/MaxPrimeAERY/Wi-Ficar3.svg?maxAge=2592000)]()
[![Packagist](https://img.shields.io/github/license/MaxPrimeAERY/Wi-Ficar3.svg?maxAge=2592000)]()

Dockerizing project instruction for Windows!<br>
#1. First of all you need to turn on Hyper-V on your machine.<br>
```
Enable-WindowsOptionalFeature -Online -FeatureName Microsoft-Hyper-V –All <br>
bcdedit /set hypervisorlaunchtype auto <br>
```
#2. Create virtual network adapter<br>
#3. Install Docker. Delete VirtualBox separately. Replace docker-machine.exe with older version 0.15 (recommended)<br>
#4. Open CMD with Admin rights and input this line<br>
```
docker-machine create -d hyperv --hyperv-virtual-switch "Name_of_your_virtual_adapter" default <br>
```
Wait for installing. If you are stuck while downloading boot2docker.iso, copy it from Docker's folder and put it here<br>
C:/Users/YOUR_USER_NAME/.docker/machine/cache/boot2docker.iso<br>
```
docker-machine create --hyperv-boot2docker-url file://C:/Users/YOUR_USER_NAME/.docker/machine/cache/boot2docker.iso -d hyperv --hyperv-virtual-switch "Name_of_your_virtual_adapter" default<br>
BE SURE THAT COMMAND ABOVE IS WRITTEN IN ONE LINE <br>
```
#5. Enter this commands to use docker in CMD (You have to do it all the time when start CMD) <br>
```
docker-machine env --shell cmd default<br>
@FOR /f "tokens=*" %i IN ('docker-machine env --shell cmd default') DO @%i <br>
```
<br>
Now we can start building our Android App<br>
#1. I would recommend you to download Docker plugin for Android Studio(BE SURE YOU HAVE LAST VERSION OF IT)<br>
#2. Configure it (in Android Studio settings)<br>
In Tools you have to enter docker's TCP socket. Enter correct IP address with appropriate port. <br>
In Registry you will understand what to enter :) <br>
To enable Docker(like Terminal, Logcat, Build) go to "View-Tool Windows-Docker". If you don't have it, restart Android Studio. <br>
#3. Start docker. You will see empty Containers and Images <br>
#4. Go to my Dockerfile-Edit Deployment, add appropriate Tag and press Run. <br>
#5. Wait for downloading. It will take a while. <br>
#6. Run CMD as Admin. cd to your project location (dont forget about #5 from previous instruction)<br>
#7. Input this commands<br>
```
docker run --rm --volume=//C:/gradle/ --workdir=//C:/Users/Max/AndroidStudioProjects3/Wi-Ficar3 android-build:android-gradle gradle init //this command generates gradles files<br>
.\gradlew assembleDebug //this command builds apk <br>
.\gradlew installDebug //installs apk to your phone <br>
```
#8. That's it. ENJOY :) <br>
##ENVIRONMENT INSTALLATION


###Requirements
* Intellij IDEA v12+ or Android Studio > 
[Windows](http://dl.google.com/android/studio/android-studio-bundle-130.687321-windows.exe), 
[Mac OS X](http://dl.google.com/android/studio/android-studio-bundle-130.687321-mac.dmg), 
[Linux](http://dl.google.com/android/studio/android-studio-bundle-130.687321-linux.tgz")
* Android SDK >
[Windows](http://dl.google.com/android/android-sdk_r22.0.1-windows.zip), 
[Mac OS X](http://dl.google.com/android/android-sdk_r22.0.1-macosx.zip), 
[Linux](http://android-sdk_r22.0.1-linux.tgz")
* JDK 1.6+
* Maven 3


###Android SDK Installation
* **Launch the SDK Manager.**
If you've used the Windows installer to install the SDK tools, you should already have the Android SDK Manager open. Otherwise, you can launch the Android SDK Manager in one of the following ways:
On Windows, double-click the SDK Manager.exe file at the root of the Android SDK directory.
On Mac or Linux, open a terminal and navigate to the tools/ directory in the Android SDK, then execute android sdk.
The SDK Manager shows all the SDK packages available for you to add to your Android SDK. 
* **Install the following:**
 - All Tools
 - All api versions from API 14
 - All Extra

Once you've chosen your packages, click Install. The Android SDK Manager installs the selected packages into your Android SDK environment.


###Maven Configuration

Open your `~/.m2/settings.xml` and add the following lines
```
<profiles>
  <profile>
    <id>android</id>
    <properties>
      <android.sdk.path>/path/to/android/sdk</android.sdk.path>
    </properties>
  </profile>
</profiles>

<activeProfiles>
  <activeProfile>android</activeProfile>
</activeProfiles>
```

###Project installation
execute the following command lines in a terminal:
```
mkdir ~/XKE
cd ~/XKE/weather-xke
git clone https://github.com/mboudraa/weather-xke.git
git checkout origin develop

mvn clean install
```

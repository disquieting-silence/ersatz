ersatz
======

Ersatz is an automatic responder for SMS messages. The primary feature of `ersatz` is the ability to only respond to certain people with potentially tailored messages. There is also support for some basic templates, including an open maps integration. However, `ersatz` is nearly always in an unstable state, so it's best not to rely on it actually working.

The most recent change to `ersatz` gave it a dependency on `thedroid`, but it hasn't otherwise been updated in quite a long time. Also, the user interface is appalling, and prevents me from using it as much as I would otherwise. Aside from uncertainly about the library dependency, the functionality of `ersatz` mostly seems to work ... but the user interface must be fixed for it to have any usability.

Dependencies
------------

`ersatz` depends on the `thedroid` project. It expects to find it locally when generating the `ant` build for the mobile device. The relative location of the `thedroid` library is specified in the file `ant.properties`.

    # Note, you will need to change this to the location of the thedroid library on your system relative to this file.
    android.library.reference.1=../libs/thedroid


Installation
------------

### Configuration
Installing `ersatz` requires the Android SDK with the `android` executable on the `path`. Inside your `ersatz` checkout directory, type: 

    android update project --path . 

This will generate the `local.properties` and `proguard` configuration required. The `android update project` command also provides you with other project configuration options, such as the target Android OS. For a full list of configuration options, consult the [Android Developer Documentation](http://developer.android.com/tools/projects/projects-cmdline.html#UpdatingAProject). 

### Target Android OS

`ersatz` has been written to operate on any 2.2 OS of Android (Froyo). However, it is possible to build `ersatz` for a different target OS. As mentioned above, using `android update project` is the approach to specify the target OS. In order to see a list of OS that your Android tools currently support, type: 

    android list targets

The output of this will provide each supported OS with an `id`. This `id` should be specified as the value for the `--target` parameter in the `android update project` command. E.g.

    android update project --path . --target android-8


### Installation

If you have correctly linked to the `thedroid` library as shown above and configured your project correctly, then installation 'should' proceed. Installation and deployment to your system (connected device or running emulator) is achieved through the `ant` build tools. Specifically: 

    ant debug install

### Troubleshooting

Generating a build requires all involved projects to have a valid build script. If this is not the case, you will commonly see this error:

    sdk.dir is missing. Make sure to generate local.properties using 'android update project' or to inject it through an env var

This error can occur for either `ersatz` itself, or any dependent projects (such as `thedroid`). As the error message suggests, the solution is to execute `android update project` with the relevant parameters for the problem project. A full guide to updating projects via the command line can be found [here.](http://developer.android.com/tools/projects/projects-cmdline.html#UpdatingAProject)


### Acknowledgements

Mirror icon: http://icons.iconarchive.com/icons/greg-barnes/vampire-hunter/48/Mirror-icon.png

Other icons: http://findicons.com/pack/766/base_software (http://www.icons-land.com/)

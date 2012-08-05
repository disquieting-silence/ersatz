ersatz
======

Ersatz is an automatic responder for SMS messages. The primary feature of `ersatz` is the ability to only respond to certain people with potentially tailored messages. There is also support for some basic templates, including an open maps integration. However, `ersatz` is nearly always in an unstable state, so it's best not to rely on it actually working.

The most recent change to `ersatz` gave it a dependency on `thedroid`, but it hasn't otherwise been updated in quite a long time. Also, the user interface is appalling, and prevents me from using it as much as I would otherwise. Aside from uncertainly about the library dependency, the functionality of `ersatz` mostly seems to work ... but the user interface must be fixed for it to have any usability.

Dependencies
------------

`ersatz` depends on the `thedroid` project. It expects to find it locally when generating the `ant` build for the mobile device. The relative location of the `thedroid` library is specified in the file `ant.properties`.

    # Note, you will need to change this to the location of the thedroid library on your system relative to this file.
    android.library.reference.1=../libs/thedroid


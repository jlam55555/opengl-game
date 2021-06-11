# opengl-game

Following the [tutorial from ThinMatrix][tutorial]

---

### Build instructions
This project uses Maven 3, Java 1.8, and LWJGL 2. Newer versions of Java and LWLGL exist, but this is to follow the tutorial as closely as possible.

Note that LWJGL 2 requires both a Java library (JAR) files, and native library files (.dll/.so). The JAR files are handled by Maven, and the native files are included in the [native/](./native) directory and loaded in from the main class.

##### Initial setup:
```bash
$ mvn install
```

##### Build/run:
```bash
$ mvn compile exec:java
```
If you are having errors compiling/running, see [Notes](#notes).

##### Clean:
```bash
$ mvn clean
```

---

### Notes

##### Inconsistency detected by ld.so
If Maven is configured to use a newer Java version, running the executable may fail with the error:
```bash
Inconsistency detected by ld.so: dl-lookup.c: 105: check_match: Assertion `version->filename == NULL || ! _dl_name_match_p (version->filename, map)' failed!
```
Make sure you run with Java 1.8 -- set this setting in your IDE or set the `JAVA_HOME` environment variable to the appropriate Java installation location, e.g.:
```bash
$ JAVA_HOME=/usr/lib/jvm/java-8-openjdk/ mvn compile exec:java
```
For more info, see [this](https://stackoverflow.com/a/55849728/2397327).

[tutorial]: https://www.youtube.com/watch?v=VS8wlS9hF8E

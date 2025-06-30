# TUtils

[![Maven Central Version](https://img.shields.io/maven-central/v/one.tranic/t-utils)](https://central.sonatype.com/artifact/one.tranic/t-utils)
[![javadoc](https://javadoc.io/badge2/one.tranic/t-utils/javadoc.svg)](https://javadoc.io/doc/one.tranic/t-utils)

No need to install TLIB Base, need Java 17.

## Install
Please use shadow to remap TUtils to your own path to avoid conflicts with other libraries/plugins using TUtils.

`maven`

```xml
<dependency>
    <groupId>one.pkg</groupId>
    <artifactId>tiny-utils</artifactId>
    <version>[VERSION]</version>
</dependency>
```

`Gradle (Groovy)`
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'one.pkg:tiny-utils:[VERSION]'
}
```

`Gradle (Kotlin DSL)`
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("one.pkg:tiny-utils:[VERSION]")
}
```
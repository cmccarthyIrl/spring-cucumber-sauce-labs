# Spring Cucumber - Travis CI - Browsestack

[![Build Status](https://travis-ci.com/cmccarthyIrl/spring-cucumber-travis-ci-browser-stack.svg?branch=master)](https://travis-ci.com/cmccarthyIrl/spring-cucumber-travis-ci-browser-stack) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/1047241f8b9542b7a6c53586f3117982)](https://www.codacy.com/gh/cmccarthyIrl/spring-cucumber-travis-ci-browser-stack/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cmccarthyIrl/spring-cucumber-travis-ci-browser-stack&amp;utm_campaign=Badge_Grade)
 
# Index

<table> 
<tr>
  <th>Start</th>
  <td>
    | <a href="#maven">Maven</a> 
    | <a href="#quickstart">Quickstart</a> | 
  </td>
</tr>
<tr>
  <th>Run</th>
  <td>
    | <a href="#troubleshooting">Troubleshooting</a>    |
  </td>
</tr>
<tr>
  <th>Advanced</th>
  <td>
    | <a href="#contributing">Contributing</a> |
    </td>
</tr>
</table>
    
# Maven

The Framework uses [Sauce Labs](https://mvnrepository.com/artifact/com.saucelabs/sauce_junit) `<dependencies>`:

```xml
<dependecies>
    ...
    <dependency>
        <groupId>com.browserstack</groupId>
        <artifactId>browserstack-local-java</artifactId>
        <version>1.0.3</version>
    </dependency>
    ...
</dependecies>
```
> To integrate the framework with Sauce Labs set the following values in the `AbstractPage.class`  

```java
     public static String username = System.getenv("BROWSERSTACK_USERNAME");
     public static String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
```
# Quickstart

- [Spring Cucumber Test Harness](https://github.com/cmccarthyIrl/spring-cucumber-test-harness) - `Recommended`

# Troubleshooting

- Execute the following commands to resolve any dependency issues
    1. `cd ~/install directory path/spring-cucumber-travis-ci-browser-stack`
    2. `mvn clean install -DskipTests` 
    
# Contributing

Spotted a mistake? Questions? Suggestions?

[Open an Issue](https://github.com/cmccarthyIrl/spring-cucumber-travis-ci-browser-stack/issues)



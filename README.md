# Redbay WebServices Client (SOAP)

Please consult Redbay Integration Platform documentation to obtain details on the Redbay API calls.

# Build
This project requires Java JDK 11 and is not yet released on Maven Central. To build locally run the bellow:

    mvn clean install

The tooling dependencies are defined in [ASDF .tool-versions file](.tool-versions).  

# Usage
Add this dependency to your project `pom.xml`:
```xml
<dependency>
    <groupId>pl.redbay</groupId>
    <artifactId>redbay-ws-client</artifactId>
    <version>1.0.0</version>
</dependency>
```
# Development usage

There is interactive JRuby client available to test calls:

```bash
./dev/redbay-client.rb rb-client.config.properties
```

See [RedbayClientTest](src/test/java/pl/redbay/ws/client/RedbayClientTest.java) for an example invocations.

# License

The source code of this project is licensed under [Apache License 2.0](LICENSE.txt)
# Redbay WebServices Client (SOAP)

Please consult Redbay Integration Platform documentation to obtain details on the Redbay API calls.

# Build
This project requires Java JDK 11 and is not yet released on Maven Central. To build locally run the below:

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

There is interactive JRuby client available to test calls.
For the first time install JRuby and `pry` gem.

```bash
asdf plugin-add ruby
JAVA_HOME=$(asdf where java) asdf install ruby
gem install pry --no-ri --no-rdoc
```
Then run the client script:
```bash
./dev/redbay-client.rb rb-client.config.properties
```

Example invocation:
```
[1] pry(#<RedbayCli>)> a = api.getProductsChanges(ticket, LocalDateTime.of(2000, 1, 1, 0, 0))
=> #<Java::PlRedbayWsClientTypes::ArrayOfChanges:0x123307c4>
[2] pry(#<RedbayCli>)> a.items.size
=> 2928
```

See [RedbayClientTest](src/test/java/pl/redbay/ws/client/RedbayClientTest.java) for invocation examples.

# License

The source code of this project is licensed under [Apache License 2.0](LICENSE.txt)
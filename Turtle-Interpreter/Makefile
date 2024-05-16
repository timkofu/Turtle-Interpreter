test:
	@mvn test

run:
	@mvn compile && mvn package && java -jar -XX:+UseZGC -XX:+ZGenerational target/Turtle-Interpreter-0.1.0.jar

upgrade:
	@mvn release:update-versions
	@mvn versions:use-latest-releases

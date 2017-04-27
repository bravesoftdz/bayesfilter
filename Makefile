clean:
	mvn clean

catcmd/target/catcmd-1.0-SNAPSHOT-jar-with-dependencies.jar:
	mvn install

run: catcmd/target/catcmd-1.0-SNAPSHOT-jar-with-dependencies.jar
	java -jar catcmd/target/catcmd-1.0-SNAPSHOT-jar-with-dependencies.jar catcmd/data/

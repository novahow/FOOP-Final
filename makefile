run:
	javac -cp . -sourcepath src -d out/ src/*.java
	java -cp out/ Main

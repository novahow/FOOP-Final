all:
	javac -cp . -sourcepath src -d out/ src/*.java
	java -Xmx2g -cp out/ Main

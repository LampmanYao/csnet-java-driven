JAVAC = javac
all:
	$(JAVAC) Test.java

clean:
	rm -f *.class
	rm -f */*/*.class


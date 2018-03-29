GEN=OscilloscopeMsg.java Constants.java
DEP=Dependencies/
CLS=Classes/
JVA=Java/

all: main.jar

main.jar: Main.class
	jar cf $(CLS)$@ $(CLS)*.class

OscilloscopeMsg.java: Oscilloscope/Oscilloscope.h
	mig -target=null -java-classname=OscilloscopeMsg java Oscilloscope/Oscilloscope.h oscilloscope -o $@

Constants.java: Oscilloscope/Oscilloscope.h
	ncg -target=null -java-classname=Constants java Oscilloscope/Oscilloscope.h NREADINGS DEFAULT_INTERVAL -o $@

Main.class: $(wildcard *.java) $(GEN)
	javac -d $(CLS) -cp .:$(DEP)* *.java

clean:
	rm -f *.class $(GEN)

veryclean: clean
	rm main.jar

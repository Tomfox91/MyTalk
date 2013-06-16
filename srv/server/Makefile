.SUFFIXES :
MAKEFLAGS += --no-builtin-rules

SRC := $(shell find src/java -name \*.java)
TESTSRC := $(shell find test -name \*.java)
TESTCLASSES := $(shell cd test/; find it -name \*Test.java | cut -d '.' -f 1 | tr / .)
JAVAC := javac -encoding UTF8
JAVATEST := java -XX:MaxPermSize=128M
ANT := ant
AUDAODIR := audao-build
BUILDDIR := build/WEB-INF/classes
LIBDIR := build/WEB-INF/lib
DBDIR := db
TESTBUILDDIR := test-build
COMPILECLASSPATH := $(shell find ../dep ../lib -name \*.jar | tr '\n' :):audao-build/audao-hsql.jar
TESTCOMPILECLASSPATH := $(shell find ../dep ../lib ../tool -name \*.jar | tr '\n' :):audao-build/audao-hsql.jar:build/WEB-INF/classes/
TESTEXECUTECLASSPATH := $(shell find ../dep ../lib ../tool -name \*.jar | tr '\n' :):build/WEB-INF/classes:audao-build/audao-hsql.jar:test-build

.PHONY: all compile test test-compile test-run copy war clean

all: compile war

$(AUDAODIR)/audao-hsql.jar: DB-schema.xml
	$(ANT) audao

compile: copy $(SRC) $(AUDAODIR)/audao-hsql.jar
	$(JAVAC) -d $(BUILDDIR) -classpath $(COMPILECLASSPATH) $(SRC)

copy:
	mkdir -p $(BUILDDIR) $(LIBDIR) $(AUDAODIR) $(TESTBUILDDIR)
	cp -rv web/* build/
	cp -rv ../lib/* $(LIBDIR)

test: test-compile test-run

test-compile: compile $(AUDAODIR)/audao-hsql.jar
	$(JAVAC) -d $(TESTBUILDDIR) -classpath $(TESTCOMPILECLASSPATH) $(TESTSRC)
	
test-run: test-compile
	$(JAVATEST) -cp $(TESTEXECUTECLASSPATH) org.junit.runner.JUnitCore $(TESTCLASSES) 2>/dev/null
	
war: compile
	cp $(AUDAODIR)/audao-hsql.jar $(LIBDIR)
	jar cf mytalk.war -C build . 
	
clean:
	rm -rf build $(AUDAODIR) $(TESTBUILDDIR) $(DBDIR) mytalk.war

.SUFFIXES :
MAKEFLAGS += --no-builtin-rules

CHROME := /usr/bin/google-chrome
FILEJS := public_html/js/libs/RTCMultiConnection-v1.2.js $(shell find public_html/js -name '???-*.js' | sort)
LIBJS := $(shell find public_html/js/libs -name '*.js' -and -not -name 'RTCMultiConnection*')
CLOSCOMP := ../tool/closure-compiler.jar
JSTD := ../tool/JsTestDriver-1.3.5.jar
INSTDIR := ../../srv/server/web

.PHONY : all test compile install clean

all : compile

test :
	mkdir -p build/report 
	java -jar $(JSTD) --port 9876 --server http://localhost:9876 \
		--browser $(CHROME) --tests all --config config/jsTestDriver.conf \
		--raiseOnFailure true --basePath .

compile : public_html/build/pt-clt.js

install : compile
	mkdir -p $(INSTDIR)/html $(INSTDIR)/js
	cp -rv public_html/css public_html/img $(INSTDIR)
	cp -v public_html/index.html $(INSTDIR)
	cp -v public_html/html/MyTalk.html $(INSTDIR)/html
	cp -v public_html/build/pt-clt.js $(INSTDIR)/js
	cp -v $(LIBJS) $(INSTDIR)/js

public_html/build/pt-clt.js : $(FILEJS)
	mkdir -p public_html/build
	java -jar $(CLOSCOMP) --js_output_file $@ $(FILEJS)
	
clean :
	rm -rf public_html/build

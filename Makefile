.SUFFIXES :
MAKEFLAGS += --no-builtin-rules

.PHONY: compile compile-srv compile-clt test-srv test-clt clean

compile: compile-srv compile-clt

compile-srv: compile-clt
	@echo "\n\n\n\n\n\t\t\tCOMPILAZIONE DEL SERVER\n\n"
	$(MAKE) -w -C srv/server war
	
compile-clt:
	@echo "\n\n\n\n\n\t\t\tCOMPILAZIONE DEL CLIENT\n\n"
	$(MAKE) -w -C clt/client install

test: test-srv test-clt

test-srv: compile-srv
	@echo "\n\n\n\n\n\t\t\tTEST DEL SERVER\n\n"
	$(MAKE) -w -C srv/server test
	
test-clt: compile-clt
	@echo "\n\n\n\n\n\t\t\tTEST DEL CLIENT\n\n"
	$(MAKE) -w -C clt/client test


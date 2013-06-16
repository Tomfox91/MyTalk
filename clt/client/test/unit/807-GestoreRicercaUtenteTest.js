describe("GestoreRicercaUtente", function(){


    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;

        var t,m;

        var socket = new Class ({

            invia : function(e) {
                t = e.tipo;
                m = e.evento.pattern;

            }

        });



        var dispatcher = new Class ({

            getSocket : function() {
                return new socket();
            }

        });

        PT.CONTROLLER.getInstanceDispatcher = function() {
            return new dispatcher();
        };

        var orig_element = document.getElementById;
        
        document.getElementById = function(id) {
            if(id == 'cercaAmiciInput') 
                return { value : 'chetriste@mt.it'};

        };

        // FINE PREPARAZIONE

        new PT.CONTROLLER.G.GestoreRicercaUtente().esegui();
        expect(t).toBe("RicercaUtenti");
        expect(m).toBe("chetriste@mt.it");


        // PULIZIA

        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        document.getElementById = orig_element;
        delete orig_element;
        delete dispatcher;
        delete socket;
        delete t;
        delete m;

        // FINE PULIZIA
    });


});



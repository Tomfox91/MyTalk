describe("GestoreRichiestaDomandaSicurezza", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t,m;
        
        var socket = new Class ({
            
            invia : function(e) { 
                t = e.tipo;
                m = e.evento.email;
                        
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
            if(id == 'lstem')
                return { value : 'mytalk@polaris.it'};


        };
        
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreRichiestaDomandaSicurezza().esegui();
        expect(t).toBe("RichiestaDomandaSicurezza");
        expect(m).toBe("mytalk@polaris.it");

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


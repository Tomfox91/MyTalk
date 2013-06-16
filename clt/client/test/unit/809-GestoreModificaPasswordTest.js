describe("GestoreModificaPassword", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t,v,n;
        
        var socket = new Class ({
            
            invia : function(e) { 
                t = e.tipo;
                v = e.evento.vecchia;
                n = e.evento.nuova;
                        
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

            if(id == 'inputOldPsw') 
                return { value : 'dsadsa'};
            if(id == 'inputNewPsw')
                return { value : 'hfd546'};



        };
        
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreModificaPassword().esegui();
        expect(t).toBe("ModificaPassword");
        expect(v).toBe("dsadsa");
        expect(n).toBe("hfd546");

        // PULIZIA
        
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        document.getElementById = orig_element;
        delete orig_element;
        delete dispatcher;
        delete socket;
        delete t;
        delete v;
        delete n;
            
        // FINE PULIZIA
    });
    
        
});



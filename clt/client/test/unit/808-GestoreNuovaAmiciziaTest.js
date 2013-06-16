describe("GestoreNuovaAmicizia", function(){

   
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
            
        var email = 'sistema@mt.it';
        
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreNuovaAmicizia().esegui(email);
        expect(t).toBe("NuovaAmicizia");
        expect(m).toBe("sistema@mt.it");
 

        // PULIZIA
        
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        delete dispatcher;
        delete socket;
        delete t;
        delete m;
            
        // FINE PULIZIA
    });
    
        
});



describe("GestoreRecuperaPassword", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t,r,n,m;
        
        var socket = new Class ({
            
            invia : function(e) { 
                t = e.tipo;
                r = e.evento.risposta;
                n = e.evento.nuova;
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

            if(id == 'risp') 
                return { value : 'kjluhghgfd'};
            if(id == 'pwdn')
                return { value : 'cicciabubu**'};
            if(id == 'lstem') 
                return { value : 'polaris@mt.it'};


        };
        
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreRecuperaPassword().esegui();
        expect(t).toBe("RecuperaPassword");
        expect(r).toBe("kjluhghgfd");
        expect(n).toBe("cicciabubu**");
        expect(m).toBe("polaris@mt.it");

        // PULIZIA
        
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        document.getElementById = orig_element;
        delete orig_element;
        delete dispatcher;
        delete socket;
        delete t;
        delete r;
        delete n;
        delete m;
            
        // FINE PULIZIA
    });
    
        
});

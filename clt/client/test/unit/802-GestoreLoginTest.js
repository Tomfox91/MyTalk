describe("GestoreLogin", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t,m,p;
        
        var socket = new Class ({
            
            invia : function(e) { 
                t = e.tipo;
                m = e.evento.email;
                p = e.evento.password;        
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
            if(id == 'email')
                return { value : 'ciao@prova.it'};
            if(id == 'pass') 
                return { value : 'password'};

        };
        
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreLogin().esegui();
        expect(t).toBe("Login");
        expect(m).toBe("ciao@prova.it");
        expect(p).toBe("password");
    
        // PULIZIA
        
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        document.getElementById = orig_element;
        delete orig_element;
        delete dispatcher;
        delete socket;
        delete t;
        delete m;
        delete p;
            
        // FINE PULIZIA
    });
    
        
});


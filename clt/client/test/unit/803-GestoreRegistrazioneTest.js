describe("GestoreRegistrazione", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t,m,u,p,d,r;
        
        var socket = new Class ({
            
            invia : function(e) { 
                t = e.tipo;
                m = e.evento.email;
                u = e.evento.username;
                p = e.evento.password;
                d = e.evento.domanda;
                r = e.evento.risposta;
                        
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
            if(id == 'emailR')
                return { value : 'test@polaris.it'};
            if(id == 'user') 
                return { value : 'Minnie'};
            if(id == 'psw') 
                return { value : 'PassworD'};
            if(id == 'domanda') 
                return { value : 'kvljb,;/*adsfkjvhers?'};
            if(id == 'risposta') 
                return { value : 'vcnmbxfdx*/-bguirejhg'};

        };
        
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreRegistrazione().esegui();
        expect(t).toBe("Registrazione");
        expect(m).toBe("test@polaris.it");
        expect(u).toBe("Minnie");
        expect(p).toBe("PassworD");
        expect(d).toBe("kvljb,;/*adsfkjvhers?");
        expect(r).toBe("vcnmbxfdx*/-bguirejhg");
        // PULIZIA
        
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        document.getElementById = orig_element;
        delete orig_element;
        delete dispatcher;
        delete socket;
        delete t;
        delete m;
        delete u;
        delete p;
        delete d;
        delete r;
            
        // FINE PULIZIA
    });
    
        
});


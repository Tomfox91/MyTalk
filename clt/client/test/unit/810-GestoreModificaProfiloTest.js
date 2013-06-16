describe("GestoreModificaProfilo", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t,u,s;
        
        var socket = new Class ({
            
            invia : function(e) { 
                t = e.tipo;
                u = e.evento.username;
                s = e.evento.stato;
                        
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
            
       User = new Class ({
            getNome : function () {return "uhbugv";},
            getStato : function () {return "Happy";}
       
       });
       
        
        var orig_registro = PT.MODEL.getInstanceRegistro;
        
        Regi = new Class ({
            getProprioUtente : function () { return (new User); }
        
        });
        
        PT.MODEL.getInstanceRegistro = function () {
            return new Regi();
        };
        
        
       
       
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreModificaProfilo().esegui();
        expect(t).toBe("ModificaProfilo");
        expect(u).toBe("uhbugv");
        expect(s).toBe("Happy");

        // PULIZIA
        
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        delete dispatcher;
        delete socket;
        PT.MODEL.getInstanceRegistro = orig_registro;
        delete orig_registro
        delete t;
        delete u;
        delete s;
        delete regi;
            
        // FINE PULIZIA
    });
    
        
});





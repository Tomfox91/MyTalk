describe("Rimuovi Amico test", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_reg = PT.MODEL.getInstanceRegistro;
        
        var registro = jasmine.createSpyObj('registro', ['rmUtente']);
        
        PT.MODEL.getInstanceRegistro = function () {
            return registro;
        }
        
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t,e;
        
        var socket = new Class ({
            
            invia : function(ex) { 
                t = ex.tipo;
                e = ex.evento.email;
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
            
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreRimuoviAmico().esegui("cecco@gmail.com");
        expect(t).toBe("EliminaAmicizia");
        expect(e).toBe("cecco@gmail.com");
    
        // PULIZIA
        
        PT.MODEL.getInstanceRegistro = orig_reg;
        delete orig_reg;
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        delete dispatcher;
        delete socket;
        delete t;
        delete e;
            
        // FINE PULIZIA
    });
    
        
});


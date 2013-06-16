describe("GestoreLogout", function(){

   
    it("test", function() {
        // PREPARAZIONE
        //PT.CONTROLLER.getInstanceDispatcher().getSocket().invia(e);
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t;
        
        var socket = new Class ({
            
            invia : function(e) { 
                t = e.tipo;           
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
        
        var orig_updater = PT.VIEW.getInstanceUpdater;
        
        var orig_ga = PT.VIEW.GestoreAccount;
        
        var GestoreAccount = jasmine.createSpyObj('GestoreAccount', ['logout']);
        
        PT.VIEW.GestoreAccount = function() {
                                return GestoreAccount;
                            };
        
                    
        // FINE PREPARAZIONE       
        
        new PT.CONTROLLER.G.GestoreLogout().esegui();
        
       
        expect(t).toBe("Logout");
        expect(GestoreAccount.logout).toHaveBeenCalled();
        

        // PULIZIA
        
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        PT.VIEW.GestoreAccount = orig_ga;
        delete orig_ga;
        delete orig_dispatcher;
        delete dispatcher;
        delete socket;
        delete t;
        PT.VIEW.getInstanceUpdater = orig_updater;
        delete orig_updater;
        delete obje;
        
            
        // FINE PULIZIA
    });
    
        
});





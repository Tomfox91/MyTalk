describe("GestoreDomandaSicurezza", function(){
    
    var orig_Account = PT.VIEW.GestoreAccount; 
    
    var ac = jasmine.createSpyObj('ac', ['notificaDomanda']);

    var dom = 'Qual è il nome del tuo primo animale domestico?';
    
    beforeEach(function() {
      
        PT.VIEW.GestoreAccount = function() {
        return ac;
        };
    });

   afterEach(function() {
        PT.VIEW.GestoreAccount = orig_Account;
    });
    
   
    
    it("test", function() {
        
        new PT.CONTROLLER.G.GestoreDomandaSicurezza().esegui({
             
            domanda: dom
                      
         });
         
        //controllo se è stato chiamato il notificaEsito nel modo corretto
         expect(ac.notificaDomanda).toHaveBeenCalledWith(dom);
        
    });
        
});





describe("GestoreListaAggiungi", function(){
    
    var orig_getInstanceRegistro = PT.MODEL.getInstanceRegistro;
    
    var registro = jasmine.createSpyObj('registro', ['rmPendente', 'setUtente']);
    
    beforeEach(function() {
        PT.MODEL.getInstanceRegistro = function() {
            return registro;
        };
    });
    
    afterEach(function() {
        PT.MODEL.getInstanceRegistro = orig_getInstanceRegistro;
        registro = orig_getInstanceRegistro;
    });
    
    it("test", function() {
        
        
        new PT.CONTROLLER.G.GestoreListaAggiungi().esegui({
            email: 'ciao@prova.it',
            username: 'pippo',
            stato: 'offline'
                     
        });
        
        
        //controllo se è stato chiamato il setUtente nel modo corretto
         expect(registro.setUtente).toHaveBeenCalledWith("ciao@prova.it","offline","pippo");
         expect(registro.rmPendente).toHaveBeenCalledWith("ciao@prova.it");
        
               
    });
        
});



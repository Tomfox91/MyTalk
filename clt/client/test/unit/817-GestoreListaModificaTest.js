describe("GestoreListaModifica", function(){
    
    var orig_getInstanceRegistro = PT.MODEL.getInstanceRegistro;
    
    var registro= jasmine.createSpyObj('registro', ['modificaNomeUtente','modificaStatoUtente']);
    
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
        
        
        new PT.CONTROLLER.G.GestoreListaModifica().esegui({
            email: 'ciao@prova.it',
            username: 'pippo',
            stato: 'offline'
        });
        
        
         expect(registro.modificaNomeUtente).toHaveBeenCalledWith("pippo","ciao@prova.it");
         expect(registro.modificaStatoUtente).toHaveBeenCalledWith("offline","ciao@prova.it");
         
        
               
    });
        
});



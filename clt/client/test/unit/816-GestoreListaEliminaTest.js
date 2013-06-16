describe("GestoreListaElimina", function(){
    
    var orig_getInstanceRegistro = PT.MODEL.getInstanceRegistro;
    
    var registro = jasmine.createSpyObj('registro', ['rmUtente']);
    
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
        
        
        new PT.CONTROLLER.G.GestoreListaElimina().esegui({
            email: 'ciao@prova.it'
        });
        
        
        //controllo se è stato chiamato il rmUtente nel modo corretto
         expect(registro.rmUtente).toHaveBeenCalledWith("ciao@prova.it");
         
        
               
    });
        
});



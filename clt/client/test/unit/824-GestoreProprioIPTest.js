describe("Gestore Proprio IP", function(){

   
    it("test", function() {
        var orig_reg = PT.MODEL.getInstanceRegistro;
        
        var registro = jasmine.createSpyObj('registro', ['setProprioIP']);
        
        PT.MODEL.getInstanceRegistro = function () {
            return registro;
        };

        new PT.CONTROLLER.G.GestoreProprioIP().esegui({ ip : "11.11.11.00" });

        expect(registro.setProprioIP).toHaveBeenCalledWith("11.11.11.00");

        PT.MODEL.getInstanceRegistro = orig_reg;
        delete orig_reg;

    });
    
        
});


describe("GestoreEsitiRicerca", function(){
     
    var orig_Updater = PT.VIEW.getInstanceUpdater; 
    
    var up = jasmine.createSpyObj('up', ['visualizzaRicerca']);
     
    var map = {};
    map['email@prova.it'] = 'Pippo';
    map['email2@prova.it'] = 'Pluto';
    
    beforeEach(function() {
         
        PT.VIEW.getInstanceUpdater = function() {
        return up;
        };       
    });
    
    
    afterEach(function() {
        PT.VIEW.getInstanceUpdater = orig_Updater;
    });
    
  
    
    it("test", function() {
        new PT.CONTROLLER.G.GestoreEsitiRicerca().esegui({
             mappa: map
                      
         });
        //controllo se è stato chiamato il visualizzaRicerca nel modo corretto
         expect(up.visualizzaRicerca).toHaveBeenCalledWith(map);
        
    });
        
});


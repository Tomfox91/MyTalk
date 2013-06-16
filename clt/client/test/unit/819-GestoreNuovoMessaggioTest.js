describe("GestoreNuovoMessaggio", function() {

    it("test", function() {        
        
        var updater = jasmine.createSpyObj('updater', ['visualizzaMessaggioSegreteria']);
        
        var orig_upd = PT.VIEW.getInstanceUpdater;
        PT.VIEW.getInstanceUpdater = function () {
            return updater;
        };
        
        var a = new PT.CONTROLLER.G.GestoreNuovoMessaggio;
        a.esegui({
            mittente: 'Asdrubale',
            messaggio: 'Ciao, ti ho cercato su MyTalk.'
        });
        
        expect(updater.visualizzaMessaggioSegreteria).toHaveBeenCalledWith('Asdrubale', 'Ciao, ti ho cercato su MyTalk.');
        
        PT.VIEW.getInstanceUpdater = orig_upd;
        delete orig_upd;
        
    });

});



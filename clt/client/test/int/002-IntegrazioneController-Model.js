describe("integrazioneController-Model", function() {
    var orig_PT = PT;

    beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
    });
    afterEach(function() {
        PT = orig_PT;
    });
    //gestore ListaAggiungi
    
    it("listaAggiungi", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ListaAggiungi", evento: {email: "e@ma.il", stato: "Online", username: "nome"}});
        expect(PT.MODEL.getInstanceRegistro().getListaPendenti()["e@ma.il"]).toBeUndefined();
        expect(PT.MODEL.getInstanceRegistro().getListaUtenti()["e@ma.il"]).not.toBeNull();
        expect(PT.MODEL.getInstanceRegistro().listaUtenti["e@ma.il"].getNome()).toEqual("nome");
        
    });
    
    //gestore ListaElimina
    
    it("listaElimina", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ListaAggiungi", evento: {email: "e@ma.il", stato: "Online", username: "nome"}});
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ListaElimina", evento: {email: "e@ma.il"}});
        expect("null").toEqual(typeOf(PT.MODEL.getInstanceRegistro().getListaPendenti()["e@ma.il"]));
        expect(PT.MODEL.getInstanceRegistro().getListaUtenti["e@ma.il"]).toBeUndefined();
    });
    
    //gestore ListaModifica
    it("listaModifica", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ListaAggiungi", evento: {email: "e@ma.il", stato: "Online", username: "nome"}});
        
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ListaModifica", evento: {email: "e@ma.il", username: "nuovonome", stato: "Occupato" }});
        
//PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ListaModifica", evento: {email: "e@ma.il", stato: "Occupato", username: "nuovonome"}});
        expect(PT.MODEL.getInstanceRegistro().getListaUtenti()["e@ma.il"].getNome()).toEqual("nuovonome");
        expect(PT.MODEL.getInstanceRegistro().getListaUtenti()["e@ma.il"].getStato()).toEqual("Occupato");
    });
    
    //GestoreNuovaRichiestaAmicizia
    it("nuovaRichiestaAmicizia", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ListaAggiungi", evento: {email: "e3@ma.il", stato: "Online", username: "nome"}});
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "NuovaRichiestaAmicizia", evento: {email: "e@ma.il", stato: "Online", username: "nome"}});
        expect(PT.MODEL.getInstanceRegistro().getListaPendenti("e@ma.il")).not.toBeNull();
    });
    //gestoreProprioUtente
    it("proprioUtente", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ProprioUtente", evento: {email: "e@ma.il", stato: "Online", username: "nome"}});
        expect(PT.MODEL.getInstanceRegistro().getProprioUtente()).not.toBeNull();
    });
    //gestoreProprioIP
    it("proprioIP", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ProprioIP", evento: {ip: "1.1.1.1"}});
        expect(PT.MODEL.getInstanceRegistro().getProprioIP()).not.toBeNull();
    });

});



describe("integrazioneController-View", function() {
    var orig_PT = PT;
    var gestore;

    beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
        PT.VIEW.GestoreAccount = function() {
                gestore = jasmine.createSpyObj("gestore", ["effettuaAccesso","notificaDomanda"]);
                return gestore;
        };
        PT.VIEW.GestoreAccount();
         window.bootbox = jasmine.createSpyObj("bootbox", ["alert"]);
    });
    afterEach(function() {
        PT = orig_PT;
    });

    //gestore Domanda Sicurezza
    it("domandaSicurezza", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "DomandaSicurezza", evento: {domanda: "domanda"}});
        expect(gestore.notificaDomanda).toHaveBeenCalled();
    });

//gestore Esiti Ricerca
    it("esitiRicerca", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "EsitiRicerca", evento: {mappa: {email: "nome"}}});
        expect($("#esiti")).toContain("a[href$='#email']");

    });
    //gestore NuovoMessaggio

    it("nuovoMessaggio", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "NuovoMessaggio", evento: {mittente: "mittente", messaggio: "messaggio"}});
        expect($("#msg p")[0]).toContainText("Mittente: mittente");
        expect($("#msg p")[1]).toContainText("messaggio");
    });
    //gestore Operazione

    it("operazione", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "Operazione", evento: {originale : "Registrazione",riuscita: true}});
        expect(window.bootbox.alert).toHaveBeenCalled();
    });
    //gestoreProprioUtente
    it("proprioUtente", function() {
        PT.CONTROLLER.getInstanceDispatcher().dispatch({tipo: "ProprioUtente", evento: {email: "e@ma.il", stato: "Online", username: "nome"}});
         expect(gestore.effettuaAccesso).toHaveBeenCalled();
    });
});

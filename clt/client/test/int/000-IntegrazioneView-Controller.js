describe("integrazione1", function() {
    var orig_PT = PT;
    var socket;
    var socket_orig = PT.CONTROLLER.getInstanceDispatcher().getSocket;
    beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');

        PT.MODEL.getInstanceRegistro().setProprioUtente(new PT.MODEL.Utente("e@ma.il", "Online", "personale"));
        PT.CONTROLLER.getInstanceDispatcher().getSocket = function() {
            socket = jasmine.createSpyObj("socket", ["invia"]);
            return socket;
        };
    
       
    });
    afterEach(function() {
        PT.CONTROLLER.getInstanceDispatcher().getSocket = socket_orig;
        PT = orig_PT;
    });
    //test interazione con il Login
    it("login", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("Login");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il Registrazione
    it("registrazione", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("Registrazione");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il RecuperaPassword
    it("recuperaPassword", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("RecuperaPassword");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il RichiestaDomandaSicurezza
    it("RichiestaDomandaSicurezza", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("RichiestaDomandaSicurezza");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il RicercaUtente
    it("RicercaUtente", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("RicercaUtente");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il NuovaAmicizia
    it("NuovaAmicizia", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("NuovaAmicizia");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il ModificaPassword
    it("ModificaPassword", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("ModificaPassword");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il ModificaProfilo
    it("ModificaProfilo", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("ModificaProfilo");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il MessaggioSegreteria
    it("MessaggioSegreteria", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("MessaggioSegreteria");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il AccettazioneAmicizia (22)
    it("AccettazioneAmicizia", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("AccettazioneAmicizia");
        expect(socket.invia).toHaveBeenCalled();
    });
    //test interazione con il EliminaAmicizia (23)
    it("EliminaAmicizia", function() {
        PT.VIEW.getInstanceSubjectView().fireEvent("EliminaAmicizia");
        expect(socket.invia).toHaveBeenCalled();
    });
    it("NuovaChiamata", function() {
          var canAVx = new Class ({
        
            richiediAggiuntaDestinatario : function(value) {}
        
        });
         var canAV = new canAVx();
  
        PT.CONTROLLER.S.CanaleAV = function() {
            return canAV;
        };
        spyOn(canAV, 'richiediAggiuntaDestinatario');
        PT.VIEW.getInstanceSubjectView().fireEvent("NuovaChiamata",{destinatario: "destinatario", tipo: "video"});
        expect(canAV.richiediAggiuntaDestinatario).toHaveBeenCalled();
       });

});
